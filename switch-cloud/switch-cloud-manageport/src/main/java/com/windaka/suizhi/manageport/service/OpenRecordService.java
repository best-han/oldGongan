package com.windaka.suizhi.manageport.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface OpenRecordService {

	/**
	 * 批量保存开门记录
	 *
	 * @param xqCode
	 * @param openRecords
	 */
	void saveOpenRecord(String xqCode,List<Map<String,Object>> openRecords) throws OssRenderException, IOException;

	/**
	 *  开门方式统计----默认最近七天开门
	 * @param params
	 * @return
	 * @throws OssRenderException
	 */
	List<Map<String,Object>> queryOpenTypesOfPerson(Map<String,Object> params) throws OssRenderException;

	/**
	 * 开门记录列表查询----默认最近七天开门  hjt
	 * @param params
	 * @return
	 * @throws OssRenderException
	 */
	Map<String,Object> queryOpenRecordList(Map<String,Object> params) throws OssRenderException;
}
