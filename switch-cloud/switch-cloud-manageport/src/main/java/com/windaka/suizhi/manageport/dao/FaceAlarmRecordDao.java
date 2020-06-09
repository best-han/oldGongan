package com.windaka.suizhi.manageport.dao;

import com.windaka.suizhi.manageport.model.FaceAlarmRecord;
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
	 * 功能描述: 获取最大记录ID值
	 * @auther: lixianhua
	 * @date: 2019/12/7 16:24
	 * @param:
	 * @return:
	 */
    Integer getMaxRecord();
	/**
	 * 功能描述: 根据报警记录主键获取主键
	 * @auther: lixianhua
	 * @date: 2019/12/7 17:24
	 * @param:
	 * @return:
	 */
    int getRecordByAlarmId(Map<String,Object> map);
}
