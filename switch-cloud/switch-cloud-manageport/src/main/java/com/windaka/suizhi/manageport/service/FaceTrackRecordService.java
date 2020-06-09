package com.windaka.suizhi.manageport.service;

import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.common.exception.OssRenderException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FaceTrackRecordService {

	/**
	 * 人脸轨迹上传
	 * @param xqCode
	 * @param params
	 * @return
	 */
	void saveFaceTrackRecord(String xqCode, List<Map<String,Object>> params) throws OssRenderException, IOException;

	/**
	 * 人脸轨迹列表查询
	 * @param params
	 * @return
	 */
	Map<String,Object> queryFaceTrackRecordList(Map<String, Object> params) throws OssRenderException;

	/**
	 * 以图搜图
	 * @param params
	 * @return
	 * @throws OssRenderException
	 */
	Map<String, Object>  queryImgByImg(Map<String, Object> params) throws OssRenderException;



}
