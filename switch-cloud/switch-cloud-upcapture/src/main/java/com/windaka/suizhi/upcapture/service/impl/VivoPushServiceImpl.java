package com.windaka.suizhi.upcapture.service.impl;

import com.vivo.push.sdk.notofication.Message;
import com.vivo.push.sdk.notofication.Result;
import com.vivo.push.sdk.notofication.TargetMessage;
import com.vivo.push.sdk.server.Sender;
import com.windaka.suizhi.upcapture.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


@Slf4j
@Service
public class VivoPushServiceImpl implements PushMessageService {

    private static int appId = 1;
    private static String appKey = "87aef24e853f46e59d549be0477f5971";
    private static  String  appSecret = "6d5514e560444a53afa7452c20760a05";


    @Override
    public void pushMsg(Set<String> targets, String content, String eventName, int wclNum) throws Exception{
        Sender sender = new Sender(appSecret);
        sender.initPool(20,10);//设置连接池参数，可选项
        sender.setAuthToken(sender.getToken(appId , appKey).getAuthToken());//设置推送的必要参数authToken(调用鉴权方法获得)

        Message saveList  = buildMessage(content,eventName,wclNum);//构建要全量推送的消息体
        //同一条信息覆盖多个用户。此方法需与批量推送用户方法配套使用
        Result resultSave = sender.saveListPayLoad(saveList);//发送保存群推消息请求
        if(resultSave.getResult()==0){//获取服务器返回的状态码，0成功，非0失败
            log.info("vivo发送保存群推消息请求成功："+resultSave.getDesc());//获取服务器返回的调用情况文字描述
        }else{
            log.info("vivo发送保存群推消息请求失败："+resultSave.getDesc());
            return;
        }
        String taskId=resultSave.getTaskId();//如请求发送成功，将获得该条消息的任务编号，即taskId

        TargetMessage targetMessage=buildTargetMessage(targets,taskId);
        Result result = sender.sendToList(targetMessage);//发送标签推送消息请求
        if(result.getResult()==0){//获取服务器返回的状态码，0成功，非0失败
            log.info("vivo推送成功："+result.getDesc());//获取服务器返回的调用情况文字描述
        }else{
            log.info("vivo推送失败："+result.getDesc());
            return;
        }

    }

    /**
     * 单推消息体（一条消息）
     * @return
     * @throws Exception
     */
    public Message buildMessage(String content, String eventName, int wclNum) throws Exception {
        Map<String,String> map=new HashMap<>();
        map.put("content",content);
        Message message = new Message.Builder()
                //.regId("12345678901234567890123")//仅构建单推消息体需要
                //.alias(ALIAS) //仅构建单推消息体需要
                //.orTagss(orTagss)   //仅构建标签推消息体需要
                //.andTags(andTags)//仅构建标签推消息体需要
                //.notTags(notTags) //仅构建标签推消息体需要
                .notifyType(1)//必填项，设置通知类型，value类型支持以下值：1：无2：响铃3：振动4：响铃和振动
                .title("接收到一条异常报警信息：")
                .content(eventName)
                .timeToLive(3600)//可选项, 消息的生命周期, 若用户离线, 设置消息在服务器保存的时间, 单位: 秒
                .skipType(4)//必填项，设置点击跳转类型：1：打开APP首页2：打开链接3：自定义4：打开app内指定页面
                .skipContent("security://com.windaka.commuinitysecurity.safeguard/NotifyDetailActivity")//可选项，跳转内容
                .networkType(-1)//可选项，发送推送使用的网络方式-1：方式不限1：仅在wifi下发送；默认为-1
                .clientCustomMap(map)//可选项，客户端自定义键值，key和value键值对总长度不能超过1024字符
                //.extra("http://www.vivo.com", "vivo")//可选项，仅单推中使用，提供了高级特性（消息送达回执）
                .requestId(UUID.randomUUID().toString())//必填项，用户请求唯一标识 最大64字符
                .build();
        return message;
    }

    /**
     * 批量推送用户消息体
     * @param aliases
     * @param taskId
     * @return
     * @throws Exception
     */
    public TargetMessage buildTargetMessage(Set<String> aliases,String taskId) throws Exception {
        /*Set<String> regids = new HashSet<>();
        regids.add("12345678901234567890123");
        regids.add("12345678901234567890321");*/
        /*Set<String> aliases = new HashSet<>();
        aliases.add("ALIAS1");
        aliases.add("ALIAS2");*/
        TargetMessage targetMessage = new TargetMessage.Builder()
                //.regIds(regids)
                .aliases(aliases)
                .requestId(UUID.randomUUID().toString())
                .taskId(taskId)
                .build();
        return targetMessage;
    }


    public static void main(String[] args) throws Exception {
        Sender sender = new Sender("appSecret");//注册登录开发平台网站获取到的appSecret
        Result result = sender.getToken(appId , "appKey");//注册登录开发平台网站获取到的appId和appKey
        sender.setAuthToken(result.getAuthToken());
        Message singleMessage = new Message.Builder()
                .regId("regId")//该测试手机设备订阅推送后生成的regId
                .notifyType(3)
                .title("try_title")
                .content("try-content")
                .timeToLive(1000)
                .skipType(2)
                .skipContent("http://www.vivo.com")
                .networkType(-1)
                .requestId("1234567890123456")
                .build();
        Result resultMessage = sender.sendSingle(singleMessage);
        System.out.println(resultMessage);
    }


}
