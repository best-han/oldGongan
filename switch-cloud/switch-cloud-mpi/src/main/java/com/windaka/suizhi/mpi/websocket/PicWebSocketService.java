package com.windaka.suizhi.mpi.websocket;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Service;

/**
 * 
 * @project: windaka-sjwl-mpi
 * @Description: 图片websocket
 * @author: yangkai
 * @date: 2019年7月16日 下午4:41:55
 */
@ServerEndpoint("/picWebSocket/{devId}")
@Service
public class PicWebSocketService {
	    public static  ConcurrentHashMap<String,String> devMap=new ConcurrentHashMap<>();//SessionId、设备Id
	    public static  ConcurrentHashMap<String,Object> sessionMap=new ConcurrentHashMap<>();//SessionId、Session
	 
	    @OnOpen
	    public void onOpen(Session session,@PathParam(value="devId")String devId){
	    	/*System.out.println("连接...当前sessionID..."+session.getId());
	    	System.out.println("连接...当前devID..."+devId);*/
	        devMap.put(session.getId(), devId);
	        sessionMap.put(session.getId(), session);
	    }
	 
	    @OnClose
	    public void onClose(Session session){
	    	devMap.remove(session.getId());
	    	sessionMap.remove(session.getId());
	    	/*System.out.println("devMap集合..."+devMap.size());
	        System.out.println("sessionMap集合..."+sessionMap.size());*/
	    }
	 
	    @OnMessage
	    public void onMessage(String message,Session session) throws IOException{
//	        System.out.println("来自客户端的消息："+message);
	    }
	 
	    @OnError
	    public void onError(Session session,Throwable throwable){
//	        System.out.println("发生错误！");
	        throwable.printStackTrace();
	    }
	    //发送信息
	    public void sendMessage(String message,Session session) {
	        try {
				session.getBasicRemote().sendText(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
}
