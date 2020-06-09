package com.windaka.suizhi.mpi.task;

import com.alibaba.fastjson.JSON;
import com.windaka.suizhi.common.utils.PropertiesUtil;
import com.windaka.suizhi.common.utils.TimesUtil;
import com.windaka.suizhi.mpi.dao.AbnormalRecordDao;
import com.windaka.suizhi.mpi.dao.CarRecordDao;
import com.windaka.suizhi.mpi.dao.MsgPhoneDao;
import com.windaka.suizhi.mpi.dao.MsgSocketIdDao;
import com.windaka.suizhi.mpi.message.SendMessage;
import com.windaka.suizhi.mpi.model.RecordAbnormal;
import com.windaka.suizhi.mpi.utils.QueueUtils;
import com.windaka.suizhi.mpi.websocket.WebSocketMes;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * 公安内网消息推从
 * 从数据库定时取数据向前端通过websocket推送
 * hjt
 */
@Slf4j
@Service
public class HomeAbnormalTask {
    @Autowired
    CarRecordDao carRecordDao;
    @Autowired
    AbnormalRecordDao abnormalRecordDao;
    @Autowired
    MsgSocketIdDao msgSocketIdDao;
    @Autowired
    MsgPhoneDao msgPhoneDao;
    @Resource
	WebSocketMes webSocketMes;
    int lastId=0;

    public void executeInternal() {
    	log.info("首页推送异常行为任务开始,开始时间:"+ TimesUtil.getServerDateTime(9,new Date()));
          lastId=msgSocketIdDao.queryMsgSocketMaxId("record_abnormal");
         List<RecordAbnormal> abnormalRecordList = abnormalRecordDao.queryAbnormalRecordById(lastId);
         abnormalRecordList.forEach(temp -> {
            /* File file=new File(PropertiesUtil.getLocalTomcatImageIp()+temp.getImg());
             if(file.exists()){//hjt*/
                 temp.setImg(PropertiesUtil.getLocalTomcatImageIp()+temp.getImg());
                 temp.setTimeStamp(System.currentTimeMillis()+"");
                 temp.setCaptureTimeStr(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(temp.getCaptureTime()));
                 // 取消车辆占道和流浪动物弹窗
//                 webSocketMes.sendMessages(JSON.toJSONString(temp));
             /*}else{//gongan
                 try{
                     if(QueueUtils.getQueueAbnormal()!=null || QueueUtils.getQueueAbnormal().size()==QueueUtils.FILE_QUEUE_SIZE)
                         QueueUtils.getQueueAbnormal().take();
                     QueueUtils.put(temp);//放入队列等待定时任务推送
                 }catch (Exception e){
                     e.printStackTrace();
                 }
             }*/
             log.info("QUEUE_POLICE_FACEALARM*******************发送流浪动物短信开始");
             List<String> phones=msgPhoneDao.queryMsgPhoneList();
             try {
                 SendMessage.send(phones,
                         "黄色预警：发现流浪动物\n事件类型：流浪动物\n出现地点："+temp.getLocation()+"\n抓拍时间："+temp.getCaptureTimeStr());
                 log.info("发送流浪动物短信成功");
             } catch (IOException e) {
                 log.error("发送流浪动物短信失败");
                 e.printStackTrace();
             }
             log.info("QUEUE_POLICE_FACEALARM*******************发送流浪动物短信结束");
             lastId=temp.getId();
         });
        Map<String,Object> params=new HashMap<>();
        if(abnormalRecordList.size()>0){
        	params.put("recordName","record_abnormal");
            params.put("maxId",lastId);
            msgSocketIdDao.updateMsgSocketMaxId(params);
        }
        log.info("首页推送异常行为任务结束,结束时间:"+ TimesUtil.getServerDateTime(9,new Date()));
    }
}
