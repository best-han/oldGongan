package com.windaka.suizhi.upcapture.service.impl;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.alibaba.fastjson.JSONArray;
import com.windaka.suizhi.upcapture.dao.UserXqDao;
import com.windaka.suizhi.upcapture.service.JPushService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class JPushServiceImpl implements JPushService {



    public static String MASTER_SECRET="4ad5147fc76439e273200211";
    public static String APP_KEY="1d30c1d5cd9556d277b8f6d5";
    public void sendJPush(String content,Set<String> alias,String eventName,int wclNum){
        JPushClient jPushClient=new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());
        try{
            PushPayload pushPayload=buildPushObject_android_and_ios(content,alias,eventName,wclNum);
            PushResult pushResult=jPushClient.sendPush(pushPayload);
        }catch(APIConnectionException e){
            // Connection error, should retry later
            log.error("Connection error, should retry later", e);
        }catch (APIRequestException e) {
            // Should review the error, and fix the request
            log.error("Should review the error, and fix the request", e);
            log.info("HTTP Status: " + e.getStatus());
            log.info("Error Code: " + e.getErrorCode());
            log.info("Error Message: " + e.getErrorMessage());
        }catch(Exception e){
            log.info("极光推送失败，alias："+alias,e);
        }

    }

    /**
     * 构建推送对象：所有平台，所有设备，内容为 ALERT 的通知 hjt
     * @param
     * @return
     */
    public static PushPayload buildPushObject_all_all_alert(String content) {
        return PushPayload.alertAll(content);
    }

    /**
     * 只推给指定的tags
     * @param
     * @param xqCode
     * @return
     */
    public static PushPayload buildPushObject_all_tag_alert(String content,String xqCode,String eventName){
        //用xqCode和event查询当前关联账号username
        //username做tags
        Set<String> tags=new HashSet<String>();
        tags.add(xqCode);
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag(tags))
                .setNotification(Notification.alert(content))
                .build();
    }

    /**
     * alert为提示消息，extra
     * @return
     */
    public PushPayload buildPushObject_android_and_ios(String content,Set<String> alias,String eventName,int wclNum) {

       /* Map<String,String> map =new HashMap<>();
        map.put("jsonContent",content);*/
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))
                //.setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .setAlert("异常报警消息："+eventName)//ios和Android都会有alert,但是ios没有下边的title
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle(eventName)
                                //.addExtras(map)
                                .addExtra("jsonContent",content)
                                .build()//Android title
                        )
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(1) //应用角标:如果不填，表示不改变角标数字，否则把角标数字改为指定的数字；为 0 表示清除。JPush 官方 SDK 会默认填充 badge 值为 "+1"
                                .addExtra("jsonContent",content)
                                .build()
                        )
                        .build()
                )
                .build();
    }

 /*   public static void main(String args[]){
        JPushClient jPushClient=new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());
        //PushPayload pushPayload=buildPushObject_all_all_alert("你好，志良");
        PushPayload pushPayload=buildPushObject_android_and_ios("你好，志良","","");
        try{
            PushResult pushResult=jPushClient.sendPush(pushPayload);
        }catch(APIConnectionException e){
            // Connection error, should retry later
            log.error("Connection error, should retry later", e);
        }catch (APIRequestException e) {
            // Should review the error, and fix the request
            log.error("Should review the error, and fix the request", e);
            log.info("HTTP Status: " + e.getStatus());
            log.info("Error Code: " + e.getErrorCode());
            log.info("Error Message: " + e.getErrorMessage());
        }
    }*/


}
