package com.windaka.suizhi.mpi.task;

import com.alibaba.fastjson.JSON;
import com.windaka.suizhi.common.constants.CommonConstants;
import com.windaka.suizhi.common.utils.PropertiesUtil;
import com.windaka.suizhi.mpi.dao.CarAlarmDao;
import com.windaka.suizhi.mpi.model.CarInOut;
import com.windaka.suizhi.mpi.model.Person;
import com.windaka.suizhi.mpi.model.PersonInOut;
import com.windaka.suizhi.mpi.model.RecordAbnormal;
import com.windaka.suizhi.mpi.utils.QueueUtils;
import com.windaka.suizhi.mpi.websocket.WebSocketCarMes;
import com.windaka.suizhi.mpi.websocket.WebSocketMes;
import com.windaka.suizhi.mpi.websocket.WebSocketPersonMes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class QueueMsgTask {

    @Resource
    WebSocketPersonMes webSocketPersonMes;
    @Autowired
    private WebSocketCarMes webSocketCarMes;
    @Resource
    WebSocketMes webSocketMes;
    @Resource
    private CarAlarmDao carAlarmDao;
    @Resource
    private PersonInfoIsCrimeTask personInfoIsCrimeTask;

    public void executeInternal() {
        log.info("***************QueueUtils.getQueuePerson()"+ QueueUtils.getQueuePerson().size());
        for(PersonInOut personInOut:QueueUtils.getQueuePerson()){
            String imgPath= CommonConstants.GONGAN_IMAGE_PATH+File.separator+"image"+File.separator+personInOut.getImg();
            log.info("*****************************PersonInOut,socketImgPath:"+imgPath);
            File file=new File(imgPath);
            if(file.exists()){
                log.info("*****************************PersonInOut,socketImgPath2:"+imgPath);
                personInOut.setImg(PropertiesUtil.getLocalTomcatImageIp()+personInOut.getImg());
                webSocketPersonMes.sendMessages(JSON.toJSONString(personInOut));
                QueueUtils.getQueuePerson().remove(personInOut);

            }
        }
        log.info("***************QueueUtils.getQueueCar()"+QueueUtils.getQueueCar().size());
        for(CarInOut carInOut:QueueUtils.getQueueCar()){
            String imgPath=CommonConstants.GONGAN_IMAGE_PATH+File.separator+"image"+File.separator+carInOut.getImg();
            log.info("*****************************CarInOut,socketImgPath:"+imgPath);
            File file=new File(imgPath);
            if(file.exists()){
                log.info("*****************************CarInOut,socketImgPath2:"+imgPath);
                carInOut.setImg(PropertiesUtil.getLocalTomcatImageIp()+carInOut.getImg());
                webSocketCarMes.sendMessages(JSON.toJSONString(carInOut));
                QueueUtils.getQueueCar().remove(carInOut);

            }
        }
        log.info("***************QueueUtils.getQueueAbnormal()"+QueueUtils.getQueueAbnormal().size());
        for(RecordAbnormal recordAbnormal:QueueUtils.getQueueAbnormal()){
            String imgPath=CommonConstants.GONGAN_IMAGE_PATH+File.separator+"image"+File.separator+recordAbnormal.getImg();
            log.info("*****************************RecordAbnormal,socketImgPath:"+imgPath);
            File file=new File(imgPath);
            if(file.exists()){
                log.info("*****************************RecordAbnormal,socketImgPath2:"+imgPath);
                recordAbnormal.setImg(PropertiesUtil.getLocalTomcatImageIp()+recordAbnormal.getImg());
                // event为5，6，7则为车辆报警，需要往车辆报警表中添加纪录
                String event = recordAbnormal.getEvent();
                if("5".equals(event)||"6".equals(event)||"7".equals(event)){
                    Map<String, Object> model = new HashMap<>();
                    model.put("xqCode", recordAbnormal.getXqCode());
                    model.put("capTime", recordAbnormal.getCaptureTime());
                    model.put("carNumber", recordAbnormal.getCarNum());
                    model.put("clStatus", "0");
                    model.put("carCaptureId",recordAbnormal.getId());
                    model.put("status", "0");
                    model.put("creTime", new Date());
                    model.put("carGroupCode",recordAbnormal.getType());
                    model.put("carGroupName",recordAbnormal.getGroupName());
                    model.put("base64Img", recordAbnormal.getCaptureImg());
                    model.put("deviceName", recordAbnormal.getDevName());
                    model.put("deviceId",recordAbnormal.getDevId());
                    this.carAlarmDao.insertRecord(model);
                }
                webSocketMes.sendMessages(JSON.toJSONString(recordAbnormal));
                QueueUtils.getQueueAbnormal().remove(recordAbnormal);

            }
        }

        log.info("***************QueueUtils.getQueuePersonInfoIsCrime()"+QueueUtils.getQueuePersonInfoIsCrime().size());
        for(Person person:QueueUtils.getQueuePersonInfoIsCrime()){
            if(personInfoIsCrimeTask.personImageToFaceCrime(person)){
                QueueUtils.getQueueCar().remove(person);
            }
        }
    }
}
