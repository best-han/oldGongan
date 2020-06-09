package com.windaka.suizhi.mpi.message;

import com.alibaba.fastjson.JSONObject;
import com.windaka.suizhi.mpi.config.RabbitmqConfig;
import com.windaka.suizhi.mpi.websocket.PicWebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

import javax.annotation.Resource;
import javax.websocket.Session;

@Slf4j
@Component
public class PicReceiver {
	/*@Resource
	RabbitTemplate rabbitTemplate;*/

	@Resource
	PicWebSocketService picWebSocketService;
	
	@RabbitListener(queues = RabbitmqConfig.QUEUE_PICS)
    @RabbitHandler
    public void process(@Payload String content){
//        log.info("22接受到的提示消息为："+content);
//        System.out.println("pic_content..."+content);
        JSONObject  contentJson = JSONObject.parseObject(content);
        String devId= (String) contentJson.get("devID");
        for (Map.Entry<String, String> entry : picWebSocketService.devMap.entrySet()) {
//            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
            if(entry.getValue().equals(devId)){
            	Session tempSession = (Session) picWebSocketService.sessionMap.get(entry.getKey());
            	picWebSocketService.sendMessage(content,tempSession );
            }
        }
        
      
    }
	/*public void sendMsg(String msg){
		rabbitTemplate.convertAndSend(RabbitmqConfig.QUEUE_PICS,msg);
	}*/
	
	/*@PostConstruct
	public void sendMsgBy5Time(){
		System.out.println("初始化测试");
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					System.out.println("222222");
					try {
						Thread.sleep(5000);
						String content="{\"devID\":\"02_1148134379157143552\",\"personName\":\"王志良\",\"capImgPath\":\"http://10.10.5.26:9002/windaka/userfiles/fileupload/captureimg/2019-07-17/1563329334922_00000024.jpg\",\"devName\":\"志良桌上的\",\"capTimeStr\":\"1563329334937\",\"capTime\":\"2019-07-17 10:08:54\"}";
						JSONObject  contentJson = JSONObject.parseObject(content);
				        String devId= (String) contentJson.get("devID");
						 for (Map.Entry<String, String> entry : picWebSocketService.devMap.entrySet()) {
//					            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
					            if(entry.getValue().equals(devId)){
					            	Session tempSession = (Session) picWebSocketService.sessionMap.get(entry.getKey());
					            	picWebSocketService.sendMessage(content,tempSession );
					            }
					        }
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		}).start();
		
	}*/
	
	
}
