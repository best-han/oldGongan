package com.windaka.suizhi.mpi.message;

import com.windaka.suizhi.mpi.config.RabbitmqConfig;
import com.windaka.suizhi.mpi.websocket.AbnormalWebSocketService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import java.util.Map;
import javax.annotation.Resource;
import javax.websocket.Session;

@Component
public class AbnormalReceiver {
	
	@Resource
	AbnormalWebSocketService abnormalWebSocketService;
	
	@RabbitListener(queues = RabbitmqConfig.QUEUE_STREET_ABNORMAL)
    @RabbitHandler
    public void process(@Payload String content){
//        log.info("22接受到的提示消息为："+content);
        for (Map.Entry<String,Object> entry : abnormalWebSocketService.sessionMap.entrySet()) {
//            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
            	Session tempSession = (Session) abnormalWebSocketService.sessionMap.get(entry.getKey());
            	abnormalWebSocketService.sendMessage(content,tempSession );
        }
      
    }
}
