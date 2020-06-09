package com.windaka.suizhi.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * @description:区域管理员与子系统关联表
 * @author: hjt
 * @create: 2019-01-03 13:33
 * @version: 1.0.0
 **/
@Mapper
public interface UserAppDao {

    /**
    * @Author: hjt
    * @Description: 查询对应用户的子系统
    */
    @Select("SELECT app_id appId FROM ht_user_app WHERE user_id = #{userId}")
    Set<String> queryByUserId(String userId);
}
