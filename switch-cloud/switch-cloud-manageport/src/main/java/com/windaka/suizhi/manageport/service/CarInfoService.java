package com.windaka.suizhi.manageport.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CarInfoService {

	/**
	 * 新增车辆信息
	 * @param xqCode
	 * @param carInfos
	 */
	void saveCarInfo(String xqCode,List<Map<String,Object>> carInfos) throws OssRenderException, IOException;

	/**
	 * 修改车辆信息
	 *
	 * @param params
	 */
	void updateCarInfo(Map<String, Object> params) throws OssRenderException, IOException;

	/**
	 * 删除车辆信息
	 * @param params
	 */
	void deleteCarInfo(Map<String, Object> params) throws OssRenderException, IOException;
}
