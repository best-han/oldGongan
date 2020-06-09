package com.windaka.suizhi.manageport.dao;

import com.windaka.suizhi.manageport.model.FaceTypePerson;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface FaceTypePersonDao {
	/**
	 * 人脸-人脸类别维护
	 * @param faceTypePerson
	 * @return
	 */
	int saveFaceTypePerson(FaceTypePerson faceTypePerson);

	/**
	 * 查询所有人脸类型
	 * @return
	 */
	List<Map<String,Object>> queryFaceTypes();

	/**
	 *  人脸-人脸类别删除(同一小区可多个删除) hjt
	 * @param params
	 */
	int delFaceTypePerson(Map<String,Object> params);
}
