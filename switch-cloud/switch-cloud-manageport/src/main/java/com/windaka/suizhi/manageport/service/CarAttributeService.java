package com.windaka.suizhi.manageport.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.io.IOException;
import java.util.Map;

public interface CarAttributeService {

    /**
     * 车辆属性保存
     * @param params
     */
    public void saveCarAttributes(Map<String, Object> params) throws OssRenderException, IOException;

    /**
     * 车辆属性修改
     * @param params
     * @throws OssRenderException
     */
    public void updateCarAttribute(Map<String, Object> params) throws OssRenderException, IOException;

    /**
     * 车辆属性删除
     * @param params
     * @throws OssRenderException
     */
    public void deleteCarAttribute(Map<String, Object> params) throws OssRenderException, IOException;
}
