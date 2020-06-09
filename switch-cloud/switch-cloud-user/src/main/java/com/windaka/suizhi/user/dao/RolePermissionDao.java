package com.windaka.suizhi.user.dao;

import com.windaka.suizhi.api.oss.sys.HtPermission;
import com.windaka.suizhi.api.user.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 角色权限关系<br>
 * @author hjt
 */
@Mapper
public interface RolePermissionDao {

	/**
	 * 保存角色的操作权限
	 * @param roleId
	 * @param permissionIds
	 * @return
	 */
	int saveRolePermissions(@Param("roleId") String roleId, @Param("permissionIds") Set<String> permissionIds);

	/**
	 * 根据角色ID删除角色的操作权限
	 * @param roleId
	 * @return
	 */
	@Delete("DELETE FROM ht_role_menu_permission WHERE role_id = #{roleId}")
	int deleteByRoleId(@Param("roleId") String roleId);

	/**
	 * 根据角色ID 查询角色的操作权限
	 * @param roleIds
	 * @return
	 */
	Set<HtPermission> queryPermissionsByRoleIds(@Param("roleIds") Set<String> roleIds);
	/**
	 * 根据角色 获取角色集的操作权限
	 * @param roles
	 * @return
	 */
	List<Map<String,String>> queryPermissionsByRoles(@Param("roles") Set<Role> roles);
	/**
	* @Author: hjt
	* @Date: 2018/12/21
	* @Description: 当前角色已选中按钮ID集合
	*/
	@Select("SELECT DISTINCT t.permission_id permissionId FROM ht_role_menu_permission t WHERE t.role_id = #{roleId}")
	Set<String> queryPermissionsByRoleId(String roleId);
}
