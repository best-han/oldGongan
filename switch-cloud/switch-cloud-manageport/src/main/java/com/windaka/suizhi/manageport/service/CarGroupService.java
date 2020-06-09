package com.windaka.suizhi.manageport.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.io.IOException;
import java.util.Map;

public interface CarGroupService {

    /**
     * 车辆库信息保存
     * @param params
     */
    public void saveCarGroups(Map<String, Object> params) throws OssRenderException, IOException;

    /**
     * 车辆库信息修改
     * @param params
     * @throws OssRenderException
     */
    public void updateCarGroup(Map<String, Object> params) throws OssRenderException, IOException;

    /**
     * 车辆库信息删除
     * @param params
     * @throws OssRenderException
     */
    public void deleteCarGroup(Map<String, Object> params) throws OssRenderException, IOException;
}
