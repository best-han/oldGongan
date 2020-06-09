package com.windaka.suizhi.manageport.dao;

import com.windaka.suizhi.manageport.model.TJAttendance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 统计－考勤管理Dao
 * @author pxl
 * @create: 2019-05-06 10:22
 */
@Mapper
public interface TJAttendanceDao {

    /**
     * 新增统计－考勤管理
     * @param model
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    int save(TJAttendance model);

    /**
     * 根据小区Code删除记录
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    int delete(String xqCode);

    /**
     * 查询统计－考勤管理列表
     * @param params
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    Map<String,Object> query(@Param("params") Map<String, Object> params);
}
