package com.windaka.suizhi.mpi.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.util.Map;

public interface FaceTrackRecordService {

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
