package com.windaka.suizhi.webapi.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface OwnerRoomDao {

	/**
	 * 查询房屋居住人性质及其数量 hjt
	 * @return
	 */
	int queryOwnerLiveTypeNum(Map<String, Object> params);

	/**
	 * 查询一段时间内新增的租户数量
	 * @param params
	 * @return
	 */
	int queryRentOwnerNum(Map<String, Object> params);
	/**
	 * 查询一段时间内新增的常住人口数量
	 * @param params
	 * @return
	 */
	int queryPermanentOwnerNum(Map<String, Object> params);


}
