package com.windaka.suizhi.user.service.impl;

import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.oss.sys.HtMenu;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.api.user.Role;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.common.utils.PageUtil;
import com.windaka.suizhi.common.utils.TimesUtil;
import com.windaka.suizhi.user.dao.RoleDao;
import com.windaka.suizhi.user.dao.RoleMenuDao;
import com.windaka.suizhi.user.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;
	@Autowired
	private RoleMenuDao roleMenuDao;


	/** 
	* @Description: 添加角色 
	* @Param: [role] 
	* @return: void 
	* @Author: Ms.wcl
	* @Date: 2019/4/24 
	*/ 
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveRole(Role role) throws OssRenderException {
		int id ;
		String roleId;
		if (roleDao.maxId() != ""){
			id = Integer.parseInt(roleDao.maxId());
			roleId = String.valueOf(id + 1);
		}else {
			roleId = "1";
		}
		String roleName = role.getRoleName();
		if (StringUtils.isBlank(roleName)) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"角色名不能为空");
		}
		if (MapUtils.isNotEmpty(roleDao.queryByRoleName(roleName)) ) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"角色名已存在");
		}
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		role.setRoleId(roleId);
		role.setCreBy(loginAppUser.getUserId());
		role.setUpdBy(loginAppUser.getUserId());
		//添加角色
		roleDao.saveRole(role);

	}

	/** 
	* @Description: 修改角色 
	* @Param: [role] 
	* @return: void 
	* @Author: Ms.wcl
	* @Date: 2019/4/24 0024
	*/ 
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateRole(Role role) throws OssRenderException {
		if (role == null || StringUtils.isBlank(role.getRoleId())) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"角色不存在");
		}
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		role.setRoleName(role.getRoleName());
		role.setUpdBy(loginAppUser.getUserId());
		role.setUpdTime(new Date());
		//修改角色
		roleDao.updateRole(role);
	}

	/** 
	* @Description: 删除角色 
	* @Param: [roleId] 
	* @return: void 
	* @Author: Ms.wcl
	* @Date: 2019/4/24 0024
	*/ 
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteRole(String roleId) throws OssRenderException {
		Role role = roleDao.queryById(roleId);
		if (role == null) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"查询的角色不存在");
		}
		//获取当前操作用户
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		role.setRoleId(roleId);
		role.setUpdBy(loginAppUser.getUserId());
		//删除角色
		//roleDao.deleteRole(roleId);
		int a = roleDao.deleteRole(roleId);
		if(a<=0){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"用户删除失败，请稍后再试！");
		}
	}

	/** 
	* @Description: 角色列表查询 
	* @Param: [params, areaId] 
	* @return: java.util.Map<java.lang.String,java.lang.Object> 
	* @Author: Ms.wcl
	* @Date: 2019/4/24 0024
	*/ 
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Page<Map<String,Object>> queryRoleList(Map<String, Object> params) throws OssRenderException {
		//当前开始和结束日期是否正确
		String startTime = (String)params.get("startTime");
		String endTime = (String)params.get("endTime");
		if(!StringUtils.isBlank(startTime)){
			if(TimesUtil.checkDateFormat(startTime)){
				//params.put("startTime",startTime+" 00:00:00");
			}else{
				throw new OssRenderException(ReturnConstants.CODE_FAILED,"开始时间格式不正确");
			}
		}
		if(!StringUtils.isBlank(endTime)){
			if(TimesUtil.checkDateFormat(endTime)){
				//params.put("endTime",endTime+" 23:59:59");
			}else{
				throw new OssRenderException(ReturnConstants.CODE_FAILED,"结束时间格式不正确");
			}
		}

		int totalRows = roleDao.totalRows(params);
		List<Map<String,Object>> list = Collections.emptyList();
		if (totalRows > 0) {
			PageUtil.pageParamConver(params, true);
			list = roleDao.queryRoleList(params);
		}

		return new Page<>(totalRows, MapUtils.getInteger(params,PageUtil.PAGE), list);

	}

	/**
	* @Description:
	* @Param: [roleId]
	* @return: com.windaka.suizhi.api.user.Role
	* @Author: Ms.wcl
	* @Date: 2019/4/24
	*/
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Role queryByRoleId(String roleId) throws OssRenderException {
		Role role = roleDao.queryById(roleId);
		if(role==null){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"查询用的角色不存在");
		}
		return role;
	}

	/**
	* @Description: 根据id查询角色
	* @Param: [roleId]
	* @return: java.util.Map<java.lang.String,java.lang.Object>
	* @Author: Ms.wcl
	* @Date: 2019/4/25 0025
	*/
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Map<String, Object> queryById(String roleId) throws OssRenderException {
		Map map = roleDao.queryByRoleId(roleId);
		if(map==null){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"查询用的角色不存在");
		}
		return map;
	}

	/**
	* @Description: 角色权限菜单管理
	* @Param: [roleId, menuIds]
	* @return: void
	* @Author: Ms.wcl
	* @Date: 2019/4/26 0026
	*/
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveRoleMenu(String roleId, List menuIds) throws OssRenderException {
		if (StringUtils.isBlank(roleId)) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"角色ID不能为空");
		}
		if (menuIds == null) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"按钮ID不能为空");
		}
		/*for (int i =0;i < menuIds.size(); i++){
			if (roleDao.menuId(menuIds.get(i).toString()) != 0) {
				throw new OssRenderException(ReturnConstants.CODE_FAILED,"菜单ID已存在");
			}else {
				roleDao.saveRoleMenu(roleId, menuIds.get(i).toString());
			}
		}*/
		//上述为角色下的菜单不能有交集，现在改为角色下的菜单可以有交集，但用户只能有一个角色 hjt 2019/8/1
		roleMenuDao.deleteRoleMenus(roleId);
		for (int i =0;i < menuIds.size(); i++) {
			roleDao.saveRoleMenu(roleId, menuIds.get(i).toString());
		}
	}

	/**
	* @Description: 角色权限按钮管理
	* @Param: [roleId, permissionIds]
	* @return: void
	* @Author: Ms.wcl
	* @Date: 2019/4/26 0026
	*/
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveRolePermission(String roleId, List permissionIds) throws OssRenderException {
		if (StringUtils.isBlank(roleId)) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"角色ID不能为空");
		}
		if (permissionIds == null) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"菜单ID不能为空");
		}
		for (int i =0;i < permissionIds.size(); i++){
			if (roleDao.permissionId(permissionIds.get(i).toString()) != 0) {
				throw new OssRenderException(ReturnConstants.CODE_FAILED,"按钮ID已存在");
			}else {
				roleDao.saveRolePermission(roleId, permissionIds.get(i).toString());
			}
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Map<String,Object> queryMenu(String roleId) throws OssRenderException {
		Map<String,Object> menuList = roleDao.queryMenu(roleId);
		if(menuList==null){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"查询的Menu不存在");
		}
		return menuList;
	}

	/**
	 * 根据角色ID，查询权限菜单列表
	 * @param roleId
	 * @return
	 * @throws OssRenderException
	 *
	 * @author pxl
	 * @create: 2019-05-08 11:47
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<Map<String,Object>> queryMenuList(String roleId) throws OssRenderException {
		List<Map<String,Object>> menuList = roleDao.queryMenuList(roleId);
		return menuList;
	}

	@Override
	public Set<String> queryMenuIdsList(String roleId) throws OssRenderException {
		Set<String> menuIds = roleMenuDao.queryMenuIdsByRoleId(roleId);
		return menuIds;
	}

	/**
	 * 根据角色ID，查询权限列表
	 * @param roleId
	 * @return
	 * @throws OssRenderException
	 *
	 * @author pxl
	 * @create: 2019-05-08 11:47
	 */
	public List<Map<String,Object>> queryPerList(@Param("roleId") String roleId) throws OssRenderException{
		List<Map<String,Object>> perList = roleDao.queryPerList(roleId);
		return perList;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<Map<String,Object>> queryChildMenu(String roleId) throws OssRenderException {
		List<Map<String,Object>> childMenuList = roleDao.queryChildMenu(roleId);
		return childMenuList;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<Map<String,Object>> queryMenuPer(String roleId) throws OssRenderException {
		List<Map<String,Object>> menuPerList = roleDao.queryMenuPer(roleId);
		return menuPerList;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<Map<String,Object>> queryChildMenuPer(String roleId) throws OssRenderException {
		List<Map<String,Object>>  childMenuPerList = null;
		List<HtMenu> childMenuList = roleDao.queryChildMe(roleId);
		for (int i =0;i < childMenuList.size(); i++){
			childMenuPerList = roleDao.queryChildMenuPer(childMenuList.get(i).getMenuId());
		}
		return childMenuPerList;
	}

}
