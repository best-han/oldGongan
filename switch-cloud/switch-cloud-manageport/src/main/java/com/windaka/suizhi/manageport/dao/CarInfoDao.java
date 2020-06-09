package com.windaka.suizhi.manageport.dao;

import com.windaka.suizhi.manageport.model.CarInfo;
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
	int updateCarInfo(Map<String,Object> carInfo);

	/**
	 * 根据carCode和xqCode查询数据
	 * @param
	 * @return
	 */
	@Select("SELECT count(*) FROM car_info WHERE manage_id = #{manageId} AND xq_code= #{xqCode} AND 'status'=0")
	int queryByCarInfo(@Param("manageId") String manageId,@Param("xqCode") String xqCode);

	/**
	 * 删除车辆信息
	 * @param xqCode
	 * @param
	 * @return
	 */
	//@Update("UPDATE car_info SET `status`=1 WHERE xq_code=#{xqCode} AND car_code=#{carCode} ")
	int deleteCarInfo(@Param("xqCode") String xqCode, @Param("manageId") String manageId);

	/**
	 * 功能描述: 获取最大ID值
	 * @auther: lixianhua
	 * @date: 2019/12/11 19:20
	 * @param:
	 * @return:
	 */
    Integer getMaxId();
	/**
	 * 功能描述: 根据manageId和xqCode获取主键
	 * @auther: lixianhua
	 * @date: 2019/12/11 19:49
	 * @param:
	 * @return:
	 */
	int getIdByCondion(Map<String, Object> map);
}
