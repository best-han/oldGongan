package com.windaka.suizhi.webapi.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.util.List;
import java.util.Map;

public interface DeviceService {
    /*
    设备统计信息
     */
    Map<String, Object> queryDeviceNumStatistics(Map<String, Object> params)throws OssRenderException;
    /*
    设备类型统计信息
     */
    Map<String, Object> queryDeviceTypeNumStatistics(Map<String, Object> params)throws OssRenderException;
    /*
    设备分布信息
     */
    List<Map<String, Object>> queryDeviceDistribution(Map<String, Object> params)throws  OssRenderException;
    /*
    获取设备明细信息
     */
    Map<String, Object> queryDeviceInfo(Map<String, Object> params) throws  OssRenderException;
}
