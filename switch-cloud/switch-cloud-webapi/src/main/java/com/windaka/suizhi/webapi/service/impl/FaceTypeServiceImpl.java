package com.windaka.suizhi.webapi.service.impl;

import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.common.utils.PageUtil;
import com.windaka.suizhi.webapi.dao.FaceTypeDao;
import com.windaka.suizhi.webapi.dao.FaceTypePersonDao;
import com.windaka.suizhi.webapi.model.FaceLibrary;
import com.windaka.suizhi.webapi.service.FaceTypeService;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class FaceTypeServiceImpl implements FaceTypeService {

    @Autowired
    FaceTypeDao faceTypeDao;
    @Autowired
    FaceTypePersonDao faceTypePersonDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFaceType(Map<String,Object> params) throws OssRenderException{
        LoginAppUser appUser= AppUserUtil.getLoginAppUser();
        String userId=appUser.getUserId();
        params.put("createBy",userId);
        params.put("updateBy",userId);
        String uuId= UUID.randomUUID().toString();
//        System.out.println(uuId);
        params.put("faceTypeCode",uuId);
        faceTypeDao.saveFaceType(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFaceType(Map<String,Object> params) throws OssRenderException{
        LoginAppUser appUser= AppUserUtil.getLoginAppUser();
        String userId=appUser.getUserId();
        params.put("updateBy",userId);
        faceTypeDao.updateFaceType(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFaceType(String id) throws OssRenderException{
        faceTypeDao.deleteFaceTypeByCode(id);
        faceTypePersonDao.deleteFaceTypePersonByCode(id);
    }

    @Override
    public Map<String,Object> queryFaceTypes(Map<String,Object> params) throws OssRenderException{
        if(params.get("limit")==null || params.get("limit").toString().trim().equals("")){
            params.put("limit",10);
        }
        if(params.get("page")==null || params.get("page").toString().trim().equals("")){
            params.put("page",1);
        }
        int totalRows = faceTypeDao.queryTotalFaceType(params);
        List<Map<String,Object>> list = Collections.emptyList();
        if (totalRows > 0) {
            PageUtil.pageParamConver(params, true);
            list=faceTypeDao.queryFaceTypes(params);
        }
        Map<String,Object> mapResult=new HashMap<>();//返回结果map
        mapResult.put("list",list);
        mapResult.put("totalRows",totalRows);
        mapResult.put("currentPage", MapUtils.getInteger(params, PageUtil.PAGE));
        return mapResult;

    }
    
    public FaceLibrary selectFaceType(Map<String,Object> params) throws OssRenderException{
    	FaceLibrary face=faceTypeDao.selectFaceType(params);
    	return face==null?new FaceLibrary():face;
    }


}
