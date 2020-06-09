package com.windaka.suizhi.manageport.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 当前登录用户关联小区Dao
 * @author pxl
 * @create: 2019-05-27 16:39
 */
@Mapper
public interface HTUserXQDao {
    /**
     * 根据当前用户id，获取关联的小区Codes(多个小区用逗号进行分隔)
     * @param userId 当前用户id
     * @return
     *
     * @author pxl
     * @create 2019-05-27 16:39
     */
    String queryXQCodeByUserId(@Param("userId") String userId);

    /**
     * 获取当前所有小区Codes(多个小区用逗号进行分隔)
     * @return
     * @author wcl
     * @create 2019-07-22 17:52
     */
    String queryAllXQCode();
}
