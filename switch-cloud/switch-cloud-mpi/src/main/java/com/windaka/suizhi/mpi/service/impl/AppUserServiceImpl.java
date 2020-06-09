package com.windaka.suizhi.mpi.service.impl;


import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.mpi.dao.HTUserXQDao;
import com.windaka.suizhi.mpi.service.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Slf4j
@Service
public class AppUserServiceImpl implements AppUserService {

	@Autowired
	private HTUserXQDao htUserXQDao;

	/**
	 * 根据当前用户、传参，进行权限验证
	 * @param userId
	 * @throws OssRenderException
	 */
	public String checkAuth(String userId) throws OssRenderException {
		String xqCodes="";
		//验证当前用户查询权限
//		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
//		AppUser appUser = (AppUser) htUserXQDao.queryByUserId(userId);
//		String sysLevel = appUser.getSysLevel();   //用户级别(1：超管，2：物业，3：小区)
		Map<String, Object> loginAppUser = htUserXQDao.queryByUserId(userId);
		String sysLevel = (String) loginAppUser.get("sysLevel");

		if(sysLevel == null || !sysLevel.equals("1")) {
			xqCodes = htUserXQDao.queryXQCodeByUserId(userId);
			if (StringUtils.isBlank(xqCodes)) {
				throw new OssRenderException(ReturnConstants.CODE_FAILED, "该用户未关联查看小区");
			}

		}else {
			xqCodes = htUserXQDao.queryAllXQCode();
		}

		return xqCodes;

	}

}
