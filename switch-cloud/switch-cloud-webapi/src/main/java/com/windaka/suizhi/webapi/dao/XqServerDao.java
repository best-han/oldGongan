package com.windaka.suizhi.webapi.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface XqServerDao {

	/**
	 * 查询某个区里的所有小区服务列表
	 * @return
	 */
	List<String> queryAllXqServerUrlByAreaId(String areaId);


}
