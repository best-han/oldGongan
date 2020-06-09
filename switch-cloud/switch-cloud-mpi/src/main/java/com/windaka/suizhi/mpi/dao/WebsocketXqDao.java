package com.windaka.suizhi.mpi.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


@Mapper
public interface WebsocketXqDao {


	/**
	 * 根据Code查询小区
	 * @param xqCode
	 * @return
	 */
	Map<String,Object> queryByxqCode(@Param("xqCode") String xqCode);
	/**
	 * 根据Code和userId查询小区
	 * @param xqCode
	 * @return
	 */
	Map<String,Object> queryByxqCodeUserId(@Param("xqCode") String xqCode,@Param("userId")String userId);

	/**
	 * 查询小区列表总条数
	 * @param params
	 * @return
	 */
	int totalRows(@Param("params") Map<String, Object> params);

	/**
	 * 查询小区列表
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryXqList(@Param("params") Map<String, Object> params);

	/**
	 * 根据areaId查询小区列表
	 * @param
	 * @return
	 */
	List<Map<String,Object>> queryListXqByAreaId(String areaId);
	/**
	 * 根据areaId和userId查询小区列表
	 * @param
	 * @return
	 */
	List<Map<String,Object>> queryListXqByAreaIdUserId(String areaId,@Param("userId")String userId);

	/**
	 * 查询统计－小区信息
	 * @param params
	 * @return
	 *
	 * @author pxl
	 * @create 2019-05-28 16:37
	 */
	Map<String,Object> queryCountInfo(@Param("params") Map<String, Object> params);

}
