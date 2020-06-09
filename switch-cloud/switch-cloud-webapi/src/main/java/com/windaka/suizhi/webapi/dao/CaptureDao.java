package com.windaka.suizhi.webapi.dao;

import com.windaka.suizhi.webapi.model.Capture;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
* @Description: 摄像机设备
* @Param:  
* @return:  
* @Author: Ms.wcl
* @Date: 2019/7/16 0016
*/
@Mapper
public interface CaptureDao {

	/**
	 * 新增摄像机设备
	 * @param capture
	 * @return
	 */
	int saveCapture(Capture capture);

	/**
	 * 修改摄像机设备
	 * @param capture
	 * @return
	 */
	int updateCapture(Capture capture);

	/**
	 * 删除摄像机设备
	 * @param xqCode
	 * @return
	 */
	@Delete("delete from zs_capture_info where xq_code=#{xqCode} and capture_id = #{captureId}")
//	int deleteCapture(@Param("captureId") String captureId);
	int deleteCapture(@Param("xqCode")String xqCode, @Param("captureId")String captureId);

	/**
	 * 根据captureId查询数据
	 * @param captureId
	 * @return
	 */
	@Select("SELECT count(*) FROM zs_capture_info WHERE capture_id = #{captureId}")
	int queryByCaptureId(@Param("captureId") String captureId);

	/**
	 * 根据xqCode查询xqName
	 * @param xqCode
	 * @return
	 */
	@Select("SELECT t.name as xqName FROM ht_xq_info t WHERE t.status=0 and t.code=#{xqCode}")
	String queryXqByXqCode(@Param("xqCode") String xqCode);
	/**
	 * 根据areaId查询xqCode和xqName
	 * @param areaId
	 * @return
	 */
	@Select("SELECT t.code as xqCode,t.name as xqName FROM ht_xq_info t WHERE t.status =0 AND t.sso_regionalcode in (select area_id from ht_area where parent_ids like concat('%',#{areaId},'%'))")
	List<Map<String,Object>> queryXqListByAreaId(@Param("areaId") String areaId);

	/**
	 * 根据xqCode查询摄像机设备列表
	 * @param xqCode
	 * @return
	 */
	@Select("select c.manage_id as captureId,c.device_addr_name as deviceAddr,c.device_name as deviceName,c.device_location as deviceLocation,c.dchnl_device_code as capDevCode,c.dchnl_device_channel as capDevChannel,c.status,c.dchnl_rtsp as dchnlRtsp from face_capture_device c WHERE c.xq_code in (${xqCode})")
	List<Map<String,Object>> queryCaptureListByxqCode(@Param("xqCode") String xqCode);

	/**
	 * 查询摄像机设备列表  hjt
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryCaptureDeviceList(Map params);

	/**
	 * 查询所有摄像机设备在线数量
	 * @param
	 * @return
	 */
	@Select("select count(*) from zs_capture_info where `status` = '1' and xq_code in (${xqCode})")
	int queryCaptureOnline(@Param("xqCode") String xqCode);

	/**
	 * 查询所有摄像机设备离线数量
	 * @param
	 * @return
	 */
	@Select("select count(*) from zs_capture_info where `status` = '0' and xq_code in (${xqCode})")
	int queryCaptureNotOnline(@Param("xqCode") String xqCode);
}
