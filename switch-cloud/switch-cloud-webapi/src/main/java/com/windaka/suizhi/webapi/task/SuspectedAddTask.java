package com.windaka.suizhi.webapi.task;

import com.windaka.suizhi.webapi.dao.CarStatisticsDao;
import com.windaka.suizhi.webapi.dao.MsgSuspectedAddDao;
import com.windaka.suizhi.webapi.dao.PersonDao;
import com.windaka.suizhi.webapi.dao.SingleTableDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 疑似新增人员/车辆定时任务触发方法 hjt
 * 查询出疑似新增的人或车将信息插入到msg_suspected_add表
 * 若是在15天之内已经存在该条记录，则不插入（保证发送一次，并且超过阈值15天后重新发送）
 */
@Slf4j
@Service
public class SuspectedAddTask {
    @Autowired
    private PersonDao personDao;
    @Autowired
    private CarStatisticsDao carStatisticsDao;
    @Autowired
    private MsgSuspectedAddDao msgSuspectedAddDao;
    @Autowired
    private SingleTableDao singleTableDao;

    public void executeInternal() {
        log.info("SuspectedAddTask**************开始");

        //若是在15天之内已经存在该条记录，则不插入（保证发送一次，并且超过阈值15天后重新发送）
        msgSuspectedAddDao.updateMsgSuspectedAddMoreThan15Days();

//处理疑似新增人
        Map innerParams = new HashMap();
        List<Map<String, Object>> personlists = personDao.personAddedList(innerParams);//获得疑似新增人员列表

        for (Map<String, Object> personList : personlists) {
            String personId = "";
            String xqCode = "";
            String senseDays = "";
            if (personList.get("personId") != null && !personList.get("personId").toString().trim().equals("")) {

                personId = personList.get("personId").toString();//personId
                innerParams = new HashMap();
                innerParams.put("personId", personId);

                List<Map<String, Object>> personAttrs = personDao.getPersonAttrByPersonId(innerParams);//xqCode
                Map<String, Object> personAttr = personAttrs.get(0);
                if (personAttr.get("xqCode") != null && !personAttr.get("xqCode").toString().trim().equals("")) {
                    xqCode = personAttr.get("xqCode").toString();
                }

                senseDays = personList.get("capDays").toString();

                innerParams = new HashMap();
                innerParams.put("personId", personId);
                innerParams.put("senseDays",senseDays);
                innerParams.put("xqCode", xqCode);
                innerParams.put("type", 0);
                int count = msgSuspectedAddDao.seleteMsgSuspectedAdd(innerParams);
                if (count == 0) {//判断msgSuspectedAdd表 是否存在此人  不存在则插入
                    msgSuspectedAddDao.insertMsgSuspectedAdd(innerParams);
                }
            }
        }

        //处理疑似新增车
        innerParams = new HashMap();
        List<Map<String, Object>> carLists = carStatisticsDao.queryCarCapDaysWithIn15Days(innerParams);
        Iterator carListsI=carLists.iterator();
        while (carListsI.hasNext())
        {
            String carNum="";
            String xqCode="";
            String senseDays = "";
            Map carListMap=(Map)carListsI.next();
            if(carListMap.get("car_num")!=null&&!carListMap.get("car_num").toString().trim().equals(""))
            {
                carNum=carListMap.get("car_num").toString();
                innerParams=new HashMap();
                innerParams.put("carNum",carNum);
                List caIdList=singleTableDao.selectCarAttributeMaxId(innerParams);
                String id="";
                if(caIdList!=null&&!caIdList.isEmpty())
                {
                    Map caIdMap=(Map)caIdList.get(0);
                    if(caIdMap.get("id")!=null&&!caIdMap.get("id").toString().trim().equals(""))
                    {
                        id=caIdMap.get("id").toString();
                    }
                }
                innerParams=new HashMap();
                innerParams.put("id",id);
                List singleCarAttributeList=singleTableDao.selectCarAttribute(innerParams);
                if(singleCarAttributeList!=null&&!singleCarAttributeList.isEmpty())
                {
                    Map caMap=(Map)singleCarAttributeList.get(0);
                    if(caMap.get("xq_code")!=null&&!caMap.get("xq_code").toString().trim().equals(""))
                    {
                        xqCode=caMap.get("xq_code").toString();
                    }
                }

                senseDays = carListMap.get("pass_num").toString();

                innerParams = new HashMap();
                innerParams.put("carNum", carNum);
                innerParams.put("senseDays",senseDays);
                innerParams.put("xqCode", xqCode);
                innerParams.put("type", 1);
                int count = msgSuspectedAddDao.seleteMsgSuspectedAdd(innerParams);
                if (count == 0) {//判断msgSuspectedAdd表 是否存在此人  不存在则插入
                    msgSuspectedAddDao.insertMsgSuspectedAdd(innerParams);
                }
            }
        }

        log.info("SuspectedAddTask**************结束");
    }
}
