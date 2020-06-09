package com.windaka.suizhi.upcapture.message;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.windaka.suizhi.upcapture.config.RabbitmqConfig;
//import com.windaka.suizhi.upcapture.websocket.SocketServer;
import com.windaka.suizhi.upcapture.dao.UserXqDao;
import com.windaka.suizhi.upcapture.service.AbnormalRecordService;
import com.windaka.suizhi.upcapture.service.HvPushService;
import com.windaka.suizhi.upcapture.service.JPushService;
import com.windaka.suizhi.upcapture.service.PushMessageService;
import com.windaka.suizhi.upcapture.service.impl.OppoPushServiceImpl;
import com.windaka.suizhi.upcapture.service.impl.VivoPushServiceImpl;
import com.windaka.suizhi.upcapture.service.impl.XiaoMiPushMessageServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;

import static com.windaka.suizhi.api.common.OssConstants.*;

/**
 * 异常行为消息接收
 */
@Slf4j
@Component
public class MsgReceiverTips {

    @Autowired
    AbnormalRecordService abnormalRecordService;
    @Autowired
    JPushService jPushService;
    @Autowired
    UserXqDao userXqDao;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    HvPushService hvPushService;
    @Autowired
    XiaoMiPushMessageServiceImpl xmPushService;
    @Autowired
    OppoPushServiceImpl oppoPushService;
    @Autowired
    VivoPushServiceImpl vivoPushService;

    @RabbitListener(queues = RabbitmqConfig.QUEUE_ABNORMAL)
    @RabbitHandler
    public void process(@Payload String content) throws Exception{
        log.info("upcapture服务接受到的异常提示消息为："+content);
        try {
            JSONObject contentJson = JSONObject.parseObject(content);
            //json对象转Map
            Map<String, Object> contentMap = (Map<String, Object>) contentJson;
            if (ObjectUtil.isNotNull(contentMap.get("event"))) {

                abnormalRecordService.saveAbnormalRecord(contentMap);
                //传给手机端的消息体只留event字段，避免太长
                String event=contentMap.get("event").toString();
                Map<String, Object> eventMap = new HashMap<>();
                eventMap.put("event",event);
                String eventJson= JSON.toJSONString(eventMap);

                        //用xqCode和event查询当前关联账号username
                //username做alias
                Set<String> alias=userXqDao.queryUsernamesByXqCode((String) contentMap.get("xqCode"));
                //Set<String> aliasJiguang=alias;
                //alias.add(xqCode);
                //华为推送start
                JSONArray hvDeviceTokens = new JSONArray();//目标设备Token
                Set<String> xmDeviceTokens = new HashSet<>();//目标设备Token
                Set<String> vivoDeviceTokens = new HashSet<>();//目标设备Token
                Set<String> oppoDeviceTokens = new HashSet<>();//目标设备Token
                int wclNum=1;//未处理条数，作为角标数字
                if(!ObjectUtils.isEmpty(contentMap.get("num"))){
                    wclNum=Integer.parseInt(contentMap.get("num").toString());
                }
                String hvUsername=null;
                String xmUsername=null;
                String vivoUsername=null;
                String oppoUsername=null;
                List<String> aliasList=new ArrayList<>(alias);
                for(int i=0;i<aliasList.size();i++){
                    hvUsername=(String)stringRedisTemplate.boundHashOps(MOBILE_USERNAME_HW).get(aliasList.get(i));//华为手机用户
                    if(StringUtils.isNotBlank(hvUsername)){
                        hvDeviceTokens.add(hvUsername);
                        alias.remove(aliasList.get(i));
                    }
                    xmUsername=(String)stringRedisTemplate.boundHashOps(MOBILE_USERNAME_XM).get(aliasList.get(i));//小米手机用户
                    if(StringUtils.isNotBlank(xmUsername)){
                        xmDeviceTokens.add(xmUsername);
                        alias.remove(aliasList.get(i));
                    }
                    vivoUsername=(String)stringRedisTemplate.boundHashOps(MOBILE_USERNAME_VIVO).get(aliasList.get(i));//vivo手机用户
                    if(StringUtils.isNotBlank(vivoUsername)){
                        vivoDeviceTokens.add(vivoUsername);
                        alias.remove(aliasList.get(i));
                    }
                    oppoUsername=(String)stringRedisTemplate.boundHashOps(MOBILE__USERNAME_OPPO).get(aliasList.get(i));//oppo手机用户
                    if(StringUtils.isNotBlank(oppoUsername)){
                        oppoDeviceTokens.add(oppoUsername);
                        alias.remove(aliasList.get(i));
                    }
                }
                if(!hvDeviceTokens.isEmpty() && hvDeviceTokens.size()>0){
                    log.info("华为");
                    hvPushService.pushHvMsg((String)contentMap.get("eventName"),eventJson,hvDeviceTokens,wclNum);
                }
                if(!xmDeviceTokens.isEmpty() && xmDeviceTokens.size()>0){
                    log.info("小米");
                    xmPushService.pushMsg(xmDeviceTokens,eventJson,(String)contentMap.get("eventName"),wclNum);
                }
                if(!vivoDeviceTokens.isEmpty() && vivoDeviceTokens.size()>0){
                    log.info("vivo");
                    vivoPushService.pushMsg(vivoDeviceTokens,eventJson,(String)contentMap.get("eventName"),wclNum);
                }
                if(!oppoDeviceTokens.isEmpty() && oppoDeviceTokens.size()>0){
                    log.info("oppo");
                    oppoPushService.pushMsg(oppoDeviceTokens,eventJson,(String)contentMap.get("eventName"),wclNum);
                }

                //极光推送start
                if(alias!=null && alias.size()>0){
                    log.info("极光");
                    jPushService.sendJPush(content, alias,(String) contentMap.get("eventName"),wclNum);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info("[MsgReceiverTips.process,参数：{},异常信息{}]",content,e.getMessage());
        }

        //改为用极光推送
        /*for(Map.Entry<String, List<Map<String,Object>>> entry: SocketServer.websocketXq.entrySet()) {

            List<Map<String,Object>> xqList=entry.getValue();
            for(int i=0;i<xqList.size();i++){
                Map<String,Object> xq=xqList.get(i);
                if(((String)xq.get("xqCode")).equals((String) contentMap.get("xqCode"))){
                    SocketServer.sendMessage(content,entry.getKey());
                }
            }

        }*/
    }




}
