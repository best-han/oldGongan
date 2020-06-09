package com.windaka.suizhi.mpi.websocket;

import com.windaka.suizhi.mpi.controller.ConvertRtspRtmpController;
import com.windaka.suizhi.mpi.model.DeviceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint(value="/socketServer/{userName}")
public class SocketServer {


    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);
    public SocketServer() {
        System.out.println("=========websocket service is open");
    }


    /**
     *
     * 用线程安全的CopyOnWriteArraySet来存放客户端连接的信息
     */
    private static CopyOnWriteArraySet<Client> socketServers = new CopyOnWriteArraySet<>();
    private static ConcurrentHashMap<String,Client> socketClients = new ConcurrentHashMap<>();//保存token对应的客户端Session
    public static  ConcurrentHashMap<String,List<Map<String,Object>>> websocketXq=new ConcurrentHashMap<>();//可以发送的小区
    private static ConcurrentHashMap<Session,HeartBean> heartsMap=new ConcurrentHashMap<Session,HeartBean>();//会话和心跳的映射

    //当前的token对应的当前打开的摄像头信息
    public static ConcurrentHashMap<String, DeviceInfo>  tokenDeviceMap=new ConcurrentHashMap<>();

    private Timer hearTimer;//心跳定时器
    private long hearTime=15000;//心跳时间
    private long heartScanTime=1000;//心跳扫描时间


    /**
     *
     * websocket封装的session,信息推送，就是通过它来信息推送
     */
    private Session session;
    /**
     *
     * 服务端的userName,因为用的是set，每个客户端的username必须不一样，否则会被覆盖。
     * 要想完成ui界面聊天的功能，服务端也需要作为客户端来接收后台推送用户发送的信息
     */
    private final static String SYS_USERNAME = "windaka";
    public static void setApplicationContext(ApplicationContext applicationContext) {
        SocketServer.applicationContext = applicationContext;
    }
    private static ApplicationContext applicationContext;


    /**
     *
     * 用户连接时触发，我们将其添加到
     * 保存客户端连接信息的socketServers中
     *
     * @param session
     * @param userName
     */
    @OnOpen
    public void open(Session session,@PathParam(value="userName")String userName){

        if(socketClients.containsKey(userName)){//如果已存在会话则先关闭
            try {
                Client client=socketClients.get(userName);
                if(client!=null){
                    Session sessiontemp=client.getSession();
                    if(sessiontemp!=null){
                        sessiontemp.close();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        socketClients.put(userName,new Client(userName,session));
        logger.info("客户端:【{}】连接成功",Thread.currentThread().getName()+"-"+userName);

        //判断是否需要启动定时器
        if(heartsMap.size()==0){
            hearTimer=startHeartCheckTimer(heartScanTime);
        }
        //创建心跳时间
        HeartBean heartBean=new HeartBean();
        heartBean.setHeartTime(System.currentTimeMillis());
        heartsMap.put(session,heartBean);

        logger.info("open+thread-----{}",Thread.currentThread().getName());

    }
    /**
     *
     * 收到客户端发送信息时触发
     * 我们将其推送给客户端(niezhiliang9595)
     * 其实也就是服务端本身，为了达到前端聊天效果才这么做的
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message, Session session){
        logger.info("onMessage+thread-----{}",Thread.currentThread().getName());

        logger.info("客户端:【{}】接收数据",message);
        //如果接受的消息是心跳
        if(message.split(":")[0].equalsIgnoreCase("ping")){
            HeartBean heartBean=heartsMap.get(session);
            if(heartBean!=null){
                heartBean.setHeartTime(System.currentTimeMillis());//设置这次心跳时间
            }
            //然后发送pong
            try {
                session.getBasicRemote().sendText("pong:");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{//如果收到的不是心跳
            //重置心跳时间
            HeartBean heartBean=heartsMap.get(session);
            heartBean.setHeartTime(System.currentTimeMillis());//设置这次心跳时间

        }


    }
    /**
     *
     * 连接关闭触发，通过sessionId来移除
     * socketServers中客户端连接信息
     */
    @OnClose
    public void onClose(Session session){
        logger.info("onClose+thread-----{}",Thread.currentThread().getName());
        //关闭username对应的Session
        for(Map.Entry<String, Client> entry: socketClients.entrySet()) {
            Client client = socketClients.get(entry.getKey());//
            if(session==client.getSession()){
                socketClients.remove(entry.getKey());
                websocketXq.remove(entry.getKey());
                //关闭当前tokenId对应的流
                DeviceInfo deviceInfo=SocketServer.tokenDeviceMap.get(client.getUserName());
                if(deviceInfo!=null){
                    ConvertRtspRtmpController convertRtspRtmpController=new ConvertRtspRtmpController();
                    Map<String,Object> param=new HashMap<>();
                    param.put("xqCode",deviceInfo.getXqCode());
                    param.put("tokenId",client.getUserName());
                    param.put("capDevChannel",deviceInfo.getCapDevChannel());
                    convertRtspRtmpController.closeRtmpVideoStream(param);
                }

            }
        }

        //删除心跳的映射关系
        heartsMap.remove(session);
        //关闭心跳
        if(heartsMap.size()==0){
            stopHeartCheckTimer(hearTimer);
        }


    }
    /**
     *
     * 发生错误时触发
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.info("onError+thread-----{}",Thread.currentThread().getName());
    }

//    /**
//     *
//     * 信息发送的方法，通过客户端的userName
//     * 拿到其对应的session，调用信息推送的方法
//     * @param message
//     * @param userName
//     */
//    public synchronized static void sendMessage(String message,String userName) {
//
//        for (Client cli : socketServers){
//            if (userName.equals(cli.getUserName())) {
//                try {
//                    cli.getSession().getBasicRemote().sendText(message);
//
//                    logger.info("服务端推送给客户端 :【{}】",cli.getUserName(),message);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
////		socketServers.forEach(client ->{
////			if (userName.equals(client.getUserName())) {
////				try {
////					client.getSession().getBasicRemote().sendText(message);
////
////					logger.info("服务端推送给客户端 :【{}】",client.getUserName(),message);
////
////				} catch (IOException e) {
////					e.printStackTrace();
////				}
////			}
////		});
//    }
    public  static boolean sendMessage(String message,String userName) {

        try {
            Client client=socketClients.get(userName);
            if(client!=null){
                Session session=client.getSession();
                if(session!=null){
                    session.getBasicRemote().sendText(message);
                    logger.info("服务端推送给客户端 :----成功【{}】",userName,message);
                    return true;
                }
            }
            return false;
//            heartsMap.get(client.getSession()).setHeartTime(System.currentTimeMillis());//发送成功一次就重设一下心跳时间

        } catch (IOException e) {
            e.printStackTrace();
            logger.info("服务端推送给客户端 :----失败【{}】",userName,message);
        }
        return false;
    }
//    /**
//     *
//     * 信息发送的方法，通过客户端的userName
//     * 拿到其对应的session，调用信息推送的方法
//     * @param message
//     */
//    public synchronized static void sendMessage(String message) {
//
//        for (Client cli : socketServers){
//            try {
//                cli.getSession().getBasicRemote().sendText(message);
//                logger.info("服务端推送给客户端 :【{}】",cli.getUserName(),message);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

//		socketServers.forEach(client ->{
//			if (userName.equals(client.getUserName())) {
//				try {
//					client.getSession().getBasicRemote().sendText(message);
//
//					logger.info("服务端推送给客户端 :【{}】",client.getUserName(),message);
//
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		});
//    }
    /**
     *
     * 获取服务端当前客户端的连接数量，
     * 因为服务端本身也作为客户端接受信息，
     * 所以连接总数还要减去服务端
     * 本身的一个连接数
     *
     * 这里运用三元运算符是因为客户端第一次在加载的时候
     * 客户端本身也没有进行连接，-1 就会出现总数为-1的情况，
     * 这里主要就是为了避免出现连接数为-1的情况
     *
     * @return
     */
    public synchronized static int getOnlineNum(){
        return  socketServers.size();
//		return socketServers.stream().filter(client -> !client.getUserName().equals(SYS_USERNAME))
//				.collect(Collectors.toList()).size();
    }
    /**
     *
     * 获取在线用户名，前端界面需要用到
     * @return
     */
    public synchronized static List<String> getOnlineUsers(){


//		List<String> onlineUsers = socketServers.stream()
//				.filter(client -> !client.getUserName().equals(SYS_USERNAME))
//				.map(client -> client.getUserName())
//				.collect(Collectors.toList());
//
//	    return onlineUsers;
        List<String> onlineUsers = new ArrayList<>();
        for (Client cli : socketServers){
            onlineUsers.add(cli.getUserName());
        }
        return onlineUsers;
    }
    /**
     *
     * 信息群发，我们要排除服务端自己不接收到推送信息
     * 所以我们在发送的时候将服务端排除掉
     * @param message
     */
    public synchronized static void sendAll(String message) {
        //群发，不能发送给服务端自己
//		socketServers.stream().filter(cli -> cli.getUserName() != SYS_USERNAME)
//				.forEach(client -> {
//			try {
//				client.getSession().getBasicRemote().sendText(message);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		});

        logger.info("服务端推送给所有客户端 :【{}】",message);
    }
    /**
     *
     * 多个人发送给指定的几个用户
     * @param message
     * @param persons
     */
    public synchronized static void SendMany(String message,String [] persons) {
        for (String userName : persons) {
            sendMessage(message,userName);
        }
    }


    /**
     * 启动心跳定时器
     */
    public Timer startHeartCheckTimer(long time){
        Timer nTimer = new Timer();
        nTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                for(Map.Entry<Session,HeartBean> entry: heartsMap.entrySet()) {
                    HeartBean heartBean=heartsMap.get(entry.getKey());
                    if(heartBean!=null){
                        long curTime=System.currentTimeMillis();
                        if(curTime-heartBean.getHeartTime()>hearTime){
                            //如果超时则关闭session，同时请求关闭当前的流
                            try {
                                entry.getKey().close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }
        },0,time);
        return  nTimer;
    }
    public void stopHeartCheckTimer(Timer timer){
        if(timer!=null){
            timer.cancel();
        }
    }



}
