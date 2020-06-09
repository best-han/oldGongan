package com.windaka.suizhi.manageport.service;

import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.common.exception.OssRenderException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FaceAlarmRecordService {
	/**
	 * 人脸报警记录上传
	 * @param xqCode
	 * @param params
	 * @return
	 */
	void saveFaceTrackRecord(String xqCode, List<Map<String,Object>> params) throws OssRenderException, IOException;

	/**
	 * 人脸报警记录信息处理
	 *
	 * @param params
	 */
	void updateFaceAlarmRecord(Map<String, Object> params) throws OssRenderException, IOException;


	/**
	 * 报警列表查询
	 * @param params
	 * @return
	 */
	Page<Map<String,Object>> queryAlarmRecordList(Map<String, Object> params) throws OssRenderException;

	/**
	 * 根据alarmId查询报警记录
	 * @param alarmId
	 * @return
	 */
	Map<String,Object> queryByAlarmId(String alarmId) throws OssRenderException;

	/**
	 * 查询人员code或者车牌号 hjt
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryPersonOrCar(Map<String, Object> params) throws OssRenderException;
}
