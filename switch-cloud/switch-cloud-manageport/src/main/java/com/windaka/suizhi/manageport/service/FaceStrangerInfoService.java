package com.windaka.suizhi.manageport.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.io.IOException;
import java.util.Map;

public interface FaceStrangerInfoService {

    /**
     * 陌生人信息保存
     * @param params
     */
    public void saveFaceStrangerInfos(Map<String, Object> params) throws OssRenderException, IOException;

    /**
     * 陌生人信息修改
     * @param params
     * @throws OssRenderException
     */
    public void updateFaceStrangerInfo(Map<String, Object> params) throws OssRenderException, IOException;

    /**
     * 陌生人信息删除
     * @param params
     * @throws OssRenderException
     */
    public void deleteFaceStrangerInfo(Map<String, Object> params) throws OssRenderException, IOException;
}
