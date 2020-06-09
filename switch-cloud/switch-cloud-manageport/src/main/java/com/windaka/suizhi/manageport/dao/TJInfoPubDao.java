package com.windaka.suizhi.manageport.dao;

import com.windaka.suizhi.manageport.model.TJInfoPub;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 统计－信息发布Dao
 * @author pxl
 * @create: 2019-05-29 10:41
 */
@Mapper
public interface TJInfoPubDao {
    /**
     * 新增统计－信息发布
     * @param model
     * @return
     *
     * @author pxl
     * @create 2019-05-29 10:41
     */
    int save(TJInfoPub model);

    /**
     * 根据小区Code、年份、月份，删除记录
     * @return
     *
     * @author pxl
     * @create 2019-05-29 10:41
     */
    int delete(String xqCode,String year,String month);

    /**
     * 查询统计－人流统计列表
     * @param params
     * @return
     *
     * @author pxl
     * @create 2019-05-29 10:41
     */
    List<Map<String,Object>> query(@Param("params") Map<String, Object> params);
}
