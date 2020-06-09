package com.windaka.suizhi.upcapture.dao;

import com.windaka.suizhi.upcapture.model.Capture;
import org.apache.ibatis.annotations.Mapper;

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
	 * 新增异常行为
	 * @param
	 * @return
	 */
	int saveAbnormalRecord(Map<String, Object> params);

	/**
	 * 修改摄像机设备
	 * @param
	 * @return
	 */
	int updateCapture(Capture capture);

	/**
	 * 功能描述: 根据manageId获取主键
	 * @auther: lixianhua
	 * @date: 2019/12/8 8:16
	 * @param:
	 * @return:
	 */
	Integer getMaxId();

}
