package com.windaka.suizhi.upcapture.service.impl;

import com.oppo.push.server.Notification;
import com.oppo.push.server.Result;
import com.oppo.push.server.Sender;
import com.oppo.push.server.Target;
import com.windaka.suizhi.upcapture.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Slf4j
@Service("oppo")
public class OppoPushServiceImpl implements PushMessageService {

    private static String appKey = "87aef24e853f46e59d549be0477f5971";
    private static  String masterSecret = "6d5514e560444a53afa7452c20760a05";


    @Override
    public void pushMsg(Set<String> targets, String content, String eventName, int wclNum) throws Exception{
        Sender sender = new Sender(appKey, masterSecret);
        //发送广播通知栏消息
        Notification broadNotification = getNotification( content,  eventName,  wclNum);// 创建通知栏消息体
        Result saveResult = sender.saveNotification(broadNotification); // 发送保存消息体请求
        saveResult.getStatusCode(); // 获取http请求状态码
        saveResult.getReturnCode(); // 获取平台返回码
        String messageId = saveResult.getMessageId(); //获取messageId
        Target target = new Target(); // 创建广播目标
        String targetValue="";
        for(String tagetString:targets){
            targetValue=tagetString+";";
        }
        //target.setTargetValue("CN_ddfaa7db1e4ecf75014143bdbc3e53ea;CN_8fa0618f178145d8c2a44091a1326411");
        target.setTargetValue(targetValue.substring(0,targetValue.length()-1));
        Result broadResult = sender.broadcastNotification(messageId, target); // 发送广播消息
        broadResult.getTaskId(); // 获取广播taskId
        List<Result.BroadcastErrorResult> errorList = broadResult.getBroadcastErrorResults();
        if (errorList.size() > 0) { // 如果大小为0，代表所有目标发送成功
            for (Result.BroadcastErrorResult error : errorList) {
                error.getErrorCode(); // 错误码
                error.getTargetValue(); // 目标
            }
        }

    }

    //创建通知栏消息体

    private Notification getNotification(String content, String eventName, int wclNum) {
        Notification notification = new Notification();
        /**
         * 以下参数必填项
         */
        notification.setTitle("异常报警消息:");
        notification.setSubTitle(eventName);
        //notification.setContent("通知栏内容");
        /**
         * 以下参数非必填项， 如果需要使用可以参考OPPO push服务端api文档进行设置
         */
        //通知栏样式 1. 标准样式  2. 长文本样式  3. 大图样式 【非必填，默认1-标准样式】
        notification.setStyle(1);
        // App开发者自定义消息Id，OPPO推送平台根据此ID做去重处理，对于广播推送相同appMessageId只会保存一次，对于单推相同appMessageId只会推送一次
        notification.setAppMessageId(UUID.randomUUID().toString());
        // 应用接收消息到达回执的回调URL，字数限制200以内，中英文均以一个计算
        notification.setCallBackUrl("http://www.test.com");
        // App开发者自定义回执参数，字数限制50以内，中英文均以一个计算
        notification.setCallBackParameter("");
        // 点击动作类型0，启动应用；1，打开应用内页（activity的intent action）；2，打开网页；4，打开应用内页（activity）；【非必填，默认值为0】;5,Intent scheme URL
        notification.setClickActionType(4);
        // 应用内页地址【click_action_type为1或4时必填，长度500】
        //notification.setClickActionActivity("com.coloros.push.demo.component.InternalActivity");
        // 网页地址【click_action_type为2必填，长度500】
        //notification.setClickActionUrl("http://www.test.com");
        // 动作参数，打开应用内页或网页时传递给应用或网页【JSON格式，非必填】，字符数不能超过4K，示例：{"key1":"value1","key2":"value2"}
        //notification.setActionParameters("{\"key1\":\"value1\",\"key2\":\"value2\"}");
        notification.setActionParameters(content);
        // 展示类型 (0, “即时”),(1, “定时”)
        notification.setShowTimeType(0);
        // 定时展示开始时间（根据time_zone转换成当地时间），时间的毫秒数
        //notification.setShowStartTime(System.currentTimeMillis() + 1000 * 60 * 3);
        // 定时展示结束时间（根据time_zone转换成当地时间），时间的毫秒数
        //notification.setShowEndTime(System.currentTimeMillis() + 1000 * 60 * 5);
        // 是否进离线消息,【非必填，默认为True】
        notification.setOffLine(true);
        // 离线消息的存活时间(time_to_live) (单位：秒), 【off_line值为true时，必填，最长3天】
        notification.setOffLineTtl(24 * 3600);
        // 时区，默认值：（GMT+08:00）北京，香港，新加坡
        notification.setTimeZone("GMT+08:00");
        // 0：不限联网方式, 1：仅wifi推送
        notification.setNetworkType(0);
        return notification;
    }

}
