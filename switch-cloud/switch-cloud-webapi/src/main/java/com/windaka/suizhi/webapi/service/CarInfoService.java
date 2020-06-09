package com.windaka.suizhi.webapi.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.util.List;
import java.util.Map;

public interface CarInfoService {

	/**
	 * 新增车辆信息
	 * @param xqCode
	 * @param carInfos
	 */
	void saveCarInfo(String xqCode,List<Map<String,Object>> carInfos) throws OssRenderException;

	/**
	 * 修改车辆信息
	 *
	 * @param params
	 */
	void updateCarInfo(Map<String, Object> params) throws OssRenderException;

	/**
	 * 删除车辆信息
	 * @param params
	 */
	void deleteCarInfo(Map<String, Object> params) throws OssRenderException;
}
