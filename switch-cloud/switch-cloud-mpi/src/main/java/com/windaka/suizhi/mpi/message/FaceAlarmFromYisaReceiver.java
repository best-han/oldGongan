package com.windaka.suizhi.mpi.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.windaka.suizhi.common.utils.EnumDateStyle;
import com.windaka.suizhi.common.utils.TimesUtil;
import com.windaka.suizhi.mpi.config.RabbitmqConfig;
import com.windaka.suizhi.mpi.dao.FacePersonAttrDao;
import com.windaka.suizhi.mpi.dao.MsgPhoneDao;
import com.windaka.suizhi.mpi.model.RecordAbnormal;
import com.windaka.suizhi.mpi.websocket.WebSocketMes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName FaceAlarmFromYisaReceiver
 * @Description 获取以萨消息队列的消息内容
 * @Author lixianhua
 * @Date 2020/1/15 16:20
 * @Version 1.0
 */
@Slf4j
@Component
public class FaceAlarmFromYisaReceiver {

    @Resource
    WebSocketMes webSocketMes;

    @Autowired
    MsgPhoneDao msgPhoneDao;

    @Autowired
    FacePersonAttrDao facePersonAttrDao;

//    @RabbitListener(queues = RabbitmqConfig.QUEUE_YS_FACEALARM)
//    @RabbitHandler
    public void process(@Payload String content) {
        log.info("*************以萨消息队列接受开始****************");
        try {
            JSONObject contentJson = JSONObject.parseObject(content);
            // 人员名称
            String name = (String) contentJson.get("name");//face_crime_feature
            // 第三方库名称
            String typeName = (String) contentJson.get("typeName");
            // 证件号码
            String idNo = (String) contentJson.get("idNo");
            // 相似度
            String score = (String) contentJson.get("score");
            // 抓拍id
            String id = (String) contentJson.get("id");
            // 根据ID获取抓拍记录
            Map<String,Object> map = this.facePersonAttrDao.selectById(id);
            String location = map.get("xqName").toString() + map.get("deviceName").toString();
            RecordAbnormal record = new RecordAbnormal();
            record.setGroupName(typeName);
            record.setCaptureTimeStr(null==map.get("capture_time")?"": TimesUtil.dateToString((Date) map.get("capture_time"), EnumDateStyle.YYYY_MM_DD_HH_MM_SS_EN));
            record.setLocation(location);
            record.setPersonName(name);
            record.setSimilar(score);
            record.setTimeStamp(System.currentTimeMillis()+"");
            String color = "黄色";
            String resultJson = JSON.toJSONString(record);
            System.out.println(resultJson);
            webSocketMes.sendMessages(resultJson);//发送数据
            log.info("QUEUE_POLICE_FACEALARM*******************开始发送人脸报警短信");
            List<String> phones = msgPhoneDao.queryMsgPhoneList();
            SendMessage.send(phones,
                    color + "预警：发现" + typeName + "人员\n姓名：" + name + "\n证件号码：" + idNo + "\n出现地点：" +location + "\n" +
                            "抓拍时间：" + record.getCaptureTimeStr());
            log.info("*************以萨消息队列成功****************");
        } catch (IOException e) {
            log.error("*************以萨消息队列失败****************");
            e.printStackTrace();
        }
        log.info("*************以萨消息队列接受结束****************");
    }
}
