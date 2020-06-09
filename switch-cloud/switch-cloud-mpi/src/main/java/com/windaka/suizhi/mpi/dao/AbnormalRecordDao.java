package com.windaka.suizhi.mpi.dao;


import org.apache.ibatis.annotations.Mapper;

import com.windaka.suizhi.mpi.model.CarInOut;
import com.windaka.suizhi.mpi.model.PersonInOut;
import com.windaka.suizhi.mpi.model.RecordAbnormal;

import java.util.List;
import java.util.Map;

/**
* @Description: 异常行为
* @Param:  
* @return:  
* @Author: hjt
* @Date: 2019/11/1
*/
@Mapper
public interface AbnormalRecordDao {

	/**
	 * 查询
	 * @param
	 * @return
	 */
	List<RecordAbnormal> queryAbnormalRecordById(int id);
	
	List<PersonInOut> queryPersonInOut(int id);
	
	List<CarInOut> queryCarInOut(int id);

	/**
	 * 获取当前最大主键
	 */
    int getMaxRecordId(Map<String, Object> params);
}
