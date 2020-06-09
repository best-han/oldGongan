package com.windaka.suizhi.user.controller;

import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.oss.sys.HtPermission;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.common.exception.OssRenderException;
//import com.windaka.suizhi.log.api.annotation.LogAnnotation;
import com.windaka.suizhi.user.service.PermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class PermissionController extends BaseController {

	@Autowired
	private PermissionService permissionService;

	/**
	 * 运营平台添加权限
	 * 
	 * @param sysPermission
	 * @return
	 */
	//@LogAnnotation(module = "添加权限")
	@PreAuthorize("hasAuthority('back:permission:save')")
	@PostMapping("/permissions")
	public Map<String,Object> savePermission(@RequestBody HtPermission sysPermission) {
		try {
			if (StringUtils.isBlank(sysPermission.getPermission())) {
				throw new OssRenderException(ReturnConstants.CODE_FAILED,"权限标识不能为空");
			}
			if (StringUtils.isBlank(sysPermission.getPermissionName())) {
				throw new OssRenderException(ReturnConstants.CODE_FAILED,"权限名不能为空");
			}

			permissionService.savePermission(sysPermission);

			return render(sysPermission, ReturnConstants.CODE_SUCESS,ReturnConstants.MSG_OPER_SUCCESS);
		}catch (Exception e){
			return failRender(e);
		}
	}

	/**
	 * 运营平台修改权限
	 * 
	 * @param sysPermission
	 */
	//@LogAnnotation(module = "修改权限")
	@PreAuthorize("hasAuthority('back:permission:update')")
	@PutMapping("/permissions")
	public Map<String,Object> updatePermission(@RequestBody HtPermission sysPermission) {
		try {
			if (StringUtils.isBlank(sysPermission.getPermissionName())) {
				throw new OssRenderException(ReturnConstants.CODE_FAILED,"权限名不能为空");
			}
			permissionService.updatePermission(sysPermission);
			return render(sysPermission, ReturnConstants.CODE_SUCESS,ReturnConstants.MSG_OPER_SUCCESS);
		}catch (Exception e){
			return failRender(e);
		}
	}

	/**
	 * 删除权限标识
	 * 
	 * @param permissionId
	 */
	//@LogAnnotation(module = "删除权限")
	@PreAuthorize("hasAuthority('back:permission:delete')")
	@DeleteMapping("/permissions/{permissionId}")
	public Map<String,Object> deletePermissionById(@PathVariable String permissionId) {
		try {
			permissionService.deletePermissionById(permissionId);
			return render();
		}catch (Exception e){
			return failRender(e);
		}
	}
	/**
	 * 查询权限标识列表
	 * @param params
	 * @return
	 */
	@PreAuthorize("hasAuthority('back:permission:query')")
	@GetMapping("/permissions")
	public Map<String, Object> queryList(@RequestParam Map<String, Object> params) {
		try {
			Page<HtPermission> page = permissionService.queryList(params);
			return render(page);
		}catch (Exception e){
			return failRender(e);
		}

	}
}
