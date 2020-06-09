package com.windaka.suizhi.webapi.dao;

import com.windaka.suizhi.webapi.model.TJWisdomlamp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 统计－智慧灯杆Dao
 * @author pxl
 * @create: 2019-05-28 16:37
 */
@Mapper
public interface TJWisdomlampDao {
    /**
     * 新增统计－智慧灯杆
     * @param model
     * @return
     *
     * @author pxl
     * @create 2019-05-28 16:37
     */
    int save(TJWisdomlamp model);

    /**
     * 删除所有统计－智慧灯杆
     * @return
     *
     * @author pxl
     * @create 2019-05-28 16:37
     */
    int delete(String xqCode);

    /**
     * 查询统计－智慧灯杆
     * @param params
     * @return
     *
     * @author pxl
     * @create 2019-05-28 16:37
     */
    Map<String,Object> query(@Param("params") Map<String, Object> params);
}
