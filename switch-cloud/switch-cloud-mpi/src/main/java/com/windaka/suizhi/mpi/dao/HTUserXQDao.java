package com.windaka.suizhi.mpi.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    /**
     * 根据用户ID获取用户信息
     * @param userId
     * @return
     */
    @Select("SELECT user_id userId,username,cname,phone,sys_level sysLevel,sys_admin*1 sysAdmin, enabled*1 enabled" +
            ",date_format(cre_time,'%Y-%m-%d %H:%i:%s') creTime " +
            " FROM ht_user t WHERE t.user_id = #{userId} and t.del_flag = 1")
    Map<String, Object> queryByUserId(String userId);
}
