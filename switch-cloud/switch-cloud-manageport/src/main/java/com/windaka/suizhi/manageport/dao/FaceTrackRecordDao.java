package com.windaka.suizhi.manageport.dao;

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
	int saveFaceTrackRecord(Map<String, Object> params);

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
	 * 功能描述: 获取最大Id
	 * @auther: lixianhua
	 * @date: 2019/12/11 8:27
	 * @param:
	 * @return:
	 */
    Integer getMaxRecordId();
}
