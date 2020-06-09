package com.windaka.suizhi.webapi.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.windaka.suizhi.webapi.model.CarAttr;
import com.windaka.suizhi.webapi.model.FacePersonAttr;
import com.windaka.suizhi.webapi.model.Water;


/**
 * 
 * @project: switch-cloud-manageport
 * @Description: 
 * @author: yangkai
 * @date: 2019年12月5日 下午4:38:28
 */
@Mapper
public interface FacePersonCaptureDao {

	int deleteByPrimaryKey(Long id);

    int insert(FacePersonAttr record);

    int insertSelective(FacePersonAttr record);

    FacePersonAttr selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FacePersonAttr record);

    int updateByPrimaryKey(FacePersonAttr record);
    
    List<FacePersonAttr> getNightReturnPerson(@Param("params")Map<String, Object> params);
    
    List<CarAttr> getNightReturnCar(@Param("params")Map<String, Object> params);
    
    List<Water> waterRate(@Param("params")Map<String, Object> params);
    
    List<Water> groupResearch(@Param("params")Map<String, Object> params);
    
    List<Water> dayRentalResearch(@Param("params")Map<String, Object> params);
    
    @Select("select water_used from house_room_arrearage where room_id =#{manageId}")
    String selectWaterYield(@Param("manageId") String manageId);
    
    @Select("select CONCAT(name,',',phone) from zs_person_info where manage_id =#{manageId}")
    String selectPhone(@Param("manageId") String manageId);
    
    @Select("select count(*) from house_owner_room where room_id =#{manageId}")
    int selectPersonNum(@Param("manageId") String manageId);
    
    int totalRows(@Param("params")Map<String, Object> params);
    
    int totalCarRows(@Param("params")Map<String, Object> params);
    
    int totalWateRows(@Param("params")Map<String, Object> params);
    
    int totalGroupRows(@Param("params")Map<String, Object> params);
    
    int totalDayRentalResearch(@Param("params")Map<String, Object> params);
    
    /**
     * 功能描述: 获取最大人员抓拍主键
     * @auther: lixianhua
     * @date: 2019/12/7 19:17
     * @param:
     * @return:
     */
    int getMaxId();
}
