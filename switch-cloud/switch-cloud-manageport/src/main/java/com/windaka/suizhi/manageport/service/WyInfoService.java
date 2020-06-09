package com.windaka.suizhi.manageport.service;

import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.manageport.model.WyInfo;

public interface WyInfoService {
	/**
	 * 物业公司新增
	 *
	 * @param wyInfo
	 */
	void saveWyInfo(WyInfo wyInfo) throws OssRenderException;

	/**
	 * 物业公司修改
	 *
	 * @param wyInfo
	 */
	void updateWyInfo(WyInfo wyInfo) throws OssRenderException;

	/**
	 * 物业公司删除
	 *
	 * @param wyCode
	 */
	void deleteWyInfo(String wyCode) throws OssRenderException;

}
