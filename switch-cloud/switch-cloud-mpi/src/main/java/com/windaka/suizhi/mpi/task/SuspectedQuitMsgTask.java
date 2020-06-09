package com.windaka.suizhi.mpi.task;


import com.windaka.suizhi.mpi.dao.MsgPhoneDao;
import com.windaka.suizhi.mpi.dao.MsgSuspectedQuitDao;
import com.windaka.suizhi.mpi.dao.PersonDao;
import com.windaka.suizhi.mpi.dao.RoomDao;
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
 * 疑似迁出人员提示短信发送
 */
@Slf4j
@Service
public class SuspectedQuitMsgTask {

    @Autowired
    MsgSuspectedQuitDao msgSuspectedQuitDao;
    @Autowired
    MsgPhoneDao msgPhoneDao;
    @Autowired
    PersonDao personDao;
    @Autowired
    RoomDao roomDao;

    public void executeInternal() {
        Map<String,Object> params=new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE,-1);
        params.put("createTime",sdf.format(yesterday.getTime()));//昨天时间
        params.put("msgStatus","0");//未发送
        List<Map<String,Object>> personList=msgSuspectedQuitDao.queryMsgSuspectedQuitList(params);
        List<String> phones=msgPhoneDao.queryMsgPhoneList();
        for(Map<String,Object> temp:personList){
            Map<String,Object> map=personDao.queryPerson(temp.get("personId").toString());
            if(map==null) continue;
            //房屋名------------------
            params.put("xqCode",temp.get("xqCode"));
            params.put("ownerId",temp.get("personId"));
            List<Map<String,Object>> roomList=roomDao.queryXqRoomIdByOwnerId(params);
            String roomNames="";
            for(Map map1:roomList){
                roomNames+=roomDao.queryRoomNameByManageId(map1.get("roomId").toString())+";";
            }
            if("".equals(roomNames)) roomNames="无";
            try{
                //log.info("SuspectedQuitMsgTask*******************开始发送疑似迁出人员短信");
                SendMessage.send(phones,"智能预警：疑似迁出人员\n姓名："+map.get("name")
                        +"\n身份证号："+map.get("paperNumber")+"\n电话号码："+map.get("phone")
                        +"\n小区："+map.get("xqName")
                        +"\n房屋："+roomNames
                        +"\n未感知时长："+temp.get("noSenseDay")+"天");
               /* System.out.println(""智能预警：疑似迁出人员\n姓名："+map.get("name")
                        +"\n身份证号："+map.get("paperNumber")+"\n电话号码："+map.get("phone")
                        +"\n小区："+map.get("xqName")
                        +"\n房屋："+roomNames
                        +"\n未感知时长："+temp.get("noSenseDay")+"天");*/
                msgSuspectedQuitDao.updateMsgSuspectedQuitById(Integer.parseInt(temp.get("id")+""));
                //log.info("SuspectedQuitMsgTask*******************发送疑似迁出人员短信结束");
            }catch(Exception e){
                e.printStackTrace();
                log.error("SuspectedQuitMsgTask似迁出人员短信发送失败");
            }
        }
    }
}
