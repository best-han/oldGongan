package com.windaka.suizhi.webapi.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface CaptureService {
	/**
	 * 新增摄像机
	 *
	 * @param params
	 */
	void saveCapture(Map<String, Object> params) throws OssRenderException, IOException;

	/**
	 * 修改摄像机
	 *
	 * @param params
	 */
	void updateCapture(Map<String, Object> params) throws OssRenderException, IOException;

	/**
	 * 删除摄像机
	 * @param xqCode
	 * @param captureIds
	 */
//	void deleteCapture(String captureId) throws OssRenderException;
	void deleteCapture(String xqCode, ArrayList captureIds) throws OssRenderException, IOException;

	/**
	 * 根据areaId查询摄像机
	 *
	 * @param xqCode
	 */
	String queryXqByXqCode(String xqCode) throws OssRenderException;

	/**
	 * 根据areaId查询xqCode和xqName
	 *
	 * @param areaId
	 */
	List<Map<String,Object>> queryXqListByAreaId(String areaId) throws OssRenderException;

	/**
	 * 根据areaId查询xqCode和xqName
	 *
	 * @param xqCode
	 */
	List<Map<String,Object>> queryCaptureListByxqCode(String xqCode) throws OssRenderException;

	/**
	 * 查询监控设备列表  hjt
	 *
	 * @param
	 */
	List<Map<String,Object>> queryCaptureDeviceList(Map<String,Object> params) throws OssRenderException;

	/**
	 * 设备（摄像机）播放地址  hjt
	 *
	 * @param
	 */
	Map<String,Object> queryCaptureFLVByGbCode(Map<String,Object> params) throws OssRenderException;

	/**
	 * 查询所有摄像机设备在线数量
	 * @param
	 * @return
	 */
	int queryCaptureOnline(String xqCode) throws OssRenderException;

	/**
	 * 查询所有摄像机设备离线数量
	 * @param
	 * @return
	 */
	int queryCaptureNotOnline(String xqCode) throws OssRenderException;
}
