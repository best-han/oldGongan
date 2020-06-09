package com.windaka.suizhi.upcapture.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface AbnormalRecordService {
	/**
	 * 新增异常行为抓拍
	 *
	 * @param params
	 */
	void saveAbnormalRecord(Map<String, Object> params) throws OssRenderException, IOException;

	/**
	 * 修改异常行为抓拍
	 *
	 * @param params
	 */
/*
	void updateAbnormalRecord(Map<String, Object> params) throws OssRenderException;

	*/
/**
	 * 删除异常行为抓拍
	 * @param xqCode
	 * @param captureIds
	 *//*

	void deleteAbnormalRecord(String xqCode, ArrayList captureIds) throws OssRenderException;

*/

}
