package com.windaka.suizhi.manageport.service;

import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.manageport.model.WyInfo;
import com.windaka.suizhi.manageport.model.Xq;
import com.windaka.suizhi.common.exception.OssRenderException;
import org.apache.ibatis.annotations.Param;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface XqService {
	/**
	 * 小区新增
	 *
	 * @param
	 */
	void saveXq(Map<String,Object> params) throws OssRenderException, IOException;

	/**
	 * 小区修改
	 *
	 * @param xq
	 */
	void updateXq(Xq xq) throws OssRenderException, IOException;

	/**
	 * 小区删除
	 *
	 * @param xqCode
	 */
	void deleteXq(String xqCode) throws OssRenderException, IOException;

	/**
	 * 根据Code查询小区
	 *
	 * @param xqCode
	 */
	Map<String,Object> queryByCode(String xqCode) throws OssRenderException;

	/**
	 * 查询小区列表
	 *
	 * @param params
	 */
	Page<Map<String,Object>> queryXqList(Map<String, Object> params) throws OssRenderException;
	/**
	 * 查询所有有所有信息的小区列表
	 *
	 * @param params
	 */
	List<Map<String,Object>> queryXqListForApp(Map<String, Object> params) throws OssRenderException;

	/**
	 * 根据区域查询小区 hjt
	 * @param areaId
	 * @return
	 * @throws OssRenderException
	 */
	List<Map<String,Object>> queryListXqByAreaId(String areaId) throws OssRenderException;

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
	 * 查询列表,查询统计－小区信息
	 * @param params
	 *
	 * @author pxl
	 * @create 2019-05-29 09:14
	 */
	Map<String,Object> queryCountInfo(Map<String, Object> params) throws OssRenderException;

	/**
	 * 所有物业以及下属小区列表查询 hjt
	 * @param
	 * @return
	 * @throws OssRenderException
	 */
	List<WyInfo> queryWyAndXqList() throws OssRenderException;
}
