package com.windaka.suizhi.manageport.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.util.List;
import java.util.Map;

public interface CallRecordService {

	/**
	 * 批量保存通话记录
	 *
	 * @param xqCode
	 * @param callRecords
	 */
	void saveCallRecord(String xqCode, List<Map<String,Object>> callRecords) throws OssRenderException;

	/**
	 * 通话记录列表查询----默认最近七天开门  hjt
	 * @param params
	 * @return
	 * @throws OssRenderException
	 */
	Map<String,Object> queryCallRecordList(Map<String,Object> params) throws OssRenderException;
}
