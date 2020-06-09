package com.windaka.suizhi.mpi.websocket;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Service;

/**
 * 
 * @project: switch-cloud-mpi
 * @Description: 异常行为
 * @author: yangkai
 * @date: 2019年12月6日 上午8:22:46
 */
@ServerEndpoint("/abnormalWebSocket")
@Service
public class AbnormalWebSocketService {
	    public static  ConcurrentHashMap<String,Object> sessionMap=new ConcurrentHashMap<>();//SessionId、Session
	 
	    @OnOpen
	    public void onOpen(Session session){
	    	/*System.out.println("连接...当前sessionID..."+session.getId());
	    	System.out.println("连接...当前devID..."+devId);*/
	        sessionMap.put(session.getId(), session);
	    }
	 
	    @OnClose
	    public void onClose(Session session){
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
