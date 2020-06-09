package com.windaka.suizhi.mpi.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface FaceTrackRecordDao {

	/**
	 * 人脸轨迹上传
	 * @param params
	 * @return
	 */
	int saveFaceTrackRecord(@Param("xqCode") String xqCode, @Param("params") Map<String, Object> params);

	/**
	 * 查询人脸轨迹列表总条数
	 * @param params
	 * @return
	 */
	int totalRows(@Param("params") Map<String, Object> params);

	/**
	 * 查询人脸轨迹列表总数（只查deviceLocation） hjt
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryFaceTrackRecordListAll(@Param("params") Map<String, Object> params);

	/**
	 * 查询人脸轨迹列表
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryFaceTrackRecordList(@Param("params") Map<String, Object> params);

	/**
	 * 查询人脸轨迹根据manageId
	 * @param
	 * @return
	 */
	Map<String,Object> queryFaceTrackRecordByManageId(@Param("manageId") String manageId);
}
