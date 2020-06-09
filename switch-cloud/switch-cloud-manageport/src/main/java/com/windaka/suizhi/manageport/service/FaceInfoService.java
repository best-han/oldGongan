package com.windaka.suizhi.manageport.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.io.IOException;
import java.util.Map;

public interface FaceInfoService {

    /**
     * 人脸特征信息保存
     * @param params
     */
    public void saveFaceInfos(Map<String, Object> params) throws OssRenderException, IOException;

    /**
     * 脸特征信息修改
     * @param params
     * @throws OssRenderException
     */
    public void updateFaceInfo(Map<String, Object> params) throws OssRenderException, IOException;

    /**
     * 脸特征信息删除
     * @param params
     * @throws OssRenderException
     */
    public void deleteFaceInfo(Map<String, Object> params) throws OssRenderException, IOException;
}
