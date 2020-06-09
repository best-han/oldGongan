package com.windaka.suizhi.mpi.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface FaceCrimeFeatureDao {

	/**
	 * 根据id查询
	 * @param
	 * @return
	 */
	Map<String,Object> queryById(@Param("id") Integer id);

	/**
	 * 功能描述: 获取人员库信息
	 * @auther: lixianhua
	 * @date: 2020/1/5 8:35
	 * @param:
	 * @return:
	 */
    Map<String, Object> queryTypePerson(@Param("id")Integer crimeFeatureId);
}
