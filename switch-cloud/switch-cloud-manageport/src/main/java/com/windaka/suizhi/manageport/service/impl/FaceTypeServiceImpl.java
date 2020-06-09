package com.windaka.suizhi.manageport.service.impl;

import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.SqlFileUtil;
import com.windaka.suizhi.manageport.dao.FaceTypeDao;
import com.windaka.suizhi.manageport.service.FaceTypeService;
import com.windaka.suizhi.manageport.util.SqlCreateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
public class FaceTypeServiceImpl implements FaceTypeService {

    @Autowired
    FaceTypeDao faceTypeDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFaceType(Map<String,Object> params) throws OssRenderException, IOException {
        if(params.get("manageId")==null || "".equals(params.get("manageId"))){
            params.put("manageId","0");//云端自身维护人脸库，manageId为“0”
        }
        faceTypeDao.saveFaceType(params);
        String sqlContent= SqlCreateUtil.getSqlByMybatis(FaceTypeDao.class.getName()+".saveFaceType",params);
        SqlFileUtil.InsertSqlToFile(sqlContent);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFaceType(Map<String,Object> params) throws OssRenderException, IOException {
        faceTypeDao.updateFaceType(params);
        String sqlContent= SqlCreateUtil.getSqlByMybatis(FaceTypeDao.class.getName()+".updateFaceType",params);
        SqlFileUtil.InsertSqlToFile(sqlContent);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFaceType(Map<String,Object> params) throws OssRenderException, IOException {
        faceTypeDao.deleteFaceType(params);
        String sqlContent= SqlCreateUtil.getSqlByMybatis(FaceTypeDao.class.getName()+".deleteFaceType",params);
        SqlFileUtil.InsertSqlToFile(sqlContent);
    }
}
