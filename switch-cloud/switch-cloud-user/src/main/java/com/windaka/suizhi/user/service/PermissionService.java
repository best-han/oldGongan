package com.windaka.suizhi.user.service;

import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.api.oss.sys.HtPermission;
import com.windaka.suizhi.common.exception.OssRenderException;

import java.util.Map;

public interface PermissionService {

	void savePermission(HtPermission sysPermission)throws OssRenderException;

	void updatePermission(HtPermission sysPermission)throws OssRenderException;

	void deletePermissionById(String permissionId)throws OssRenderException;

	Page<HtPermission> queryList(Map<String, Object> params)throws OssRenderException;
}
