package com.windaka.suizhi.webapi.dao;


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
	int totalRows(Map<String, Object> params);

	/**
	 * 功能描述: 根据条件查询异常总数
	 * @auther: lixianhua
	 * @date: 2019/12/18 12:56
	 * @param:
	 * @return:
	 */
    Integer queryTotalNum(Map<String, Object> params);
	/**
	 * 功能描述: 查询事件异常信息
	 * @auther: lixianhua
	 * @date: 2019/12/18 12:57
	 * @param:
	 * @return:
	 */
	List<Map<String, Object>> queryAbnormalAlarmList(Map<String, Object> params);
	/**
	 * 功能描述: 根据主键更新信息
	 * @auther: lixianhua
	 * @date: 2019/12/19 9:20
	 * @param:
	 * @return:
	 */
    int updateAlarmById(Map<String, Object> params);
	/**
	 * 功能描述: 根据条件查询异常信息
	 * @auther: lixianhua
	 * @date: 2020/1/9 12:24
	 * @param:
	 * @return:
	 */
    Map<String, Object> queryAbnormalByCondition(Map<String, Object> condition);
}
