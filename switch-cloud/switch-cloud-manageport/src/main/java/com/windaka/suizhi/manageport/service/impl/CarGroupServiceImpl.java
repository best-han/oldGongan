package com.windaka.suizhi.manageport.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.SaveUtil;
import com.windaka.suizhi.common.utils.SqlFileUtil;
import com.windaka.suizhi.manageport.dao.CarGroupDao;
import com.windaka.suizhi.manageport.service.CarGroupService;
import com.windaka.suizhi.manageport.util.SqlCreateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class CarGroupServiceImpl implements CarGroupService {

    @Autowired
    CarGroupDao carGroupDao;

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void saveCarGroups(Map<String, Object> params) throws OssRenderException, IOException {
        String xqCode=(String)params.get("xqCode");
        if(StringUtils.isBlank(xqCode)){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区Code不能为空");
        }
        List list=(List<Map<String,Object>>) params.get("list");
        if(CollectionUtils.isEmpty(list)){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"数据为空");
        }
        carGroupDao.saveCarGroups(xqCode,list);
        String []keyNames={"xqCode","manageId","type","typeName","name","memo","status","createBy","createDate","updateBy","updateDate","remarks"};
        String sqlContentFront="insert into car_group (xq_code,manage_id," +
                "`type` ," +
                "`type_name` ," +
                "`name` ," +
                "`memo` ," +
                "`status` ," +
                "`create_by` ," +
                "`create_date` ," +
                "`update_by` ," +
                "`update_date` ," +
                "`remarks`" +
                ") " +
                "values";
        SaveUtil.listSqlSave(keyNames,sqlContentFront,xqCode,list);
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void updateCarGroup(Map<String, Object> params) throws OssRenderException, IOException {
        carGroupDao.updateCarGroup(params);
        String sqlContent= SqlCreateUtil.getSqlByMybatis(CarGroupDao.class.getName()+".updateCarGroup",params);
        SqlFileUtil.InsertSqlToFile(sqlContent);
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void deleteCarGroup(Map<String, Object> params) throws OssRenderException, IOException {
        carGroupDao.deleteCarGroup(params);
        String sqlContent= SqlCreateUtil.getSqlByMybatis(CarGroupDao.class.getName()+".deleteCarGroup",params);
        SqlFileUtil.InsertSqlToFile(sqlContent);
    }

}
