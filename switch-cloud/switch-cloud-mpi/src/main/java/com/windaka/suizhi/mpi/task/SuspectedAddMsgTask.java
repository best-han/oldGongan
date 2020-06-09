package com.windaka.suizhi.mpi.task;


import com.windaka.suizhi.mpi.dao.FacePersonAttrDao;
import com.windaka.suizhi.mpi.dao.MsgPhoneDao;
import com.windaka.suizhi.mpi.dao.MsgSuspectedAddDao;
import com.windaka.suizhi.mpi.message.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 疑似新增人/车提示短信发送
 */
@Slf4j
@Service
public class SuspectedAddMsgTask {

    @Autowired
    MsgSuspectedAddDao msgSuspectedAddDao;
    @Autowired
    MsgPhoneDao msgPhoneDao;
    @Autowired
    FacePersonAttrDao facePersonAttrDao;

    public void executeInternal() {
        Map<String,Object> params=new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE,-1);
        params.put("createTime",sdf.format(yesterday.getTime()));//昨天时间
        params.put("type","0");//人
        params.put("msgStatus","0");//未发送
        List<Map<String,Object>> personList=msgSuspectedAddDao.queryMsgSuspectedAddList(params);
        params.put("type","1");//车
        List<Map<String,Object>> carList=msgSuspectedAddDao.queryMsgSuspectedAddList(params);
        List<String> phones=msgPhoneDao.queryMsgPhoneList();
        for(Map temp:personList){
            Map<String,Object> map=facePersonAttrDao.queryByPersonId(temp.get("personId").toString());//陌生人员查询不到
            if(map==null) continue;
            try{
                //log.info("SuspectedAddMsgTask*******************开始发送疑似新增人员短信");
              //  String sex=("1").equals(map.get("sex"))?"男":"女";
              /*  SendMessage.send(phones,"智能预警：疑似新增人员"
                      //  +"\n姓名：陌生人"
                      //  +"\n年龄："+map.get("age")
                      //  +"\n性别："+sex
                        +"\n小区："+map.get("xqName")
                        +"\n15天内感知天数："+map.get("senseDays")+"天"
                        +"\n最近一次感知位置："+map.get("deviceName")
                        +"\n最近一次感知时间："+map.get("captureTime")
                );*/
                 System.out.println("智能预警：疑似新增人员"
                                      //  +"\n姓名：陌生人"
                                      //  +"\n年龄："+map.get("age")
                                      //  +"\n性别："+sex
                                        +"\n小区："+map.get("xqName")
                                        +"\n15天内感知天数："+map.get("senseDays")+"天"
                                        +"\n最近一次感知位置："+map.get("deviceName")
                                        +"\n最近一次感知时间："+map.get("captureTime"));
                msgSuspectedAddDao.updateMsgSuspectedAddById(Integer.parseInt(temp.get("id")+""));
                //log.info("SuspectedAddMsgTask*******************发送疑似新增人员短信结束");
            }catch(Exception e){
                e.printStackTrace();
                log.error("SuspectedAddMsgTask似新增人员短信发送失败");
            }
        }
        carList.forEach(temp->{
            //Map<String,Object> map=personDao.queryPerson(temp.get("personId").toString());
            try{
                //log.info("SuspectedAddMsgTask*******************开始发送疑似新增车辆短信");
                /*SendMessage.send(phones,"智能预警：疑似新增车辆"
                        +"\n车牌号："+temp.get("carNum")
                        +"\n小区："+temp.get("xqName")
                        +"\n15天内感知天数："+temp.get("senseDays")+"天"
                );*/
                System.out.println("智能预警：疑似新增车辆"
                        +"\n车牌号："+temp.get("carNum")
                        +"\n小区："+temp.get("xqName")
                        +"\n15天内感知天数："+temp.get("senseDays")+"天");
                msgSuspectedAddDao.updateMsgSuspectedAddById(Integer.parseInt(temp.get("id")+""));
                //log.info("SuspectedAddMsgTask*******************发送疑似新增人员短信结束");
            }catch(Exception e){
                e.printStackTrace();
                log.error("SuspectedAddMsgTask似新增车辆短信发送失败");
            }
        });


    }

    public static void main(String[] args){
        long l=1;
        System.out.println(l+"");
    }
}
