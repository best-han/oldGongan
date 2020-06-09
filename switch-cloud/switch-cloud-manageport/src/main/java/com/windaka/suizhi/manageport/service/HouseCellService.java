package com.windaka.suizhi.manageport.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.io.IOException;
import java.util.Map;

public interface HouseCellService {

    /**
     * 单元基础信息保存
     * @param params
     */
    public void saveHouseCells(Map<String, Object> params) throws OssRenderException, IOException;

    /**
     * 单元基础信息修改
     * @param params
     * @throws OssRenderException
     */
    public void updateHouseCell(Map<String, Object> params) throws OssRenderException, IOException;

    /**
     * 单元基础信息删除
     * @param params
     * @throws OssRenderException
     */
    public void deleteHouseCell(Map<String, Object> params) throws OssRenderException, IOException;
}
