package com.windaka.suizhi.mpi.message;

import com.alibaba.fastjson.JSONObject;
import com.windaka.suizhi.mpi.config.RabbitmqConfig;
import com.windaka.suizhi.mpi.websocket.SocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class MsgReceiverTips {

    @RabbitListener(queues = RabbitmqConfig.QUEUE_TIPS)
    @RabbitHandler
    public void process(@Payload String content){
        log.info("接受到的提示消息为："+content);
        JSONObject  contentJson = JSONObject.parseObject(content);
        //json对象转Map
        Map<String,Object> contentMap = (Map<String,Object>)contentJson;

        for(Map.Entry<String, List<Map<String,Object>>> entry: SocketServer.websocketXq.entrySet()) {

            List<Map<String,Object>> xqList=entry.getValue();
            for(int i=0;i<xqList.size();i++){
                Map<String,Object> xq=xqList.get(i);
                if(((String)xq.get("xqCode")).equals((String) contentMap.get("xqCode"))){
                    SocketServer.sendMessage(content,entry.getKey());
                }
            }

        }
    }

//    @RabbitListener(queues = RabbitmqConfig.QUEUE_TIPS)
//    @RabbitHandler
//    public void process(String hello, Channel channel, Message message) throws IOException {
//        System.out.println("HelloReceiver收到  : " + hello);
//        try {
//            //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
//            System.out.println("receiver success");
//        } catch (IOException e) {
//            e.printStackTrace();
//            //丢弃这条消息
//            //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
//            System.out.println("receiver fail");
//        }
//
//    }



}
