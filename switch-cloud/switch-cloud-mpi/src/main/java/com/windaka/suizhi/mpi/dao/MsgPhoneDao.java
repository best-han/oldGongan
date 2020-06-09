package com.windaka.suizhi.mpi.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MsgPhoneDao {

	/**
	 * 查询所有发送报警至电话的列表
	 * @return
	 */
	List<String> queryMsgPhoneList();


}
