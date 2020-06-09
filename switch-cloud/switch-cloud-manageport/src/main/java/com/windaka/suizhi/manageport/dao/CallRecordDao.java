package com.windaka.suizhi.manageport.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description: 通话记录
 * @Param:
 * @return:
 * @Author: Ms.wcl
 * @Date: 2019/8/21
 */
@Mapper
public interface CallRecordDao {

	/**
	 * 新增开门记录
	 * @param xqCode
	 * @param callRecord
	 * @return
	 */
	int saveCallRecord(@Param("xqCode") String xqCode, @Param("callRecord") Map<String,Object> callRecord);

	/**
	 * 查询总数  hjt
	 * @param params
	 * @return
	 */
	int queryTotalCallRecord(Map<String,Object> params);

	/**
	 * 通话记录列表查询  hjt
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryCallRecordList(Map<String,Object> params);
}
