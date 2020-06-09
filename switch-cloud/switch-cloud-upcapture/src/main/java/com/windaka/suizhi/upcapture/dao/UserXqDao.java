package com.windaka.suizhi.upcapture.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * 用户小区关系<br>
 * 用户和小区是多对多关系，ht_user_xq是中间表
 * @author hjt
 */
@Mapper
public interface UserXqDao {

	/**
	 * 通过小区code查出下的所有username
	 * @param xqCode
	 * @return
	 */
	Set<String> queryUsernamesByXqCode(String xqCode);

}
