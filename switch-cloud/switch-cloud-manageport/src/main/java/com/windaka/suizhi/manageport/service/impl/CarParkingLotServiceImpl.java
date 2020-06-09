package com.windaka.suizhi.manageport.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.SaveUtil;
import com.windaka.suizhi.common.utils.SqlFileUtil;
import com.windaka.suizhi.manageport.dao.CarParkingLotDao;
import com.windaka.suizhi.manageport.service.CarParkingLotService;
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
public class CarParkingLotServiceImpl implements CarParkingLotService {
    @Autowired
    CarParkingLotDao carParkingLotDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveCarParkingLots(Map<String, Object> params) throws OssRenderException, IOException {
        String xqCode=(String)params.get("xqCode");
        if(StringUtils.isBlank(xqCode)){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区Code不能为空");
        }
        List list=(List<Map<String,Object>>) params.get("list");
        if(CollectionUtils.isEmpty(list)){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"数据为空");
        }
        carParkingLotDao.saveCarParkingLots(xqCode,list);
        String []keyNames={"xqCode","manageId","parkingLot","parkingLotCode","totalLot","idleLot","carNumNum","entranceNum","exitNum",
        "ledNum","status","createBy","createDate","updateBy","updateDate","remarks"};
        String sqlContentFront="insert into car_parking_lot (xq_code,manage_id," +
                "`parking_lot` ," +
                "`parking_lot_code` ," +
                "`total_lot` ," +
                "`idle_lot` ," +
                "`car_num_num` ," +
                "`entrance_num` ," +
                "`exit_num` ," +
                "`led_num` ," +
                "`status` ," +
                "`create_by` ," +
                "`create_date` ," +
                "`update_by` ," +
                "`update_date` ," +
                "`remarks`" +
                ") values";
        SaveUtil.listSqlSave(keyNames,sqlContentFront,xqCode,list);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCarParkingLot(Map<String, Object> params) throws OssRenderException, IOException {
        carParkingLotDao.updateCarParkingLot(params);
        String sqlContent= SqlCreateUtil.getSqlByMybatis(CarParkingLotDao.class.getName()+".updateCarParkingLot",params);
        SqlFileUtil.InsertSqlToFile(sqlContent);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteCarParkingLot(Map<String, Object> params) throws OssRenderException, IOException {
        carParkingLotDao.deleteCarParkingLot(params);
        String sqlContent= SqlCreateUtil.getSqlByMybatis(CarParkingLotDao.class.getName()+".deleteCarParkingLot",params);
        SqlFileUtil.InsertSqlToFile(sqlContent);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteCarParkingLotAll(Map<String, Object> params) throws OssRenderException, IOException {
        carParkingLotDao.deleteCarParkingLotAll(params);
        String sqlContent= SqlCreateUtil.getSqlByMybatis(CarParkingLotDao.class.getName()+".deleteCarParkingLotAll",params);
        SqlFileUtil.InsertSqlToFile(sqlContent);
    }
}
