package com.windaka.suizhi.mpi.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface XqServerDao {

	/**
	 * 查询某个区里的所有小区服务列表
	 * @return
	 */
	List<Map<String,Object>> queryAllXqServerUrlByAreaId(String areaId);
	/**
	 * 查询小区内的服务地址
	 * @param xqCode
	 * @return
	 */
	String queryXqServerUrl(String xqCode);


}
