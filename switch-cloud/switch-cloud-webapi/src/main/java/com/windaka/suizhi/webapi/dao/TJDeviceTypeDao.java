package com.windaka.suizhi.webapi.dao;

import com.windaka.suizhi.webapi.model.TJDeviceType;
import org.apache.ibatis.annotations.Mapper;

/**
 * 统计－设备类型管理Dao
 * @author pxl
 * @create: 2019-05-06 10:22
 */
@Mapper
public interface TJDeviceTypeDao {

    /**
     * 新增统计－设备类型管理
     * @param model
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    int save(TJDeviceType model);

    /**
     * 查询设备类型管理
     * @param deviceCode
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    TJDeviceType query(String deviceCode);
}
