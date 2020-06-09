package com.windaka.suizhi.webapi.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DeviceDao {
    /**
     * 查询设备数量信息
     * @param params
     * @return
     */
    Map<String, Object> queryDeviceNum(Map<String, Object> params);

    /**
     * 查询设备类型统计数据
     * @param params
     * @return
     */
    List<Map<String, Object>> queryDeviceTypeNum(Map<String, Object> params);

    /**
     * 查询设备详细信息
     * @param params
     * @return
     */
    List<Map<String, Object>> queryDeviceInfo(Map<String, Object> params);

    /**
     * 查询设备区域分布信息
     * @param params
     * @return
     */
    List<Map<String, Object>> queryDeviceDistributionInfo(Map<String, Object> params);
}
