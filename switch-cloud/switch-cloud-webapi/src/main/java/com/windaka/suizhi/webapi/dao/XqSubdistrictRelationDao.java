package com.windaka.suizhi.webapi.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface XqSubdistrictRelationDao {

	/**
	 * 根据小区subdistrictId，获取小区其下的所有小区
	 * @param subdistrictId
	 * @return
	 * @author hjt
	 * @create
	 */
	List<Map<String,Object>> queryXqBySubdistrictId(String subdistrictId);

}
