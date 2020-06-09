package com.windaka.suizhi.user.dao;

import com.windaka.suizhi.api.user.Role;
import org.apache.ibatis.annotations.*;

import java.util.Set;

/**
 * @description:
 * @author: hjt
 * @create: 2018-12-20 14:05
 * @version: 1.0.0
 **/
@Mapper
public interface RoleAppDao {

    /**
    * @Author: hjt
    * @Description: 角色列表查询
    */
    @Select("SELECT app_id FROM ht_role_app WHERE role_id = #{roleId}")
    Set<String>  quertList(String roleId);

    /**
    * @Author: hjt
    * @Description: 删除当前角色全部子系统关联
    */
    @Delete("DELETE FROM ht_role_app WHERE role_id = #{roleId}")
    int delete(String roleId);

    //批量新增管理员与子系统关联
    int save(@Param("roleId") String roleId, @Param("appIds") Set<String> appIds);

    /**
    * @Author: hjt
    * @Description: 角色集合查询对应子系统
    */
    Set<String> queryByRoleIds(@Param("roles") Set<Role> roles);
}
