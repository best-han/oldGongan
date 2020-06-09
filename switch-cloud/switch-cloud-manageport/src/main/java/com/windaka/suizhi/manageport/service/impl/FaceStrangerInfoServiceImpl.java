package com.windaka.suizhi.manageport.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.SaveUtil;
import com.windaka.suizhi.common.utils.SqlFileUtil;
import com.windaka.suizhi.manageport.dao.FaceStrangerInfoDao;
import com.windaka.suizhi.manageport.service.FaceStrangerInfoService;
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
public class FaceStrangerInfoServiceImpl implements FaceStrangerInfoService {

    @Autowired
    FaceStrangerInfoDao faceStrangerInfoDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFaceStrangerInfos(Map<String, Object> params) throws OssRenderException, IOException {
        String xqCode=(String)params.get("xqCode");
        if(StringUtils.isBlank(xqCode)){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区Code不能为空");
        }
        List list=(List<Map<String,Object>>) params.get("list");
        if(CollectionUtils.isEmpty(list)){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"数据为空");
        }
        faceStrangerInfoDao.saveFaceStrangerInfos(xqCode,list);
        String []keyNames={"xqCode","manageId","modelSex","modelAge","modelQuality","modelWidth","modelHeight","imageUrl","feature"};
        String sqlContentFront="insert into face_stranger_info (xq_code,manage_id," +
                "`model_sex` ," +
                "`model_age` ," +
                "`model_quality` ," +
                "`model_width` ," +
                "`model_height` ," +
                "`image_url` ," +
                "`feature`" +
                ")" +
                " values";
        SaveUtil.listSqlSave(keyNames,sqlContentFront,xqCode,list);

    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFaceStrangerInfo(Map<String, Object> params) throws OssRenderException, IOException {
        faceStrangerInfoDao.updateFaceStrangerInfo(params);
        String sqlContent= SqlCreateUtil.getSqlByMybatis(FaceStrangerInfoDao.class.getName()+".updateFaceStrangerInfo",params);
        SqlFileUtil.InsertSqlToFile(sqlContent);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFaceStrangerInfo(Map<String, Object> params) throws OssRenderException, IOException {
        faceStrangerInfoDao.deleteFaceStrangerInfo(params);
        String sqlContent= SqlCreateUtil.getSqlByMybatis(FaceStrangerInfoDao.class.getName()+".deleteFaceStrangerInfo",params);
        SqlFileUtil.InsertSqlToFile(sqlContent);
    }
}
