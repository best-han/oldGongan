package com.windaka.suizhi.manageport.service;

import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.common.exception.OssRenderException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface AbnormalRecordService {

	/**
	 * 修改异常行为抓拍
	 *
	 * @param params
	 */
	void updateAbnormalRecord(Map<String, Object> params) throws OssRenderException, IOException;

	/**
	 *  异常行为列表查询(分页)
	 * @param params
	 * @return
	 * @throws OssRenderException
	 */
	Page<Map<String, Object>> queryAbnormalRecordPageList(Map<String, Object> params) throws OssRenderException;

	/**
	 * 异常行为列表查询
	 * @param params
	 * @return
	 * @throws OssRenderException
	 */
	List<Map<String, Object>> queryAbnormalRecordList(Map<String, Object> params) throws OssRenderException;

/**
	 * 删除异常行为抓拍
	 * @param xqCode
	 * @param captureIds
	 *//*

	void deleteAbnormalRecord(String xqCode, ArrayList captureIds) throws OssRenderException;

*/

}
