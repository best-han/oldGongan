package com.windaka.suizhi.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


@Mapper
public interface SubdistrictDao {

	/**
	 * 根据userId查询街道
	 * @param userId
	 * @return
	 */
	List<Map<String,Object>> querySubdistrictByUserId(@Param("userId") String userId);

	/**
	 * 绑定人员和街道关系
	 * @param params
	 * @return
	 */
	int saveUserSubdistrict(Map params);

	/**
	 * 删除该用户所有绑定的街道
	 * @param userId
	 * @return
	 */
	int deleteUserSubdistrict(String userId);


}
