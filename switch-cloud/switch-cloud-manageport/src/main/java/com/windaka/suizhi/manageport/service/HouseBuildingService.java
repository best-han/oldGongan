package com.windaka.suizhi.manageport.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.io.IOException;
import java.util.Map;

public interface HouseBuildingService {

    /**
     * 楼宇基础信息保存
     * @param params
     */
    public void saveHouseBuildings(Map<String, Object> params) throws OssRenderException, IOException;

    /**
     * 楼宇基础信息修改
     * @param params
     * @throws OssRenderException
     */
    public void updateHouseBuilding(Map<String, Object> params) throws OssRenderException, IOException;

    /**
     * 楼宇基础信息删除
     * @param params
     * @throws OssRenderException
     */
    public void deleteHouseBuilding(Map<String, Object> params) throws OssRenderException, IOException;
}
