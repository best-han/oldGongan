package com.windaka.suizhi.mpi.task;

import com.alibaba.fastjson.JSON;
import com.windaka.suizhi.common.utils.PropertiesUtil;
import com.windaka.suizhi.mpi.dao.CarAccessRecordDao;
import com.windaka.suizhi.mpi.dao.MsgSocketIdDao;
import com.windaka.suizhi.mpi.model.CarInOut;
import com.windaka.suizhi.mpi.utils.QueueUtils;
import com.windaka.suizhi.mpi.websocket.WebSocketCarMes;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CarInOutTask {
    @Autowired
    CarAccessRecordDao carAccessRecordDao;

    @Resource
    MsgSocketIdDao msgSocketIdDao;

    @Autowired
    private WebSocketCarMes webSocketCarMes;

    int lastId = 0;

    public void executeInternal() throws Exception {
    	log.info("车辆进出定时任务开启。。。。。");
        lastId = msgSocketIdDao.queryMsgSocketMaxId("car_access_record");
        List<CarInOut> carInOutList = carAccessRecordDao.queryCarInOut(lastId);
        carInOutList.forEach(temp -> {
            temp.setTimeStamp(System.currentTimeMillis() + "");
            // 抓拍图像
           /* File file=new File(PropertiesUtil.getLocalTomcatImageIp() + temp.getImg());//gongan
            if(file.exists()){//若图片存在，则推送  hjt*/
                temp.setImg(PropertiesUtil.getLocalTomcatImageIp()+temp.getImg());
                webSocketCarMes.sendMessages(JSON.toJSONString(temp));
           /* }else{//gongan
                try{
                    if(QueueUtils.getQueueCar()!=null || QueueUtils.getQueueCar().size()==QueueUtils.FILE_QUEUE_SIZE)
                        QueueUtils.getQueueCar().take();//若满了则闪掉先进第一个
                    QueueUtils.put(temp);//放入队列等待定时任务推送
                }catch (Exception e){
                    e.printStackTrace();
                }
            }*/
            lastId = temp.getId();
        });
        Map<String, Object> params = new HashMap<>();
        if (carInOutList.size() > 0) {
            params.put("recordName", "car_access_record");
            params.put("maxId", lastId);
            msgSocketIdDao.updateMsgSocketMaxId(params);
        }
    }
}
