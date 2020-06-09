package com.windaka.suizhi.mpi.dao;


import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
* @Description: 各记录表推送最大主键记录表
* @Param:  
* @return:  
* @Author: hjt
* @Date: 2019/11/1
*/
@Mapper
public interface MsgSocketIdDao {

	/**
	 * 查询对应记录表最大主键
	 * @param
	 * @return
	 */
	int queryMsgSocketMaxId(String recordName);

	/**
	 * 更新对应记录的最大主键值
	 * @return
	 */
	int updateMsgSocketMaxId(Map<String,Object> params);
	
	int updateCarTaskData();
	
	int updatePersonTaskData();
	
	int updateMsgTaskData();

}
