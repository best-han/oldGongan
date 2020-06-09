package com.windaka.suizhi.manageport.dao;

import com.windaka.suizhi.manageport.model.TJCarInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 统计－车辆信息Dao
 * @author pxl
 * @create: 2019-05-06 10:22
 */
@Mapper
public interface TJCarInfoDao {

    /**
     * 新增统计－车辆信息
     * @param model
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    int save(TJCarInfo model);

    /**
     * 根据小区Code删除车辆信息
     * @return
     *
     * @author wcl
     * @create 2019-05-30
     */
    int delete(String xqCode, Date operDate, int hour);

    /**
     * 查询统计－车辆信息列表
     * @param params
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    List<Map<String,Object>> query(@Param("params") Map<String, Object> params);
}
