package com.windaka.suizhi.mpi.task;

import com.alibaba.fastjson.JSON;
import com.windaka.suizhi.common.utils.PropertiesUtil;
import com.windaka.suizhi.common.utils.TimesUtil;
import com.windaka.suizhi.mpi.dao.AbnormalRecordDao;
import com.windaka.suizhi.mpi.dao.MsgSocketIdDao;
import com.windaka.suizhi.mpi.model.PersonInOut;
import com.windaka.suizhi.mpi.utils.QueueUtils;
import com.windaka.suizhi.mpi.websocket.WebSocketPersonMes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

@Slf4j
@Service
public class PersonInOutTask {
    @Autowired
    AbnormalRecordDao abnormalRecordDao;
    
    @Resource
	WebSocketPersonMes webSocketPersonMes;
    
    @Resource
    MsgSocketIdDao msgSocketIdDao;
    
    int lastId=0;

    public void executeInternal(){
        //log.info("人员进出任务开始,开始时间:"+ TimesUtil.getServerDateTime(9,new Date()));
         lastId=msgSocketIdDao.queryMsgSocketMaxId("person_in_out");
         List<PersonInOut> personInOutList = abnormalRecordDao.queryPersonInOut(lastId);
         personInOutList.forEach(temp -> {
             temp.setTimeStamp(System.currentTimeMillis()+"");
             /*File file=new File(PropertiesUtil.getLocalTomcatImageIp()+temp.getImg());//gongan
             if(file.exists()){//hjt*/
                 temp.setImg(PropertiesUtil.getLocalTomcatImageIp()+temp.getImg());
                 webSocketPersonMes.sendMessages(JSON.toJSONString(temp));
             /*}else{ //gongan
                 try{
                     if(QueueUtils.getQueuePerson()!=null || QueueUtils.getQueuePerson().size()==QueueUtils.FILE_QUEUE_SIZE)
                         QueueUtils.getQueuePerson().take();
                     QueueUtils.put(temp);//放入队列等待定时任务推送
                 }catch (Exception e){
                     e.printStackTrace();
                 }
             }*/
             lastId=temp.getId();
         });
        Map<String,Object> params=new HashMap<>();
        if(personInOutList.size()>0){
        	params.put("recordName","person_in_out");
            params.put("maxId",lastId);
            msgSocketIdDao.updateMsgSocketMaxId(params);
        }
        //log.info("人员进出任务结束,结束时间:"+ TimesUtil.getServerDateTime(9,new Date()));
    }
}
