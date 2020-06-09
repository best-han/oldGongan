package com.windaka.suizhi.mpi.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;


@Mapper
public interface CarRecordDao {


	/**
	 * 车辆报警记录单个查询
	 * @param
	 * @return
	 */
	Map<String,Object> queryCarAlarmRecordById(int id);
	/**
	 * 车辆占道记录单个查询
	 * @param
	 * @return
	 */
	Map<String,Object> queryCarJeevesRecordById(int id);


	/**
	 * 功能描述: 获取最大ID值
	 * @auther: lixianhua
	 * @date: 2019/12/7 16:50
	 * @param:
	 * @return:
	 */
    int getMaxRecord();

	/**
	 * 功能描述: 获取车辆占道报警最大主键
	 * @auther: lixianhua
	 * @date: 2019/12/7 19:34
	 * @param:
	 * @return:
	 */
	int getCarMaxRecord();

}
