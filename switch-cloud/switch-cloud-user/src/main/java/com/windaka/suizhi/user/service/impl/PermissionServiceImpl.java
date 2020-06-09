package com.windaka.suizhi.user.service.impl;

import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.oss.sys.HtPermission;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.common.utils.PageUtil;
import com.windaka.suizhi.user.dao.PermissionDao;
import com.windaka.suizhi.user.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionDao permissionDao;



	@Transactional(rollbackFor = Exception.class)
	@Override
	public void savePermission(HtPermission sysPermission) throws OssRenderException {
		HtPermission permission = permissionDao.queryByPermission(sysPermission.getPermission());
		if (permission != null) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"权限标识已存在");
		}
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		sysPermission.setCreBy(loginAppUser.getUserId());
		sysPermission.setCreTime(new Date());
		sysPermission.setUpdBy(loginAppUser.getUserId());
		sysPermission.setUpdTime(sysPermission.getCreTime());

		permissionDao.savePermission(sysPermission);
		log.info("保存权限标识：{}", sysPermission);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updatePermission(HtPermission sysPermission) throws OssRenderException{
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		sysPermission.setUpdBy(loginAppUser.getUserId());
		sysPermission.setUpdTime(new Date());
		permissionDao.updatePermission(sysPermission);
		log.info("修改权限标识：{}", sysPermission);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deletePermissionById(String permissionId) throws OssRenderException{
		HtPermission permission = permissionDao.queryById(permissionId);
		if (permission == null) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"权限标识不存在");
		}

		permissionDao.deletePermissionById(permissionId);
		//rolePermissionDao.deleteRolePermission(null, null, permissionId);
		log.info("删除权限标识：{}", permission);
	}

	@Override
	public Page<HtPermission> queryList(Map<String, Object> params) throws OssRenderException{
		int totalRows = permissionDao.totalRows(params);
		List<HtPermission> list = Collections.emptyList();
		if (totalRows > 0) {
			PageUtil.pageParamConver(params, false);

			list = permissionDao.queryList(params);
		}
		return new Page<>(totalRows, MapUtils.getInteger(params,PageUtil.PAGE), list);
	}

}
