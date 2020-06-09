package com.windaka.suizhi.mpi.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface FaceAlarmRecordDao {

	/**
	 * 根据id查询报警记录
	 * @param
	 * @return
	 */
	Map<String,Object> queryById(@Param("id") int id);

	/**
	 * 功能描述: 获取最大记录ID值
	 * @auther: lixianhua
	 * @date: 2019/12/7 16:24
	 * @param:
	 * @return:
	 */
    int getMaxRecord();
	/**
	 * 功能描述: 添加人脸报警记录
	 * @auther: lixianhua
	 * @date: 2019/12/16 20:04
	 * @param:
	 * @return:
	 */
	int insertFaceAlarmRecord(Map<String,Object> alarmMap );
}
