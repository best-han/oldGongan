package com.windaka.suizhi.webapi.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.webapi.dao.CarGroupCarDao;
import com.windaka.suizhi.webapi.dao.FaceTypePersonDao;
import com.windaka.suizhi.webapi.service.SpecialBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class SpecialBaseInfoServiceImpl implements SpecialBaseInfoService {

    @Autowired
    FaceTypePersonDao faceTypePersonDao;
   /* @Autowired
    SubdistrictDao subdistrictDao;*/
   @Autowired
   CarGroupCarDao carGroupCarDao;

    @Override
    public Map<String,Object> querySpecialNum(Map<String,Object> params) throws OssRenderException{
        if (StringUtils.isBlank((String)params.get("areaId"))) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"区域不能为空");
        }
        Map<String,Object> map=new HashMap<>();
        params.put("faceTypeCode","4");//黑名单
        int blacklistNum=faceTypePersonDao.queryFaceTypePersonNum(params);//黑名单人数
        map.put("blacklistNum",blacklistNum);
        params.put("faceTypeCode","3");//特殊人员
        int specialPersonNum=faceTypePersonDao.queryFaceTypePersonNum(params);
        map.put("specialPersonNum",specialPersonNum);
        params.put("carType","3");//特殊车辆
        int specialCarNum=carGroupCarDao.queryCarGroupCarNum(params);
        map.put("specialCarNum",specialCarNum);
        return map;
    }
}
