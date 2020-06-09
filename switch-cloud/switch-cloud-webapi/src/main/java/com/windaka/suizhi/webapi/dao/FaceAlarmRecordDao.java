package com.windaka.suizhi.webapi.dao;

import com.windaka.suizhi.webapi.model.FaceAlarmRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface FaceAlarmRecordDao {
	/**
	 * 人脸报警记录上传
	 * @param params
	 * @return
	 */
	int saveFaceAlarmRecord(@Param("xqCode")String xqCode,@Param("params")Map<String, Object> params);

	/**
	 * 人脸报警记录信息处理
	 * @param faceAlarmRecord
	 * @return
	 */
	int updateFaceAlarmRecord(FaceAlarmRecord faceAlarmRecord);
	/**
	 * 查询全部报警记录列表总条数
	 * @param params
	 * @return
	 */
	int totalRows(@Param("params") Map<String, Object> params);

	/**
	 * 查询全部报警记录列表
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryAllAlarmRecordList(@Param("params") Map<String, Object> params);
	/**
	 * 查询全部人脸报警记录列表
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryFaceAlarmRecordList(@Param("params") Map<String, Object> params);

	/**
	 * 查询全部车辆报警记录列表
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryCarAlarmRecordList(@Param("params") Map<String, Object> params);

	/**
	 * 根据alarmId查询报警记录
	 * @param alarmId
	 * @return
	 */
	Map<String,Object> queryByAlarmId(@Param("alarmId") String alarmId);

	/**
	 * 查询人员code或者车牌号 hjt
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryPersonOrCar(Map<String, Object> params);
	/**
	 * 功能描述: 查询人员报警总数
	 * @auther: lixianhua
	 * @date: 2019/12/18 10:07
	 * @param:
	 * @return:
	 */
	Integer queryTotalNum(Map<String, Object> params);
	/**
	 * 功能描述: 查询人脸报警明细
	 * @auther: lixianhua
	 * @date: 2019/12/18 10:08
	 * @param:
	 * @return:
	 */
	List<Map<String, Object>> queryPersonAlarmList(Map<String, Object> params);
	/**
	 * 功能描述: 获取异常处理信息
	 * @auther: lixianhua
	 * @date: 2019/12/18 21:59
	 * @param:
	 * @return:
	 */
	Map<String, Object> queryHandleContent(Map<String, Object> params);
	/**
	 * 功能描述: 根据主键更新人脸报警
	 * @auther: lixianhua
	 * @date: 2019/12/19 9:15
	 * @param:
	 * @return:
	 */
	int updateFaceAlarmById(Map<String, Object> params);
	/**
	 * 功能描述: 获取首页预警信息
	 * @auther: lixianhua
	 * @date: 2019/12/29 12:22
	 * @param:
	 * @return:
	 */
	List<Map<String, Object>> queryHomeAlarmList(Map<String, Object> params);
	/**
	 * 功能描述: 根据条件查询人员报警记录
	 * @auther: lixianhua
	 * @date: 2020/1/9 8:58
	 * @param:
	 * @return:
	 */
	Map<String, Object> queryFaceAlarmByCondition(Map<String,Object> map);
}
