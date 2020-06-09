package com.windaka.suizhi.webapi.service;

import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.webapi.model.TJDeviceType;

import java.util.Map;

/**
 * 统计－设备类型信息Service
 * @author pxl
 * @create: 2019-05-06 10:22
 */
public interface TJDeviceTypeService {
    /**
     * 新增统计－设备类型信息
     * @param params
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    void saveMap(Map<String, Object> params) throws OssRenderException;

    /**
     * 查询设备类型管理
     * @param deviceCode
     *
     * @author pxl
     * @create 2019-05-06 10:56
     */
    TJDeviceType query(String deviceCode);
}
