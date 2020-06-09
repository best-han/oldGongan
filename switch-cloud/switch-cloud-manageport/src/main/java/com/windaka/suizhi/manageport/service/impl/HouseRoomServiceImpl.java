package com.windaka.suizhi.manageport.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.SqlFileUtil;
import com.windaka.suizhi.manageport.dao.HouseRoomDao;
import com.windaka.suizhi.manageport.service.HouseRoomService;
import com.windaka.suizhi.manageport.util.SqlCreateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class HouseRoomServiceImpl implements HouseRoomService {

    @Autowired
    HouseRoomDao houseRoomDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveHouseRooms(Map<String, Object> params) throws OssRenderException, IOException {
        String xqCode=(String)params.get("xqCode");
        if(StringUtils.isBlank(xqCode)){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区Code不能为空");
        }
        List<Map<String,Object>> list=(List<Map<String,Object>>) params.get("list");
        if(CollectionUtils.isEmpty(list)){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"数据为空");
        }
        houseRoomDao.saveHouseRooms(xqCode,list);

        String []keyNames={"xqCode","manageId", "cellId", "code",
        "name", "houseTypeCode", "orientationCode", "func", "funcName",
        "decorationNum", "useArea", "buildingAre", "remark", "atticArea",
        "storageArea", "floorNum", "arrearage", "propertyType", "rent",
        "length", "width", "purpose",
        "purposeName", "subordinateCell", "isSaled", "isRented", "saleContractNo",
        "rentContractNo", "coefficient", "status", "createBy", "createDate",
        "updateBy", "updateDate" , "remarks", "decorationTime", "housePropertyCardNo",
        "subscribeNo", "isPayMaintenanceFund", "preMaintenanceFund"};

        String []keyValues=new String[keyNames.length];

        for(Map<String,Object> map:list){
            map.put("xqCode",xqCode);
            for(int i=0;i<keyNames.length;i++){
                keyValues[i]=SqlFileUtil.keyAddValue(map,keyNames[i]);
            }

            String saveHouseRooms="insert into house_room (xq_code,manage_id,\n" +
                    "        `cell_id` ,\n" +
                    "        `code` ,\n" +
                    "        `name` ,\n" +
                    "        `house_type_code` ,\n" +
                    "        `orientation_code` ,\n" +
                    "        `func` ,\n" +
                    "        `func_name` ,\n" +
                    "        `decoration_num` ,\n" +
                    "        `use_area` ,\n" +
                    "        `building_area` ,\n" +
                    "        `remark` ,\n" +
                    "        `attic_area` ,\n" +
                    "        `storage_area` ,\n" +
                    "        `floor_num` ,\n" +
                    "        `arrearage` ,\n" +
                    "        `property_type` ,\n" +
                    "        `rent` ,\n" +
                    "        `length` ,\n" +
                    "        `width` ,\n" +
                    "        `purpose` ,\n" +
                    "        `purpose_name` ,\n" +
                    "        `subordinate_cell` ,\n" +
                    "        `is_saled` ,\n" +
                    "        `is_rented` ,\n" +
                    "        `sale_contract_no` ,\n" +
                    "        `rent_contract_no` ,\n" +
                    "        `coefficient` ,\n" +
                    "        `status` ,\n" +
                    "        `create_by` ,\n" +
                    "        `create_date` ,\n" +
                    "        `update_by`,\n" +
                    "        `update_date` ,\n" +
                    "        `remarks` ,\n" +
                    "        `decoration_time` ,\n" +
                    "        `house_property_card_no` ,\n" +
                    "        `subscribe_no` ,\n" +
                    "        `is_pay_maintenance_fund`,\n" +
                    "        `pre_maintenance_fund`\n" +
                    "        )\n" +
                    "        values(";
            for(int i=0;i<keyNames.length;i++){

                if (i==keyNames.length-1){
                    saveHouseRooms+=keyValues[i]+")";
                }
                else {
                    saveHouseRooms+=keyValues[i]+",";
                }
            }
            SqlFileUtil.InsertSqlToFile(saveHouseRooms);
        }



    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateHouseRoom(Map<String, Object> params) throws OssRenderException, IOException {
        houseRoomDao.updateHouseRoom(params);
        String updateHouseRoom= SqlCreateUtil.getSqlByMybatis(HouseRoomDao.class.getName()+".updateHouseRoom",params);
        SqlFileUtil.InsertSqlToFile(updateHouseRoom);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteHouseRoom(Map<String, Object> params) throws OssRenderException, IOException {
        houseRoomDao.deleteHouseRoom(params);
        String deleteHouseRoom= SqlCreateUtil.getSqlByMybatis(HouseRoomDao.class.getName()+".deleteHouseRoom",params);
        SqlFileUtil.InsertSqlToFile(deleteHouseRoom);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveHouseRoomRentRecord(Map<String, Object> params) throws OssRenderException, IOException {
        String xqCode=(String)params.get("xqCode");
        if(StringUtils.isBlank(xqCode)){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区Code不能为空");
        }
        houseRoomDao.saveHouseRoomRentRecord(params);
        String sqlContent= SqlCreateUtil.getSqlByMybatis(HouseRoomDao.class.getName()+".saveHouseRoomRentRecord",params);
        SqlFileUtil.InsertSqlToFile(sqlContent);
    }

}
