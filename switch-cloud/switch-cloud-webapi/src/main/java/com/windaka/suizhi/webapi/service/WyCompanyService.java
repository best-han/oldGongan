package com.windaka.suizhi.webapi.service;

import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.webapi.model.WyCompany;

import java.util.Map;

public interface WyCompanyService {
	/**
	 * 物业公司新增
	 *
	 * @param wyCompany
	 */
	void saveWy(WyCompany wyCompany) throws OssRenderException;

	/**
	 * 物业公司修改
	 *
	 * @param wyCompany
	 */
	void updateWy(WyCompany wyCompany) throws OssRenderException;

	/**
	 * 物业公司删除
	 *
	 * @param companyCode
	 */
	void deleteWy(String companyCode) throws OssRenderException;

	/**
	 * 根据Code查询物业公司
	 *
	 * @param companyCode
	 */
	Map<String,Object> queryByCode(String companyCode) throws OssRenderException;

	/**
	 * 查询物业公司列表
	 *
	 * @param params
	 */
	Page<Map<String,Object>> queryWyList(Map<String, Object> params) throws OssRenderException;
}
