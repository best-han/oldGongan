package com.windaka.suizhi.mpi.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.util.List;
import java.util.Map;

public interface WebsocketXqService {

	/**
	 * 查询小区列表
	 *
	 * @param xqCode
	 */
	public List<Map<String,Object>> queryListXqInfo(String areaId, String xqCode,String userId) throws OssRenderException;


}
