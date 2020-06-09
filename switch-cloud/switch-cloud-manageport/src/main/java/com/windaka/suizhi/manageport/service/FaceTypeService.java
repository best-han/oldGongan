package com.windaka.suizhi.manageport.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.io.IOException;
import java.util.Map;

public interface FaceTypeService {

    /**
     * 人脸类型维护
     * @throws OssRenderException
     */
    public void saveFaceType(Map<String,Object> params) throws OssRenderException, IOException;

    /**
     * 人脸类型修改
     * @param params
     * @throws OssRenderException
     */
    public void updateFaceType(Map<String,Object> params) throws OssRenderException, IOException;

    /**
     * 人脸类型删除
     * @param params
     * @throws OssRenderException
     */
    public void deleteFaceType(Map<String,Object> params) throws OssRenderException, IOException;
}
