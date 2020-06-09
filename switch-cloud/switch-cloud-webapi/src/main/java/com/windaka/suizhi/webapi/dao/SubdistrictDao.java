package com.windaka.suizhi.webapi.dao;

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
	 * 保存街道
	 * @param params
	 * @return
	 */
	int saveSubdistrict(Map<String,Object> params);

	/**
	 * 街道删除
	 * @param subdistrictId
	 * @return
	 */
	int deleteSubdistrict(String subdistrictId);

	/**
	 * 小区街道关系绑定
	 * @param params
	 */
	int saveXqSubdistrict(Map<String,Object> params);

	/**
	 * 删除该街道的所有绑定小区关系（清空）
	 * @param subdistrictId
	 * @return
	 */
	int deleteXqSubdistrict(String subdistrictId);


}
