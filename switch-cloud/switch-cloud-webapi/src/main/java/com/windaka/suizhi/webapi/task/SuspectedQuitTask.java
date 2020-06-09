package com.windaka.suizhi.webapi.task;

import com.windaka.suizhi.webapi.dao.MsgSuspectedQuitDao;
import com.windaka.suizhi.webapi.dao.PersonDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 疑似迁出人员定时任务触发方法 hjt
 * 查询出疑似迁出的人将信息插入到msg_suspected_quit表
 * 超过阈值后，每天执行一次，每天插入一条
 */
@Slf4j
@Service
public class SuspectedQuitTask {
    @Autowired
    private PersonDao personDao;
    @Autowired
    private MsgSuspectedQuitDao msgSuspectedQuitDao;

    public void executeInternal() {
        log.info("SuspectedQuitTask**************开始");

        //疑似迁出人
        Map<String, Object> innerParams;
        innerParams = new HashMap();
        List<Map<String, Object>> personQuitlists = personDao.quitPersonAndCarList(innerParams);//疑似迁出人列表

        for (Map<String, Object> personQuitList : personQuitlists) {
            String personId = "";
            String xqCode = "";
            String noSenseDay = "";
            if (personQuitList.get("personId") != null && !personQuitList.get("personId").toString().trim().equals("")) {
                personId = personQuitList.get("personId").toString();//personId
            }
            if (personQuitList.get("xqCode") != null && !personQuitList.get("xqCode").toString().trim().equals("")) {
                xqCode = personQuitList.get("xqCode").toString();//xqCode
            }
            if (personQuitList.get("noSenceTimeMin") != null && !personQuitList.get("noSenceTimeMin").toString().trim().equals("")) {
                noSenseDay = personQuitList.get("noSenceTimeMin").toString();//xqCode
            }

            innerParams = new HashMap();
            innerParams.put("personId", personId);
            innerParams.put("xqCode", xqCode);
            innerParams.put("noSenseDay", noSenseDay);
            int count = msgSuspectedQuitDao.selectMsgSuspectedQuit(innerParams);//根据personId判断是否存在此记录
            if (count == 0) {//判断msgSuspectedQuit表 是否存在此人  不存在则插入
                msgSuspectedQuitDao.insertMsgSuspectedQuit(innerParams);
            }
            /*int count = msgSuspectedQuitDao.selectMsgSuspectedQuit2(innerParams);//根据 personId && noSenseDay 判断是否存在此记录
            if (count == 0) {//判断msgSuspectedQuit表 是否存在此记录  不存在则插入
                msgSuspectedQuitDao.insertMsgSuspectedQuit(innerParams);
            }*/
        }
        log.info("SuspectedQuitTask**************结束");
    }
}

