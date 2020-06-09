package com.windaka.suizhi.manageport.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface CarRecordDao {
	/**
	 * 批量保存汽车报警记录 hjt
	 * @param map
	 * @return
	 */
	int saveCarAlarmRecords(Map map);
	/**
	 * 批量保存汽车占道报警记录 hjt
	 * @param map
	 * @return
	 */
	int saveCarJeevesRecords(Map map);

	/**
	 * 车辆报警记录更新
	 * @param map
	 * @return
	 */
	int updateCarAlarmRecord(Map map);
	/**
	 * 车辆占道报警记录更新
	 * @param map
	 * @return
	 */
	int updateCarJeevesRecord(Map map);

	/**
	 * 车辆报警记录单个查询
	 * @param carAlarmId
	 * @return
	 */
	Map<String,Object> queryCarAlarmRecord(String carAlarmId);
	/**
	 * 车辆占道记录单个查询
	 * @param jeevesId
	 * @return
	 */
	Map<String,Object> queryCarJeevesRecord(String jeevesId);

	/**
	 * 查询车辆出入记录总数  hjt
	 * @param params
	 * @return
	 */
	int queryTotalCarAccessRecord(Map<String,Object> params);

	/**
	 * 车辆出入记录列表查询  hjt
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryCarAccessRecordList(Map<String,Object> params);

	/**
	 * 陌生车辆数量查询  hjt
	 * @param params
	 * @return
	 */
	int totalStrangCar(Map<String, Object> params);

	/**
	 * 占道车辆数量查询  hjt
	 * @param params
	 * @return
	 */
	int totalJeevesCar(Map<String, Object> params);
	/**
	 * 功能描述: 获取最大ID值
	 * @auther: lixianhua
	 * @date: 2019/12/7 16:50
	 * @param:
	 * @return:
	 */
    Integer getMaxRecord();
	/**
	 * 功能描述: 根据报警主键获取主键
	 * @auther: lixianhua
	 * @date: 2019/12/7 17:45
	 * @param:
	 * @return:
	 */
	int getRecordByAlarmId(Map<String, Object> map);
	/**
	 * 功能描述: 获取车辆占道报警最大主键
	 * @auther: lixianhua
	 * @date: 2019/12/7 19:34
	 * @param:
	 * @return:
	 */
	Integer getCarMaxRecord();
	/**
	 * 功能描述: 根据占道主键获取主键
	 * @auther: lixianhua
	 * @date: 2019/12/8 8:01
	 * @param:
	 * @return:
	 */
	int getRecordByJeevesId(Map<String, Object> map);
}
