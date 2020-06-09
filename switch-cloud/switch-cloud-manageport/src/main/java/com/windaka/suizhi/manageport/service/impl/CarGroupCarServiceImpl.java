package com.windaka.suizhi.manageport.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.SaveUtil;
import com.windaka.suizhi.common.utils.SqlFileUtil;
import com.windaka.suizhi.manageport.dao.CarGroupCarDao;
import com.windaka.suizhi.manageport.service.CarGroupCarService;
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
public class CarGroupCarServiceImpl implements CarGroupCarService {

    @Autowired
    CarGroupCarDao carGroupCarDao;

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void saveCarGroupCars(Map<String, Object> params) throws OssRenderException, IOException {
        String xqCode=(String)params.get("xqCode");
        if(StringUtils.isBlank(xqCode)){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区Code不能为空");
        }
        List list=(List<Map<String,Object>>) params.get("list");
        if(CollectionUtils.isEmpty(list)){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"数据为空");
        }
        carGroupCarDao.saveCarGroupCars(xqCode,list);

        String []keyNames={"xqCode","manageId","groupCode","carCode"};
        String sqlContentFront="insert into car_group_car (xq_code,manage_id," +
                "`group_code` ," +
                "`car_code`" +
                ")" +
                " values";
        SaveUtil.listSqlSave(keyNames,sqlContentFront,xqCode,list);

    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void updateCarGroupCar(Map<String, Object> params) throws OssRenderException, IOException {
        carGroupCarDao.updateCarGroupCar(params);
        String sqlContent= SqlCreateUtil.getSqlByMybatis(CarGroupCarDao.class.getName()+".updateCarGroupCar",params);
        SqlFileUtil.InsertSqlToFile(sqlContent);
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void deleteCarGroupCar(Map<String, Object> params) throws OssRenderException, IOException {
        carGroupCarDao.deleteCarGroupCar(params);
        String sqlContent= SqlCreateUtil.getSqlByMybatis(CarGroupCarDao.class.getName()+".deleteCarGroupCar",params);
        SqlFileUtil.InsertSqlToFile(sqlContent);
    }

}
