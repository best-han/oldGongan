package com.windaka.suizhi.manageport.dao;


import org.apache.ibatis.annotations.Mapper;

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
	 * 修改异常行为
	 * @param
	 * @return
	 */
	int updateAbnormalRecord(Map<String, Object> params);


	/**
	 * 列表查询
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryAbnormalRecordList(Map<String, Object> params);
	/**
	 * 列表查询（分页）
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryAbnormalRecordPageList(Map<String, Object> params);
	/**
	 * 总数
	 * @param params
	 * @return
	 */
	int queryTotalAbnormalRecord(Map<String, Object> params);

	/**
	 * 功能描述: 根据manageId获取主键
	 * @auther: lixianhua
	 * @date: 2019/12/8 8:16
	 * @param:
	 * @return:
	 */
    int getIdByManageId(Map<String, Object> params);
}
