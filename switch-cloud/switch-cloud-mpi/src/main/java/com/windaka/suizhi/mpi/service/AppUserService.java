package com.windaka.suizhi.mpi.service;


import com.windaka.suizhi.common.exception.OssRenderException;

public interface AppUserService {

	/**
	 * 根据当前用户、传参，进行权限验证
	 *
	 * @param userId
	 */
	String checkAuth(String userId) throws OssRenderException;

}
