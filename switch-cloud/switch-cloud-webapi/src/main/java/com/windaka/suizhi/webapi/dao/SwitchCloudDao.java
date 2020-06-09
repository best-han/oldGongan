package com.windaka.suizhi.webapi.dao;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
* @Description: 数据库针对表级方法
* @Param:  
* @return:  
* @Author: hjt
* @Date:
*/
@Mapper
public interface SwitchCloudDao {

	/**
	 * 获取下一个自增主键（若表中数据会被删除，则max(id)不能用，用此方法）
	 * @param
	 * @return
	 */
    Integer queryNextId(String tableName);
}
