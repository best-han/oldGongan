package com.windaka.suizhi.manageport.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.SqlFileUtil;
import com.windaka.suizhi.manageport.dao.*;
import com.windaka.suizhi.manageport.service.HouseOwnerRoomService;
import com.windaka.suizhi.manageport.util.SqlCreateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Service
public class HouseOwnerRoomServiceImpl implements HouseOwnerRoomService {

    @Autowired
    HouseOwnerRoomDao houseOwnerRoomDao;

    @Autowired
    private PersonInfoDao personInfoDao;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private HouseRoomDao houseRoomDao;

    @Autowired
    private HyPersonDataDao hyPersonDataDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveHouseOwnerRooms(Map<String, Object> params) throws OssRenderException, IOException {

        String xqCode = (String) params.get("xqCode");
        if (StringUtils.isBlank(xqCode)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "小区Code不能为空");
        }
        List<Map<String, Object>> list = (List<Map<String, Object>>) params.get("list");

        if (CollectionUtils.isEmpty(list)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "数据为空");
        }
//        list.get(0).put("decorationTime",new Date());
        // 添加业主房产信息表
        houseOwnerRoomDao.saveHouseOwnerRooms(xqCode, list);

        // 添加烟台专用人员记录
        for (Map<String, Object> map : list) {
            String id = UUID.randomUUID().toString();
            Map<String, Object> personMap = new HashMap<>(8);
            personMap.put("manageId", map.get("ownerId"));
            // 根据manageId获取业主信息
            List<Map<String, Object>> personList = personDao.getPersonListPure(personMap);
            Map<String, Object> roomMap = new HashMap<>(8);
            roomMap.put("manageId", map.get("roomId"));
            // 根据roomId获取房产信息
            List<Map<String, Object>> roomList = this.houseRoomDao.getRoomListPure(roomMap);
            boolean flag = personList == null || personList.size() == 0 || roomList == null || roomList.size() == 0;
            if (flag) {
                continue;
            }
            personMap = personList.get(0);
            roomMap = roomList.get(0);
            map.put("id", id);
            map.put("name", map.get("ownerName"));
            map.put("paperType", personMap.get("paperType"));
            map.put("paperNum", personMap.get("paperNum"));
            map.put("sex", personMap.get("sex"));
            map.put("birthday", personMap.get("birthday"));
            map.put("nation", personMap.get("nationName"));
            map.put("address", personMap.get("orgionPlace"));
            map.put("phone", personMap.get("phone"));
            if (null != map.get("liveType")) {
                // 业主和家庭成员属于常住人口
                if ("1".equals(map.get("liveType").toString()) || "2".equals(map.get("liveType").toString())) {
                    map.put("typename", "常住人口");
                    // 租户属于流动人口
                } else if ("3".equals(map.get("liveType").toString())) {
                    map.put("typename", "流动人口");
                }
            }
            map.put("marriage", roomMap.get("marriageStatus"));
            map.put("education", personMap.get("educationName"));
            map.put("political", personMap.get("political_status_name"));
            // 添加人员信息
            Integer num = personInfoDao.insertPerson(map);
            if (num < 1) {
                throw new OssRenderException(ReturnConstants.CODE_FAILED, "数据添加异常");
            }

            //添加 Hy 人员信息////////////////////////////////////////////
            if (personMap.get("extend_s6").equals("1")) {           //外国人不入Hy
                String addr_code = null;
                addr_code = roomMap.get("addr_code").toString();
                personMap.put("addr_code", addr_code);

                if (null != map.get("liveType")) {
                    // 业主和家庭成员属于常住人口
                    if ("1".equals(map.get("liveType").toString()) || "2".equals(map.get("liveType").toString())) {
                        personMap.put("typeName", "常住人口");
                        // 租户属于流动人口
                    } else if ("3".equals(map.get("liveType").toString())) {
                        personMap.put("typeName", "流动人口");
                    }
                }

                Map<String, Object> hyPerson = new HashMap<String, Object>();
                String hyId = UUID.randomUUID().toString();
                hyPerson.put("id", hyId);
                hyPerson.put("name", personMap.get("name"));
                hyPerson.put("paperType", personMap.get("paper_type_name"));
                hyPerson.put("paperNumber", personMap.get("paper_number"));
                hyPerson.put("sex", personMap.get("sex_name"));
                hyPerson.put("birthday", personMap.get("birthday"));
                hyPerson.put("nation", personMap.get("nation_name"));
                hyPerson.put("address", personMap.get("orgion_place"));
                hyPerson.put("phone", personMap.get("phone"));
                hyPerson.put("typeName", personMap.get("typeName"));
                hyPerson.put("addressCode", personMap.get("addr_code"));//标准地址编码
                hyPerson.put("marriage", personMap.get("marriage_status_name"));
                hyPerson.put("education", personMap.get("education_name"));
                hyPerson.put("political", personMap.get("political_status_name"));
                hyPerson.put("fulladdress", personMap.get("address"));
                int i = 0;
                i = hyPersonDataDao.saveHyPerson(hyPerson);
                if (i < 1) {
                    throw new OssRenderException(ReturnConstants.CODE_FAILED, "Hy数据添加异常");
                }
                String[] colNames = {"name", "paper_type_name", "paper_number", "sex_name", "birthday", "nation_name", "orgion_place", "phone",
                        "person_identity_name", "addr_code", "marriage_status_name", "education_name", "political_status_name", "address"};
                String[] colValues = new String[colNames.length];
                int n = colNames.length;
                for (int j = 0; j < n; j++) {
                    colValues[j] = SqlFileUtil.keyAddValue(personMap, colNames[j]);
                }
                String sql = "insert into hy_person_data (id,name,paper_type,paper_num,sex,birthday,nation,address,phone,\n" +
                        "        typename,addresscode,marriage,education,political,fulladdress,cre_time,upd_time) values (\n" +
                        "        '" + hyId + "',\n" +
                        "        " + colValues[0] + ",\n" +
                        "        " + colValues[1] + ",\n" +
                        "        " + colValues[2] + ",\n" +
                        "        " + colValues[3] + ",\n" +
                        "        " + colValues[4] + ",\n" +
                        "        " + colValues[5] + ",\n" +
                        "        " + colValues[6] + ",\n" +
                        "        " + colValues[7] + ",\n" +
                        "        " + colValues[8] + ",\n" +
                        "        " + colValues[9] + ",\n" +
                        "        " + colValues[10] + ",\n" +
                        "        " + colValues[11] + ",\n" +
                        "        " + colValues[12] + ",\n" +
                        "        " + colValues[13] + ",\n" +
                        "        now(),\n" +
                        "        now())";
                SqlFileUtil.InsertSqlToFile(sql);
            }

        }


        String[] keyNames = {"xqCode", "manageId", "ownerId", "ownerName",
                "roomId", "useStatus", "checkinTime", "decorationTime", "subscribeNo",
                "housePropertyCardNum", "isPayMaintenanceFund", "preMaintenanceFund", "remark", "orderId",
                "status", "createBy", "createDate", "updateBy", "updateDate",
                "remarks", "liveType", "ownerRelation",
                "liveTypeName", "ownerRelationName", "residence"};

        String[] keyValues = new String[keyNames.length];

        for (Map<String, Object> map : list) {
            map.put("xqCode", xqCode);
            for (int i = 0; i < keyNames.length; i++) {
                keyValues[i] = SqlFileUtil.keyAddValue(map, keyNames[i]);
            }

            String saveHouseOwnerRooms = "insert into house_owner_room (xq_code,manage_id,\n" +
                    "        `owner_id` ,\n" +
                    "        `owner_name` ,\n" +
                    "        `room_id` ,\n" +
                    "        `use_status` ,\n" +
                    "        `checkin_time` ,\n" +
                    "        `decoration_time` ,\n" +
                    "        `subscribe_no` ,\n" +
                    "        `house_property_card_num` ,\n" +
                    "        `is_pay_maintenance_fund` ,\n" +
                    "        `pre_maintenance_fund` ,\n" +
                    "        `remark` ,\n" +
                    "        `order_id` ,\n" +
                    "        `status` ,\n" +
                    "        `create_by` ,\n" +
                    "        `create_date` ,\n" +
                    "        `update_by` ,\n" +
                    "        `update_date` ,\n" +
                    "        `remarks` ,\n" +
                    "        `live_type` ,\n" +
                    "        `owner_relation`,\n" +
                    "        `live_type_name` ,\n" +
                    "        `owner_relation_name`,\n" +
                    "        residence\n" +
                    "        )\n" +
                    "        values(";
            for (int i = 0; i < keyNames.length; i++) {

                if (i == keyNames.length - 1) {
                    saveHouseOwnerRooms += keyValues[i] + ")";
                } else {
                    saveHouseOwnerRooms += keyValues[i] + ",";
                }
            }
            SqlFileUtil.InsertSqlToFile(saveHouseOwnerRooms);
        }


    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateHouseOwnerRoom(Map<String, Object> params) throws OssRenderException, IOException {
        houseOwnerRoomDao.updateHouseOwnerRoom(params);
        String updateHouseOwnerRoom = SqlCreateUtil.getSqlByMybatis(HouseOwnerRoomDao.class.getName() + ".updateHouseOwnerRoom", params);
        SqlFileUtil.InsertSqlToFile(updateHouseOwnerRoom);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteHouseOwnerRoom(Map<String, Object> params) throws OssRenderException, IOException {
        houseOwnerRoomDao.deleteHouseOwnerRoom(params);
        String deleteHouseOwnerRoom = SqlCreateUtil.getSqlByMybatis(HouseOwnerRoomDao.class.getName() + ".deleteHouseOwnerRoom", params);
        SqlFileUtil.InsertSqlToFile(deleteHouseOwnerRoom);

        //删除Hy 人员信息  根据paperNum 删除人员
        String manageId = params.get("manageId").toString();
        if (manageId == null || manageId.equals("")) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "Hy-----缺少manageId参数");
        }

        Map<String, Object> personMap = new HashMap<String, Object>();
        personMap.put("manageId", manageId);
        List<Map<String, Object>> personList = personDao.getPersonListPure(personMap);
        personMap = personList.get(0);

        String paperNum = null;
        paperNum = personMap.get("paper_number").toString();  //获得paperNum
        if (paperNum == null || paperNum.equals("")) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "Hy-----缺少paperNum参数");
        }

        int i = 0;
        i = hyPersonDataDao.deleteHyPerson(paperNum);
        if (i < 1) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "Hy数据删除异常");
        }
        String sql = "delete from hy_person_data where paper_num = '" + paperNum + "'";
        SqlFileUtil.InsertSqlToFile(sql);

    }
}
