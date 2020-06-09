package com.windaka.suizhi.mpi.task;

import com.alibaba.fastjson.JSON;
import com.windaka.suizhi.common.utils.EnumDateStyle;
import com.windaka.suizhi.common.utils.PropertiesUtil;
import com.windaka.suizhi.common.utils.TimesUtil;
import com.windaka.suizhi.mpi.dao.CarAccessRecordDao;
import com.windaka.suizhi.mpi.dao.CarAlarmDao;
import com.windaka.suizhi.mpi.dao.MsgSocketIdDao;
import com.windaka.suizhi.mpi.model.RecordAbnormal;
import com.windaka.suizhi.mpi.utils.QueueUtils;
import com.windaka.suizhi.mpi.websocket.WebSocketMes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName HomeCarTask
 * @Description 首页车辆报警弹窗定时任务
 * @Author lixianhua
 * @Date 2020/1/7 9:45
 * @Version 1.0
 */
@Slf4j
@Service
public class HomeCarTask {

    @Autowired
    CarAccessRecordDao carAccessRecordDao;

    @Resource
    WebSocketMes webSocketMes;

    @Resource
    MsgSocketIdDao msgSocketIdDao;

    @Autowired
    private CarAlarmDao carAlarmDao;

    public void run() {
        log.info("首页推送车辆报警任务开始,开始时间:" + TimesUtil.getServerDateTime(9, new Date()));
        int lastId = msgSocketIdDao.queryMsgSocketMaxId("car_access_record_home");
        // 获取最大车辆出入id;
        Integer maxId = this.carAccessRecordDao.getMaxId();
        List<RecordAbnormal> carInOutList = carAccessRecordDao.queryCarForAlarm(lastId);
        carInOutList.forEach(temp -> {
            Map<String, Object> map = this.carAlarmDao.getRecordByCarNum(temp.getCarNum());
            boolean insertFlag = true;
            // 查询车辆报警是否存在该车记录
            if (null != map) {
                insertFlag = false;
                // 已处理
                if ("1".equals(map.get("clStatus").toString())) {
                    return;
                }
            }
            String event = "";
            if ("1".equals(temp.getAlarmLevel())) {
                event = "5";
            } else if ("2".equals(temp.getAlarmLevel())) {
                event = "6";
            } else if ("3".equals(temp.getAlarmLevel())) {
                event = "7";
            }
            temp.setEvent(event);
            temp.setTimeStamp(System.currentTimeMillis() + "");
            temp.setLocation(temp.getXqName()+temp.getDevName());
            temp.setCaptureTimeStr(null == temp.getCaptureTime() ? "" : TimesUtil.dateToString(temp.getCaptureTime(), EnumDateStyle.YYYY_MM_DD_HH_MM_SS_CN));
            // 抓拍图像
            File file = new File(PropertiesUtil.getLocalTomcatImageIp() + temp.getImg());//gongan
            if (file.exists()) {//若图片存在，则推送  hjt
                // 将车辆异常信息发送到webSocket
                temp.setImg(PropertiesUtil.getLocalTomcatImageIp() + temp.getImg());
                String message = JSON.toJSONString(temp);
                System.out.println(message);
                webSocketMes.sendMessages(message);
            } else {//gongan
                try {
                    QueueUtils.put(temp);//放入队列等待定时任务推送
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 添加车辆报警记录
            if (insertFlag) {
                Map<String, Object> model = new HashMap<>();
                model.put("xqCode", temp.getXqCode());
                model.put("capTime", temp.getCaptureTime());
                model.put("carNumber", temp.getCarNum());
                model.put("clStatus", "0");
                model.put("alarmLevel",Integer.parseInt(temp.getAlarmLevel()));
                model.put("carCaptureId",temp.getId());
                model.put("status", "0");
                model.put("creTime", new Date());
                model.put("carGroupCode",temp.getType());
                model.put("carGroupName",temp.getGroupName());
                model.put("base64Img", temp.getCaptureImg());
                model.put("deviceName", temp.getDevName());
                model.put("deviceId",temp.getDevId());
                this.carAlarmDao.insertRecord(model);
            }
        });
        Map<String, Object> params = new HashMap<>();
        if (carInOutList.size() > 0) {
            params.put("recordName", "car_access_record_home");
            params.put("maxId", maxId);
            msgSocketIdDao.updateMsgSocketMaxId(params);
        }
        log.info("首页推送车辆报警任务结束,结束时间:" + TimesUtil.getServerDateTime(9, new Date()));
    }
}
