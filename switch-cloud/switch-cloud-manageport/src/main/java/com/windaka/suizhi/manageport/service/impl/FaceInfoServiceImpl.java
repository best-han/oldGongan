package com.windaka.suizhi.manageport.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.SaveUtil;
import com.windaka.suizhi.common.utils.SqlFileUtil;
import com.windaka.suizhi.manageport.dao.FaceInfoDao;
import com.windaka.suizhi.manageport.service.FaceInfoService;
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
public class FaceInfoServiceImpl implements FaceInfoService {

    @Autowired
    FaceInfoDao faceInfoDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFaceInfos(Map<String, Object> params) throws OssRenderException, IOException {
        String xqCode=(String)params.get("xqCode");
        if(StringUtils.isBlank(xqCode)){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区Code不能为空");
        }
        List list=(List<Map<String,Object>>) params.get("list");
        if(CollectionUtils.isEmpty(list)){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"数据为空");
        }
        faceInfoDao.saveFaceInfos(xqCode,list);
        String []keyNames={"xqCode","manageId","personFace","personId"};
        String sqlContentFront="insert into face_information (xq_code,manage_id,\n" +
                "`person_face` ," +
                "`person_id`" +
                ")" +
                " values";
        SaveUtil.listSqlSave(keyNames,sqlContentFront,xqCode,list);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFaceInfo(Map<String, Object> params) throws OssRenderException, IOException {
        faceInfoDao.updateFaceInfo(params);
        String sqlContent= SqlCreateUtil.getSqlByMybatis(FaceInfoDao.class.getName()+".updateFaceInfo",params);
        SqlFileUtil.InsertSqlToFile(sqlContent);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFaceInfo(Map<String, Object> params) throws OssRenderException, IOException {
        faceInfoDao.deleteFaceInfo(params);
        String sqlContent= SqlCreateUtil.getSqlByMybatis(FaceInfoDao.class.getName()+".deleteFaceInfo",params);
        SqlFileUtil.InsertSqlToFile(sqlContent);
    }
}
