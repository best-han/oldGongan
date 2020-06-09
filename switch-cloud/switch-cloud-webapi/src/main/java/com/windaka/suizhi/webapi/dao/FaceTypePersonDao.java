package com.windaka.suizhi.webapi.dao;

import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.webapi.model.FaceTypePerson;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface FaceTypePersonDao {
	/**
	 * 人脸-人脸类别维护
	 * @param params
	 * @return
	 * 人员布控-新增 ygy
	 */
	int saveFaceTypePerson(Map<String, Object> params);

	/**
	 * 查询所有人脸类型
	 * @return
	 */
	List<Map<String,Object>> queryFaceTypes();

	/**
	 *  人脸-人脸类别删除(同一小区可多个删除) hjt
	 *  人员布控-删除 ygy
	 * @param params
	 */
	int delFaceTypePerson(Map<String, Object> params);
	int deletFaceTypePerson(Map<String, Object> params);

	/**
	 * 查询某种人脸类型的人的数量
	 * @param params
	 * @return
	 */
	int queryFaceTypePersonNum(Map<String, Object> params);

	/**
	 * 人员布控-列表查询 ygy
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> faceFaceTypeList(Map<String, Object> params);
//	int faceFaceTypeListTotal(Map<String, Object> params);//后台分页 弃用
//	List<Map<String,Object>> crimeFaceTypeList(Map<String, Object> params);//犯罪人员列表

	/**
	 * 获取最大ID值
	 * @return
	 */
	int getMaxRecordId();

	void deleteFaceTypePersonByCode(String id);

}
