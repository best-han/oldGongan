package com.windaka.suizhi.user.dao;

import com.windaka.suizhi.api.oss.sys.HtMenu;
import com.windaka.suizhi.api.user.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: hjt
 * @Date: 2018/12/10 10:24
 * @Version 1.0
 */
@Mapper
public interface RoleMenuDao {

    /**
     * 保存角色的功能权限
     * @param roleId
     * @param menuIds
     * @return
     */
    int saveRoleMenus(@Param("roleId") String roleId, @Param("menuIds") Set<String> menuIds);

    /**
     * 删除角色的功能权限
     * @param roleId
     * @return
     */
   /* @Delete("DELETE FROM ht_role_menu WHERE role_id = #{roleId}")
    int deleteRoleMenus(@Param("roleId") String roleId);*/

    /**
     * 删除角色的功能权限 hjt
     * @param roleId
     * @return
     */
    @Delete("DELETE FROM ht_role_menu_permission WHERE role_id = #{roleId}")
    int deleteRoleMenus(String roleId);

    /**
    * @Author: hjt
    * @Description: 根据角色id查询功能菜单
    */
    @Select("SELECT DISTINCT t.menu_id menuId FROM ht_role_menu_permission t WHERE t.role_id = #{roleId}")
    Set<String> queryMenuIdsByRoleId(String roleId);

    /**
    * @Author: hjt
    * @Description: 查询多个角色功能菜单的集合
    */
    List<HtMenu> queryMenusByRoleIds(@Param("roleIds") Set<String> roleIds);

    /**
     * @Author: hjt
     * @Date: 2018/12/19
     * @Description: 查询所有菜单，标注当前角色是否选中
     */
    Set<Map<String, Object>> queryMenusByRoles(@Param("roles") Set<Role> roles);

}
