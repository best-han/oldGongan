package com.windaka.suizhi.manageport.dao;

import com.windaka.suizhi.manageport.model.TJDeviceTotal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 统计－设备管理Dao
 * @author pxl
 * @create: 2019-05-06 10:22
 */
@Mapper
public interface TJDeviceTotalDao {

    /**
     * 新增统计－设备管理
     * @param model
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    int save(TJDeviceTotal model);

    /**
     * 根据小区Code删除记录
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    int delete(String xqCode,String deviceType);

    /**
     * 查询统计－设备管理列表
     * @param params
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    Map<String,Object> queryTotal(@Param("params") Map<String, Object> params);

    /**
     * 查询统计－设备管理列表
     * @param params
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    List<Map<String,Object>> queryDetail(@Param("params") Map<String, Object> params);
}
