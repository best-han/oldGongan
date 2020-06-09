package com.windaka.suizhi.manageport.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.SqlFileUtil;
import com.windaka.suizhi.manageport.dao.HouseBuildingDao;
import com.windaka.suizhi.manageport.service.HouseBuildingService;
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
public class HouseBuildingServiceImpl implements HouseBuildingService {
    @Autowired
    HouseBuildingDao houseBuildingDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveHouseBuildings(Map<String, Object> params) throws OssRenderException, IOException {
        String xqCode=(String)params.get("xqCode");
        if(StringUtils.isBlank(xqCode)){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区Code不能为空");
        }
        List<Map<String,Object>> list=(List<Map<String,Object>>) params.get("list");
        if(CollectionUtils.isEmpty(list)){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"数据为空");
        }
        houseBuildingDao.saveHouseBuildings(xqCode,list);

        String []keyNames={"xqCode","manageId", "communityId", "code",
                "name", "buildingFunc", "useArea", "buildArea", "constructionPermitNum",
                "presalePermitNum", "completionTime", "topTime", "decoration", "structureCategory",
                "damageCondition", "remark", "cellCount", "buildingType", "cleanLayerNum",
                "mopLayerNum", "galleryPassageGround", "elevatorCar",
                "accessDoor", "elevatorDoor", "wellDoor","electricWellDoor","blindWindow", "fireHydrant", "tidyMirror",
                "cellDoor", "hardenGround", "purificationGreenSpace", "noPurificationGreenSpace", "artificialSurface",
                "isUseElevator", "isNeedSecondHydropower", "status", "createBy", "createDate",
                "updateBy", "updateDate", "remarks", "buildingAddr", "buildingNum",
                "eachFloorRoom", "groundFloors"};

        String []keyValues=new String[keyNames.length];

        for(Map<String,Object> map:list){
            map.put("xqCode",xqCode);
            for(int i=0;i<keyNames.length;i++){
                keyValues[i]=SqlFileUtil.keyAddValue(map,keyNames[i]);
            }

            String saveHouseBuildings=" insert into house_building (xq_code,manage_id,community_id,code,name,building_func,use_area,build_area\n" +
                    "        ,construction_permit_num,presale_permit_num,completion_time,top_time,decoration,structure_category\n" +
                    "        ,damage_condition,remark,cell_count,building_type,clean_layer_num,mop_layer_num,gallery_passage_ground\n" +
                    "        ,elevator_car,access_door,elevator_door,well_door,electric_well_door,blind_window,fire_hydrant,tidy_mirror\n" +
                    "        ,cell_door,harden_ground,purification_green_space,no_purification_green_space,artificial_surface,is_use_elevator\n" +
                    "        ,is_need_second_hydropower,status,create_by,create_date,update_by,update_date,remarks,building_addr,building_num\n" +
                    "        ,each_floor_room,ground_floors) values(";
            for(int i=0;i<keyNames.length;i++){

                if (i==keyNames.length-1){
                    saveHouseBuildings+=keyValues[i]+")";
                }
                else {
                    saveHouseBuildings+=keyValues[i]+",";
                }
            }
            SqlFileUtil.InsertSqlToFile(saveHouseBuildings);
        }


    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateHouseBuilding(Map<String, Object> params) throws OssRenderException, IOException {
        houseBuildingDao.updateHouseBuilding(params);
        String updateHouseBuilding= SqlCreateUtil.getSqlByMybatis(HouseBuildingDao.class.getName()+".updateHouseBuilding",params);
        SqlFileUtil.InsertSqlToFile(updateHouseBuilding);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteHouseBuilding(Map<String, Object> params) throws OssRenderException, IOException {
        houseBuildingDao.deleteHouseBuilding(params);
        String deleteHouseBuilding=SqlCreateUtil.getSqlByMybatis(HouseBuildingDao.class.getName()+".deleteHouseBuilding",params);
        SqlFileUtil.InsertSqlToFile(deleteHouseBuilding);
    }
}
