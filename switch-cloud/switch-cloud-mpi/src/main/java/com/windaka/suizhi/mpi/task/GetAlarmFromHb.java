package com.windaka.suizhi.mpi.task;

import com.alibaba.fastjson.JSON;
import com.windaka.suizhi.common.utils.TimesUtil;
import com.windaka.suizhi.mpi.config.OracleConfig;
import com.windaka.suizhi.mpi.dao.DictDao;
import com.windaka.suizhi.mpi.dao.MsgPhoneDao;
import com.windaka.suizhi.mpi.dao.PersonDao;
import com.windaka.suizhi.mpi.message.SendMessage;
import com.windaka.suizhi.mpi.model.RecordAbnormal;
import com.windaka.suizhi.mpi.websocket.WebSocketMes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * @ClassName GetAlarmFromHb
 * @Description 从海博视图中获取报警弹窗并发送短信
 * @Author lixianhua
 * @Date 2020/1/10 18:56
 * @Version 1.0
 */
@Service
@Slf4j
public class GetAlarmFromHb {
    // 运行状态
    private Boolean running = false;

    @Autowired
    WebSocketMes webSocketMes;

    @Autowired
    MsgPhoneDao msgPhoneDao;

    @Autowired
    DictDao dictDao;


    public void run() {
        log.info("********************* 从海博视图中获取报警记录任务开始，开始时间为:" + TimesUtil.getServerDateTime(9, new Date()) + "**********************");
        if (!running) {
            running = true;
            try {
                // 查询现有身份证集合
                List<String> idNoList = dictDao.getIdNoList();
                Map<String ,Object> condition = new HashMap<>(8);
                condition.put("location","青岛开发区凤凰城%");
//                condition.put("location","王%");
                if(null!=idNoList && idNoList.size()>0){
                    String str1 ="'" + StringUtils.join(idNoList.toArray(), "','") + "'";
                    condition.put("idNoStr",str1);
                }
                // 从视图中获取报警信息
                List<Map<String, String>> list = OracleConfig.query(condition);
                if (null != list && list.size() > 0) {
                    for(Map<String,String> map :list){
                        RecordAbnormal record = new RecordAbnormal();
                        String colorName = "橙色";
                        record.setEvent("9");
                        record.setPersonName(map.get("personName"));
                        record.setCaptureTimeStr(map.get("captureTime"));
                        record.setLocation(map.get("location"));
                        record.setSimilar(map.get("similar"));
                        record.setGroupName(map.get("groupName"));
                        String resultJson =  JSON.toJSONString(record);
                        // 发送弹窗数据
                        webSocketMes.sendMessages(resultJson);
                        // 发送短信
                        log.info("QUEUE_POLICE_FACEALARM*******************发送海博人脸报警短信开始");
                        List<String> phones=msgPhoneDao.queryMsgPhoneList();
                        SendMessage.send(phones,colorName+"预警：发现"+map.get("groupName")+"人员\n姓名："+map.get("personName")+"\n证件号码："+map.get("idNo")+"\n出现地点："+map.get("location")+"\n抓拍时间："+map.get("captureTime"));
                        log.info("QUEUE_POLICE_FACEALARM*******************发送海博人脸报警短信结束");
                        // 添加记录到临时表中
                        this.dictDao.insertHbRecord(map.get("idNo"));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                log.info("");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                log.info("");
            }catch (IOException e) {
                e.printStackTrace();
                log.info("");
            }
            running = false;
        }
        log.info("********************* 从海博视图中获取报警记录任务结束，结束时间为:" + TimesUtil.getServerDateTime(9, new Date()) + "**********************");
    }
}
