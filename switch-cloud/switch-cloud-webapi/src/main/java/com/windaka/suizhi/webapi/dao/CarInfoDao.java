package com.windaka.suizhi.webapi.dao;

import com.windaka.suizhi.webapi.model.CarInfo;
import org.apache.ibatis.annotations.*;

import java.util.Map;

/**
 * @Description: 车辆信息
 * @Param:
 * @return:
 * @Author: Ms.wcl
 * @Date: 2019/8/20
 */
@Mapper
public interface CarInfoDao {

	/**
	 * 新增车辆信息
	 * @param carInfo
	 * @param xqCode
	 * @return
	 */
	int saveCarInfo(String xqCode,Map<String,Object> carInfo);

	/**
	 * 修改车辆信息
	 * @param carInfo
	 * @return
	 */
	int updateCarInfo(CarInfo carInfo);

	/**
	 * 根据carCode和xqCode查询数据
	 * @param carCode
	 * @return
	 */
	@Select("SELECT count(*) FROM car_info WHERE car_code = #{carCode} AND xq_code= #{xqCode} AND 'status'=0")
	int queryByCarInfo(@Param("carCode") String carCode,@Param("xqCode") String xqCode);

	/**
	 * 删除车辆信息
	 * @param xqCode
	 * @param carCode
	 * @return
	 */
	@Update("UPDATE car_info SET `status`=1 WHERE xq_code=#{xqCode} AND car_code=#{carCode} ")
	int deleteCarInfo(@Param("xqCode") String xqCode, @Param("carCode") String carCode);



}
