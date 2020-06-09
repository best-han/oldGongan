package com.windaka.suizhi.webapi.dao;

import com.windaka.suizhi.webapi.model.FaceTypePerson;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface CarGroupCarDao {

    /**
     * 查询某种车辆类型的车的数量
	 * @param params
     * @return
     */
	int queryCarGroupCarNum(Map<String, Object> params);

	int saveCarGroupCar(Map<String, Object> params);

	int deleteCarGroupCar(Map<String, Object> params);

	int deleteCarGroupCarR(Map<String, Object> params);

	int deleteCarGroupCarPermenently(Map<String, Object> params);

	List<Map<String, Object>> queryCarGroupCar(Map<String, Object> params);

	List<Map<String, Object>> queryCarGroup(Map<String, Object> params);

	List<Map<String, Object>> queryHtUser(Map<String, Object> params);
}
