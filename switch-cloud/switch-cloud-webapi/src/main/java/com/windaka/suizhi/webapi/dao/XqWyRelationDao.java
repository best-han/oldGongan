package com.windaka.suizhi.webapi.dao;

import com.windaka.suizhi.webapi.model.WyInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface XqWyRelationDao {
	int saveXqWyRelation(String xqCode,String wyCode);
	int deleteXqWyRelation(String xyCode);

	/**
	 * 根据小区Codes，获取小区对应的物业Codes(多个物业用逗号进行分隔)
	 * @param xqCodes 小区Codes
	 * @return
	 *
	 * @author pxl
	 * @create 2019-05-27 16:39
	 */
	String queryWYCodesByXQCodes(@Param("xqCodes") String xqCodes);

	/**
	 * 所有物业以及下属小区列表查询 hjt
	 * @return
	 */
	List<WyInfo> queryWyAndXqList();
}
