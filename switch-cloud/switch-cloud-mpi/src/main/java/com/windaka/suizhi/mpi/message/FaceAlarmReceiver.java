package com.windaka.suizhi.mpi.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.windaka.suizhi.common.utils.EnumDateStyle;
import com.windaka.suizhi.common.utils.PropertiesUtil;
import com.windaka.suizhi.common.utils.TimesUtil;
import com.windaka.suizhi.mpi.config.RabbitmqConfig;
import com.windaka.suizhi.mpi.dao.*;
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
import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Component
public class FaceAlarmReceiver {

    @Resource
    WebSocketMes webSocketMes;
    @Autowired
    FaceTrackRecordDao faceTrackRecordDao;
    @Autowired
    FaceCrimeFeatureDao faceCrimeFeatureDao;
    @Autowired
    MsgSocketIdDao msgSocketIdDao;
    @Autowired
    private FacePersonAttrDao facePersonAttrDao;
    @Autowired
    private FaceAlarmRecordDao faceAlarmRecordDao;
    @Autowired
    MsgPhoneDao msgPhoneDao;


    /**
     * 功能描述: 获取抓拍人员信息和犯罪人员信息比对结果  发送websocket
     * @auther: lixianhua
     * @date: 2019/12/16 16:28
     * @param:
     * @return:
     */
    @RabbitListener(queues = RabbitmqConfig.QUEUE_POLICE_FACEALARM)
    @RabbitHandler
    public void process(@Payload String  content) {
        log.info("QUEUE_POLICE_FACEALARM*******************接收到的消息体："+content);
        JSONObject contentJson = JSONObject.parseObject(content);
        // 犯罪库ID
        String crimeFeatureId = (String) contentJson.get("crimeFeatureId");//face_crime_feature
        // 人员ID
        String id = (String) contentJson.get("Id");
        // 相似度
        String similarity = (String) contentJson.get("similarity");
        // 推送信息
        RecordAbnormal record = new RecordAbnormal();
        // 根据主键抓拍获取人员信息
        Map<String,Object> captureMap = this.facePersonAttrDao.selectById(id);
        if (null == captureMap ){
            return;
        }
        // 出现位置
        record.setLocation(captureMap.get("xqName").toString()+captureMap.get("deviceName").toString());
        record.setXqName(captureMap.get("xqName").toString());
        // 抓拍时间
        record.setCaptureTimeStr(null==captureMap.get("capture_time")?"":TimesUtil.dateToString((Date) captureMap.get("capture_time"), EnumDateStyle.YYYY_MM_DD_HH_MM_SS_EN));
        // 抓拍信息
        record.setImg(null!=captureMap.get("base64_img")? PropertiesUtil.getLocalTomcatImageIp()+captureMap.get("base64_img").toString():"");
        // 根据主键获取人员库信息
        Map<String,Object> crimeMap = this.faceCrimeFeatureDao.queryTypePerson(Integer.parseInt(crimeFeatureId));
        if (null == crimeMap) {
            return;
        }
        record.setTimeStamp(System.currentTimeMillis()+"");
        // 姓名
        record.setPersonName(null == crimeMap.get("personName")?"无":crimeMap.get("personName").toString());
        // 人脸库信息
        record.setLibraryImg(null!=crimeMap.get("imagePath")?PropertiesUtil.getLocalTomcatImageIp()+crimeMap.get("imagePath").toString():"");
        // 库名称
        String faceTypeName = crimeMap.get("groupName").toString();
        record.setGroupName(faceTypeName);
        // 报警等级（用于判断弹窗颜色）
        String event = "9";
        String colorName="";
        if (null!=crimeMap.get("alarmLevel")){
            event = crimeMap.get("alarmLevel").toString();
            if ("1".equals(event)){
                event = "8";
                colorName="黄色";
            }else if("2".equals(event)){
                event = "9";
                colorName="橙色";
            } else if("3".equals(event)){
                event = "10";
                colorName="红色";
            }
        }
        record.setEvent(event);
        // 相似度
        BigDecimal simi = new BigDecimal(similarity);

        record.setSimilar(String.valueOf((simi.setScale(2,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("100")).intValue()));
        String resultJson = JSON.toJSONString(record);
        System.out.println(resultJson);
        webSocketMes.sendMessages(resultJson);//发送数据
        // 报警信息
        Map<String,Object> alarmMap = new HashMap<>();
        alarmMap.put("alarmId",captureMap.get("manage_id"));
        alarmMap.put("alarmTime",(Date)captureMap.get("capture_time"));
        alarmMap.put("clStatus","0");
        alarmMap.put("status","0");
        alarmMap.put("captureId",id);
        alarmMap.put("creTime",new Date());
        alarmMap.put("contrastValue",similarity);
        alarmMap.put("deviceCode",captureMap.get("deviceCode"));
        alarmMap.put("alarmLevel",crimeMap.get("alarmLevel"));
        alarmMap.put("faceImgUrl",crimeMap.get("imagePath"));
        alarmMap.put("base64Img",captureMap.get("base64_img"));
        alarmMap.put("personName",crimeMap.get("personName"));
        alarmMap.put("face_id",Integer.parseInt(crimeFeatureId));
        alarmMap.put("faceTypeCode",crimeMap.get("faceTypeCode"));
        alarmMap.put("faceTypeName",crimeMap.get("groupName"));
        alarmMap.put("xqCode",captureMap.get("xq_code"));
        // 人脸报警记录中添加一条记录
        faceAlarmRecordDao.insertFaceAlarmRecord(alarmMap);
        try{
            log.info("QUEUE_POLICE_FACEALARM*******************开始发送人脸报警短信");
            List<String> phones=msgPhoneDao.queryMsgPhoneList();
            SendMessage.send(phones,colorName+"预警：发现"+faceTypeName+"人员\n姓名："+alarmMap.get("personName")+"\n证件号码："+crimeMap.get("personPaperNum")+"\n出现地点："+record.getLocation()+"\n抓拍时间："+record.getCaptureTimeStr());
            log.info("QUEUE_POLICE_FACEALARM*******************发送人脸报警短信结束");
        }catch(Exception e){
            e.printStackTrace();
            log.error("FaceAlarmReceiver短信发送失败");
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
