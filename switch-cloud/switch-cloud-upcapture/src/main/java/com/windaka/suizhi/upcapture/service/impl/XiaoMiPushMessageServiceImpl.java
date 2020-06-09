package com.windaka.suizhi.upcapture.service.impl;

import com.alibaba.fastjson.JSON;
import com.windaka.suizhi.upcapture.service.PushMessageService;
import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class XiaoMiPushMessageServiceImpl implements PushMessageService {

    private static String masterSecret = "rwaJHKs7aeg3EiJAoMaTYw==";//用户在申请Push服务获取的服务参数

    @Override
    public void pushMsg(Set<String> targets, String content, String eventName,int wclNum) throws Exception {
        Constants.useOfficial();
        Sender sender = new Sender(masterSecret);
        /*String messagePayload = "{\"online\":\"1\"}";
        Message message = new Message.Builder()
                .title(title)
                .description(content)
                .payload(messagePayload)
                .restrictedPackageName(pushMessageDTO.getAppName())//设置包名
                .passThrough(0)//消息使用透传方式1表示透传消息，0表示通知栏消息
                .extra("jsonContent",content)
                .notifyType(-1)
                .build();

        sender.sendToAlias(message, pushMessageDTO.getTarget(), 1);*/
     //   String messagePayload = "消息体内容";
        //String title = "消息标题";
        Message message = new Message.Builder() // 编译消息体
                .title("接收到一条异常报警消息:") // 设置在通知栏展示的通知的标题
                .description(eventName) // 设置在通知栏展示的通知描述
                .payload(content) // 设置要发送的消息内容
                .restrictedPackageName("com.windaka.communitysecurity.safeguard") // 设置app的包名packageName, packageName必须和开发者网站上申请的结果一致
                .passThrough(0)  //设置消息是否通过透传的方式至App, 1表示透传消息, 0表示通知栏消息(默认是通知栏消息)
                //.notifyId(notifyId) // 可选项, 默认情况下, 通知栏只显示一条推送消息, 如果通知栏要显示多条推送消息, 需要针对不同的消息设置不同的notify_id
                .notifyType(1) // 设置通知类型, type类型支持以下值：1：使用默认提示音提示2：使用默认震动提示4：使用默认led灯光提示-1（系统默认值）：以上三种效果都有0：以上三种效果都无，即静默推送。
                // 下面extra配置可以自定义打开手机app指定页面（Activity）
                .extra(Constants.EXTRA_PARAM_NOTIFY_EFFECT, Constants.NOTIFY_ACTIVITY)
                .extra(Constants.EXTRA_PARAM_INTENT_URI,"intent://com.windaka.communitysecurity.safeguard/MiPushReceiveActivity#Intent;scheme=security;launchFlags=0x14000000;S.message="+content+";end")//intent配置需要app开发者编译好给你
                .build();

        List<String> targetsList=new ArrayList<>(targets);
        Result result;
        if(targetsList.size()==1){
            result = sender.sendToAlias(message,targetsList.get(0) ,3);//单个alias发送
        }else{
            result = sender.sendToAlias(message,targetsList ,3); //根据regID，发送消息到指定设备上，最后一个参数没重试次数
        }
        log.info("小米推送 -- >> 返回结果Result:{}", JSON.toJSONString(result));

        //return 1;
    }

    public static void main(String[] args) throws Exception{
        Constants.useOfficial();
        Sender sender = new Sender(masterSecret);
        Message message = new Message.Builder() // 编译消息体
                .title("接收到一条异常报警消息:") // 设置在通知栏展示的通知的标题
                .description("ceshi") // 设置在通知栏展示的通知描述
                .payload("") // 设置要发送的消息内容
                .restrictedPackageName("com.windaka.communitysecurity.safeguard") // 设置app的包名packageName, packageName必须和开发者网站上申请的结果一致
                .passThrough(0)  //设置消息是否通过透传的方式至App, 1表示透传消息, 0表示通知栏消息(默认是通知栏消息)
                //.notifyId(notifyId) // 可选项, 默认情况下, 通知栏只显示一条推送消息, 如果通知栏要显示多条推送消息, 需要针对不同的消息设置不同的notify_id
                .notifyType(-1) // 设置通知类型, type类型支持以下值：1：使用默认提示音提示2：使用默认震动提示4：使用默认led灯光提示-1（系统默认值）：以上三种效果都有0：以上三种效果都无，即静默推送。
                // 下面extra配置可以自定义打开手机app指定页面（Activity）
                .extra(Constants.EXTRA_PARAM_NOTIFY_EFFECT, Constants.NOTIFY_ACTIVITY)
                .extra(Constants.EXTRA_PARAM_INTENT_URI,"intent://com.windaka.communitysecurity.safeguard/MiPushReceiveActivity#Intent;scheme=security;launchFlags=0x14000000;S.message="+""+";end")//intent配置需要app开发者编译好给你
                //.extra("jsonContent",content)
                .build();
        log.info(message.toString());

        List<String> targetsList=new ArrayList<>();
        targetsList.add("15066893239");
        targetsList.add("15066893238");
       Result result = sender.sendToAlias(message,targetsList ,3); //根据regID，发送消息到指定设备上，最后一个参数没重试次数
       // Result result = sender.send(message,"15066893239" ,3);
        log.info("小米推送 -- >> 返回结果Result:{}", JSON.toJSONString(result));
    }


}
