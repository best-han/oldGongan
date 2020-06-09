package com.windaka.suizhi.user.service;

import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.api.user.Role;
import com.windaka.suizhi.common.exception.OssRenderException;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RoleService{
	/**
	 * 角色管理-新增
	 * @param role
	 */
	void saveRole(Role role) throws OssRenderException;

	/**
	 * 角色管理-修改
	 * @param role
	 */
	void updateRole(Role role) throws OssRenderException;

	/**
	 * 角色管理-删除
	 * @param roleId
	 */
	void deleteRole(String roleId) throws OssRenderException;

	/**
	 * 	角色管理-查询列表
	 * @param params
	 * @return java.lang.Map
	 */
	Page<Map<String,Object>> queryRoleList(Map<String, Object> params) throws OssRenderException;

	/**
	 * 根据角色ID查询角色
	 * @param roleId
	 * @return role
	 */
	Role queryByRoleId(String roleId) throws OssRenderException;

	/**
	 * 根据角色ID查询角色
	 * @param roleId
	 * @return java.lang.Map
	 */
	Map<String, Object> queryById(String roleId) throws OssRenderException;

	/**
	* @Description: 角色权限菜单管理
	* @Param: [roleId, menuIds, permissionIds]
	* @return: void
	* @Author: Ms.wcl
	* @Date: 2019/4/26
	*/
	void saveRoleMenu(String roleId, List menuIds) throws OssRenderException;

	/**
	 * @Description: 角色权限按钮管理
	 * @Param: [roleId, menuIds, permissionIds]
	 * @return: void
	 * @Author: Ms.wcl
	 * @Date: 2019/4/26
	 */
	void saveRolePermission(String roleId, List permissionIds) throws OssRenderException;

	/**
	 * 根据角色ID查询权限菜单
	 * @param roleId
	 * @return htMenu
	 */
	Map<String,Object> queryMenu(String roleId) throws OssRenderException;

	/**
	 * 根据角色ID，查询权限菜单列表
	 * @param roleId
	 * @return
	 * @throws OssRenderException
	 *
	 * @author pxl
	 * @create: 2019-05-08 11:47
	 */
	List<Map<String,Object>> queryMenuList(String roleId) throws OssRenderException;

	/**
	 * 根据角色ID，查询菜单id集合
	 * @param roleId
	 * @return
	 * @throws OssRenderException
	 */
	Set<String> queryMenuIdsList(String roleId) throws OssRenderException;

	/**
	 * 根据角色ID，查询权限列表
	 * @param roleId
	 * @return
	 * @throws OssRenderException
	 *
	 * @author pxl
	 * @create: 2019-05-08 11:47
	 */
	List<Map<String,Object>> queryPerList(@Param("roleId") String roleId) throws OssRenderException;

	/**
	 * 根据角色ID查询权限子菜单
	 * @param roleId
	 * @return htMenu
	 */
	List<Map<String,Object>> queryChildMenu(String roleId) throws OssRenderException;

	/**
	 * 根据角色ID查询权限菜单按钮
	 * @param roleId
	 * @return htMenu
	 */
	List<Map<String,Object>> queryMenuPer(String roleId) throws OssRenderException;

	/**
	 * 根据角色ID查询权限子菜单按钮
	 * @param menuId
	 * @return htMenu
	 */
	List<Map<String,Object>> queryChildMenuPer(String menuId) throws OssRenderException;
}
