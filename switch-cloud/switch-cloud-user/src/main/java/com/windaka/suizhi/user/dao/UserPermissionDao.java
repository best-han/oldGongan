package com.windaka.suizhi.user.dao;

import com.windaka.suizhi.api.oss.sys.HtPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: hjt
 * @Date: 2018/12/10 10:23
 * @Version 1.0
 */
@Mapper
public interface UserPermissionDao {
    /**
     * 设置区域管理员OSS操作权限
     *
     * @param userId
     * @return

    @Insert("INSERT INTO ht_user_menu_permission(user_id,menu_id,permission_id)\n" +
            " SELECT #{userId}, a.menu_id, a.permission_id FROM ht_permission a\n" +
            " WHERE EXISTS(SELECT 1 FROM ht_menu b WHERE a.menu_id = b.menu_id AND b.status=1 AND b.del_flag=1 AND b.qd_sign='N')\n" +
            " AND a.status=1 AND a.del_flag=1")
    int saveAreaManagerPermissions(String userId);
     */
    /**
     * 删除用户的操作权限
     *
     * @param userId
     * @return

    @Delete("DELETE FROM ht_user_menu_permission WHERE user_id = #{userId}")
    int deletePermissionsByUserId(String userId);
     */
    /**
     * 查询区域管理员操作权限
     *
     * @param userId
     * @return
    @Select("SELECT a.permission_id AS permissionId,  a.menu_id AS  menuId,a.permission AS permission," +
            " a.permission_name AS permissionName,a.cre_time AS creTime,a.upd_time AS updTime" +
            " FROM ht_permission a  " +
            " WHERE exists(SELECT 1 FROM ht_user_menu_permission b WHERE a.permission_id=b.permission_id AND b.user_id = #{userId})" +
            " AND a.status = 1 AND a.del_flag = 1")
    Set<HtPermission> queryPermissionsByUserId(String userId);
    */

    /**
     * 查询管理员操作权限
     * @param
     * @return
     */
    Set<HtPermission> queryAdminPermissions();


    /**
     * @Author: hjt
     * @Date: 2018/12/21
     * @Description: 用户ID查询可分配的按钮
     */
    List<Map<String, String>> queryAdminPermissionForMap(@Param("areaId") String areaId);
}
