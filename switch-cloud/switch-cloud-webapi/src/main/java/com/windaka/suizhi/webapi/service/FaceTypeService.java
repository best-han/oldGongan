package com.windaka.suizhi.webapi.service;

import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.webapi.model.FaceLibrary;

import java.util.Map;

public interface FaceTypeService {

    /**
     * 人脸类型维护
     * @throws OssRenderException
     */
    public void saveFaceType(Map<String, Object> params) throws OssRenderException;

    /**
     * 人脸类型修改
     * @param params
     * @throws OssRenderException
     */
    public void updateFaceType(Map<String, Object> params) throws OssRenderException;

    /**
     * 人脸类型删除
     * @param id
     * @throws OssRenderException
     */
    public void deleteFaceType(String id) throws OssRenderException;

    /**
     * 人脸类型查询
     * @param params
     * @throws OssRenderException
     */
    public Map<String,Object> queryFaceTypes(Map<String,Object> params) throws OssRenderException;
    
    public FaceLibrary selectFaceType(Map<String,Object> params) throws OssRenderException;
}
