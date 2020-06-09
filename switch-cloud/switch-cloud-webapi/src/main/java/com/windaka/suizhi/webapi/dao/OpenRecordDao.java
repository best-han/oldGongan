package com.windaka.suizhi.webapi.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description: 开门记录
 * @Param:
 * @return:
 * @Author: Ms.wcl
 * @Date: 2019/8/21
 */
@Mapper
public interface OpenRecordDao {
	/**
	 * 新增开门记录
	 * @param xqCode
	 * @param openRecord
	 * @return
	 */
	int saveOpenRecord(@Param("xqCode") String xqCode, @Param("openRecord") Map<String,Object> openRecord);

	/**
	 * 查询所有的开门方式 hjt
	 * @return
	 */
	List<Map<String,Object>> queryOpenType();

	/**
	 * 查询某一天时间某种开门方式的总数  hjt
	 * @param params
	 * @return
	 */
	int querySumOneDayOpenRecordByOpenType(Map<String,Object> params);

	/**
	 * 查询某一天时间所有开门方式的总数  hjt
	 * @param params
	 * @return
	 */
//	int querySumOneDayOpenRecord(Map<String,Object> params);

	/**
	 *  查询出某个时间段，每一天的开门次数(默认最近7天) hjt
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> querySumOneDayOpenRecordList(Map<String,Object> params);

	/**
	 * 查询总数  hjt
	 * @param params
	 * @return
	 */
	int queryTotalOpenRecord(Map<String,Object> params);

	/**
	 * 开门记录列表查询  hjt
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryOpenRecordList(Map<String,Object> params);

}
