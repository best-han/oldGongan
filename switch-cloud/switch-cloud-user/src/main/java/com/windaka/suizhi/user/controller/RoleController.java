package com.windaka.suizhi.user.controller;

import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.api.user.Role;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.user.service.RoleService;
import com.windaka.suizhi.api.common.ReturnConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


/**
* @Description: 角色管理
* @Author: Ms.wcl
* @Date: 2019/4/24
*/
@Slf4j
@RestController
@RequestMapping(value = "/user")
public class RoleController extends BaseController {
	@Autowired
	private RoleService roleService;

	/**
	 * 添加角色
	 * @param role
	 */
	@PostMapping("/role")
	public Map<String,Object> saveRole(@RequestBody Role role) {
		try{
			roleService.saveRole(role);
			return render();
		}catch (Exception e){
			log.info("[RoleController.saveRole,参数：{},异常信息{}]",role,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 修改角色
	 * @param role
	 */
	@PutMapping("/role")
	public Map<String,Object> updateRole(@RequestBody Role role) {
		try{
			roleService.updateRole(role);
			return render();
		}catch (Exception e){
			log.info("[RoleController.updateRole,参数：{},异常信息{}]",role,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 删除角色（伪删除）
	 * @param roleId
	 */
	@DeleteMapping("/role/{roleId}")
	public Map<String,Object> deleteRole(@PathVariable String roleId) {
		try{
			roleService.deleteRole(roleId);
			return render();
		}catch (Exception e){
			log.info("[RoleController.deleteRole,参数：{},异常信息{}]",roleId,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 查询列表
	 * @param params
	 */
	@GetMapping("/role/list")
	public Map<String,Object> queryRoleList(@RequestParam Map<String, Object> params) {
		try{
			Page page = roleService.queryRoleList(params);
			return render(page);
		}catch (Exception e){
			log.info("[RoleController.queryList,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 根据ID单个查询
	 * @param roleId
	 */
	@GetMapping("/role/{roleId}")
	public Map<String,Object> queryByRoleId(@PathVariable String roleId) {
		try{
			Map map = roleService.queryById(roleId);
			return render(map);
		}catch (Exception e){
			log.info("[RoleController.queryByRoleId,参数：{},异常信息{}]",roleId,e.getMessage());
			return failRender(e);
		}
	}

	/**
	* @Description: 添加角色权限-菜单、按钮
	* @Param: [roleId, menuIds, permissionIds]
	* @return: java.util.Map<java.lang.String,java.lang.Object>
	* @Author: Ms.wcl
	* @Date: 2019/4/26 0026
	*/
	@PostMapping("/role/roleMenuPer")
	public Map<String, Object> saveRoleMenuPermission(@RequestBody Map<String, Object> params) {
		
//		List<Map<String,Object>> listPermissionIds = (List<Map<String, Object>>) params.get("permissionIds");
		try{
			String roleId = (String) params.get("roleId");
			String menuStr = (String) params.get("menuIds");
//			System.out.println("menuStr...."+menuStr);
//			System.out.println(roleId);
			List<String> listMenuIds = Arrays.asList(menuStr.split(","));
			roleService.saveRoleMenu(roleId,listMenuIds);
//			roleService.saveRolePermission(roleId,listPermissionIds );
			return render();
		}catch (Exception e){
			log.info("[RoleController.saveRoleMenuPermission,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}
	
	/**
	 * 权限管理－权限查询
	 * @param roleId
	 * @return
	 *
	 * @author pxl
	 * @create: 2019-05-08 11:47
	 */
	@GetMapping("/roleMenuPer/{roleId}")
	public Map<String,Object> queryAuth(@PathVariable String roleId) {
		try {
			List<Map<String,Object>> menuList = roleService.queryMenuList(roleId);
			List<Map<String,Object>> perList = roleService.queryPerList(roleId);
			List<Map<String,Object>> mainMenuList = menuList.stream().filter(m ->m.get("pmenuId").equals("mainmain"))
					.collect(Collectors.toList());
			mainMenuList.forEach(m ->{
				setChild(m,menuList,perList);
			});

			if(mainMenuList.size() == 0){
				throw new OssRenderException(ReturnConstants.CODE_FAILED,"查询的Menu不存在");
			}

			Map<String,Object> result = new HashMap<String, Object>();
			result.put("list",mainMenuList);
			return render(result);
		}catch (Exception e){
			log.info("[RoleController.saveRoleMenuPermission,参数：{},异常信息{}]",roleId,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 角色管理-菜单查询
	 * @param roleId
	 * @return
	 */
	@GetMapping("/roleMenu/{roleId}")
	public Map<String,Object> queryRoleMenus(@PathVariable String roleId) {
		try {
			Set<String> menuIds = roleService.queryMenuIdsList(roleId);
			Map<String,Object> result = new HashMap<String, Object>();
			result.put("menuIds",menuIds);
			return render(result);
		}catch (Exception e){
			log.info("[RoleController.queryRoleMenus,参数：{},异常信息{}]",roleId,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 递归设置菜单树
	 * @param menu
	 * @param menus
	 */
	private void setChild(Map<String,Object> menu, List<Map<String,Object>> menus,List<Map<String,Object>> pers) {
		List<Map<String,Object>> child = menus.stream().filter(m -> m.get("pmenuId").equals(menu.get("menuId")))
				.collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(child)) {
			menu.put("childMenu",child);
			//递归设置子元素，多级菜单支持
			child.parallelStream().forEach(c -> {
				setChild(c, menus,pers);
			});
		}

		List<Map<String,Object>> perList = pers.stream().filter(m -> m.get("menuId").equals(menu.get("menuId")))
				.collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(perList)) {
			menu.put("perList",perList);
		}
	}

}
