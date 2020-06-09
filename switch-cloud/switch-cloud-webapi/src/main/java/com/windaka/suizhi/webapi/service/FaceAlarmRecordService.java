package com.windaka.suizhi.webapi.service;

import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.common.exception.OssRenderException;

import java.util.List;
import java.util.Map;

public interface FaceAlarmRecordService {
	/**
	 * 人脸报警记录上传
	 * @param xqCode
	 * @param params
	 * @return
	 */
	void saveFaceTrackRecord(String xqCode, List<Map<String,Object>> params) throws OssRenderException;

	/**
	 * 人脸报警记录信息处理
	 *
	 * @param params
	 */
	void updateFaceAlarmRecord(Map<String, Object> params) throws OssRenderException;


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
	/**
	 * 功能描述: 获取人脸报警明细
	 * @auther: lixianhua
	 * @date: 2019/12/18 10:00
	 * @param:
	 * @return:
	 */
    Map<String, Object> queryAlarmPersonList(Map<String, Object> params) throws OssRenderException;
	/**
	 * 功能描述: 获报警处理信息
	 * @auther: lixianhua
	 * @date: 2019/12/18 21:52
	 * @param:
	 * @return:
	 */
    Map<String, Object> queryHandleInfo(Map<String, Object> params) throws OssRenderException;
	/**
	 * 功能描述: 添加报警处理信息
	 * @auther: lixianhua
	 * @date: 2019/12/19 9:05
	 * @param:
	 * @return:
	 */
	void saveAlarmHandleInfo(Map<String, Object> params) throws OssRenderException;
	/**
	 * 功能描述: 获取首页预警信息
	 * @auther: lixianhua
	 * @date: 2019/12/29 12:16
	 * @param:
	 * @return:
	 */
    List<Map<String, Object>> queryHomeAlarmList(Map<String, Object> params);
	/**
	 * 功能描述: 根据主键获取人员报警信息
	 * @auther: lixianhua
	 * @date: 2020/1/9 8:51
	 * @param:
	 * @return:
	 */
    Map<String, Object> queryAlarmPersonById(Integer id) throws OssRenderException;
}
