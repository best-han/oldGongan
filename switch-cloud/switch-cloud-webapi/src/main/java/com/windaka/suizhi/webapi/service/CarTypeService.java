package com.windaka.suizhi.webapi.service;

import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.common.utils.PageUtil;
import com.windaka.suizhi.webapi.dao.CarTypeDao;
import com.windaka.suizhi.webapi.model.CarLibrary;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarTypeService  {

    @Autowired
    CarTypeDao carTypeDao;

    @Transactional(rollbackFor = Exception.class)
    public void saveCarType(Map<String,Object> params) throws OssRenderException{
        LoginAppUser appUser= AppUserUtil.getLoginAppUser();
        String userId=appUser.getUserId();
        params.put("createBy",userId);
        params.put("updateBy",userId);
        carTypeDao.saveCarType(params);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateCarType(Map<String,Object> params) throws OssRenderException{
        LoginAppUser appUser= AppUserUtil.getLoginAppUser();
        String userId=appUser.getUserId();
        params.put("updateBy",userId);
        carTypeDao.updateCarType(params);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteCarType(Map<String,Object> params) throws OssRenderException{
        carTypeDao.deleteCarType(params);
    }

    public Map<String,Object> queryCarTypes(Map<String,Object> params) throws OssRenderException{
        int totalRows = carTypeDao.queryTotalCarType(params);
        List<Map<String,Object>> list = Collections.emptyList();
        if (totalRows > 0) {
            PageUtil.pageParamConver(params, true);
            list=carTypeDao.queryCarTypes(params);
        }
        Map<String,Object> mapResult=new HashMap<>();//返回结果map
        mapResult.put("list",list);
        mapResult.put("totalRows",totalRows);
        mapResult.put("currentPage", MapUtils.getInteger(params, PageUtil.PAGE));
        return mapResult;

    }
    
    public CarLibrary selectCarType(Map<String,Object> params) throws OssRenderException{
    	CarLibrary face=carTypeDao.selectCarType(params);
    	return face==null?new CarLibrary():face;
    }


}
