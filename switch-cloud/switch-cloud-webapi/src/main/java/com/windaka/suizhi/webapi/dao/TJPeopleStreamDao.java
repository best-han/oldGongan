package com.windaka.suizhi.webapi.dao;

import com.windaka.suizhi.webapi.model.TJPeopleStream;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 统计－人流统计Dao
 * @author pxl
 * @create: 2019-05-06 10:22
 */
@Mapper
public interface TJPeopleStreamDao {

    /**
     * 新增统计－人流统计
     * @param model
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    int save(TJPeopleStream model);

    /**
     * 根据小区Code,date,hour删除人流统计信息
     * @return
     * @author wcl
     * @create 2019-05-29
     */
    int delete(String xqCode, Date operDate, int hour);

    /**
     * 查询统计－人流统计列表
     * @param params
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    List<Map<String,Object>> query(@Param("params") Map<String, Object> params);
}
