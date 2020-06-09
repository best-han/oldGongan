package com.windaka.suizhi.mpi.dao;

import com.windaka.suizhi.mpi.model.FaceCaptureAttrModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface FacePersonAttrDao {

	/**
	 * 根据id查询
	 * @param
	 * @return
	 */
	Map<String,Object> queryByManageId(@Param("manageId") String manageId);
	/**
	 * 功能描述: 根据主键获取人员信息
	 * @auther: lixianhua
	 * @date: 2019/12/16 11:32
	 * @param:
	 * @return:
	 */
	Map<String, Object> selectById(String id);
	/**
	 * 功能描述: 获取人脸抓拍最新数据
	 * @auther: lixianhua
	 * @date: 2019/12/26 17:36
	 * @param:
	 * @return:
	 */
    List<FaceCaptureAttrModel> getMoreRecord(Map<String ,Object> map);

	/**
	 * 根据personId查询  hjt
	 * @param
	 * @return
	 */
	Map<String,Object> queryByPersonId(String personId);
	/**
	 * 功能描述: 获取最大抓拍ID
	 * @auther: lixianhua
	 * @date: 2020/1/14 16:24
	 * @param:
	 * @return:
	 */
	@Select("select MAX(id) from face_person_attr")
    Integer getFaceMaxId();
}
