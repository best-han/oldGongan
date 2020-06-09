package com.windaka.suizhi.mpi.websocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Service;

@ServerEndpoint(value="/homePageMessage")
@Service
public class WebSocketMes {
	
	    private static CopyOnWriteArraySet<Session> sessionSet=new CopyOnWriteArraySet<Session>();
	 
	    @OnOpen
	    public void onOpen(Session session){
	        sessionSet.add(session);//加入set中
	    }
	 
	    @OnClose
	    public void onClose(Session session){
	    	sessionSet.remove(session);
	    }
	 
	    @OnMessage
	    public void onMessage(String message,Session session){
	        System.out.println("来自客户端的消息："+message);
	    }
	 
	    @OnError
	    public void onError(Session session,Throwable throwable){
	        throwable.printStackTrace();
	    }
	//   下面是自定义的一些方法
	    /*public void sendMessage(String message) throws IOException {
	        this.session.getBasicRemote().sendText(message);
	    }*/
	    
	    public void sendMessages(String message){
	    	System.out.println("发送session数量。。。"+sessionSet.size());
	    	sessionSet.forEach(set -> {
	    		try {
	    			synchronized(set){
	    				set.getBasicRemote().sendText(message);
	    			}
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	});
	    }

}
