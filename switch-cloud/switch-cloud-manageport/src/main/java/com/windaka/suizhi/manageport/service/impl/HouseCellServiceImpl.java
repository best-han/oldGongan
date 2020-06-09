package com.windaka.suizhi.manageport.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.SqlFileUtil;
import com.windaka.suizhi.manageport.dao.HouseCellDao;
import com.windaka.suizhi.manageport.service.HouseCellService;
import com.windaka.suizhi.manageport.util.SqlCreateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class HouseCellServiceImpl implements HouseCellService {

    @Autowired
    HouseCellDao houseCellDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveHouseCells(Map<String, Object> params) throws OssRenderException, IOException {
        String xqCode=(String)params.get("xqCode");
        if(StringUtils.isBlank(xqCode)){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区Code不能为空");
        }
        List<Map<String,Object>> list=(List<Map<String,Object>>) params.get("list");
        if(CollectionUtils.isEmpty(list)){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"数据为空");
        }
        houseCellDao.saveHouseCells(xqCode,list);

        String []keyNames={"xqCode","manageId", "buildingId", "code",
                "name", "beginFloor", "endFloor", "beginRoomNum", "endRoomNum",
                "households", "remark", "status", "createBy", "createDate",
                "status", "createBy", "createDate", "updateBy", "updateDate",
                "updateBy", "updateDate", "item.remarks"};

        String []keyValues=new String[keyNames.length];

        for(Map<String,Object> map:list){
            map.put("xqCode",xqCode);
            for(int i=0;i<keyNames.length;i++){
                keyValues[i]=SqlFileUtil.keyAddValue(map,keyNames[i]);
            }

            String saveHouseCells="insert into house_cell (xq_code,manage_id,\n" +
                    "        `building_id` ,\n" +
                    "        `code`,\n" +
                    "        `name`,\n" +
                    "        `begin_floor` ,\n" +
                    "        `end_floor`,\n" +
                    "        `begin_room_num`,\n" +
                    "        `end_room_num`,\n" +
                    "        `households`,\n" +
                    "        `remark` ,\n" +
                    "        `status`,\n" +
                    "        `create_by`,\n" +
                    "        `create_date`,\n" +
                    "        `update_by`,\n" +
                    "        `update_date`,\n" +
                    "        `remarks`\n" +
                    "        )\n" +
                    "        values(";
            for(int i=0;i<keyNames.length;i++){

                if (i==keyNames.length-1){
                    saveHouseCells+=keyValues[i]+")";
                }
                else {
                    saveHouseCells+=keyValues[i]+",";
                }
            }
            SqlFileUtil.InsertSqlToFile(saveHouseCells);
        }




    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateHouseCell(Map<String, Object> params) throws OssRenderException, IOException {
        houseCellDao.updateHouseCell(params);
        String updateHouseCell= SqlCreateUtil.getSqlByMybatis(HouseCellDao.class.getName()+".updateHouseCell",params);
        SqlFileUtil.InsertSqlToFile(updateHouseCell);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteHouseCell(Map<String, Object> params) throws OssRenderException, IOException {
        houseCellDao.deleteHouseCell(params);
        String deleteHouseCell= SqlCreateUtil.getSqlByMybatis(HouseCellDao.class.getName()+".deleteHouseCell",params);
        SqlFileUtil.InsertSqlToFile(deleteHouseCell);
    }
}
