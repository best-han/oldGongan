package com.windaka.suizhi.manageport.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
* @Description: 车辆出入场记录
* @Author: Ms.wcl
* @Date: 2019/8/22 0022
*/
public interface CarAccessRecordService {

	/**
	 * 批量保存车辆出入场记录
	 *
	 * @param xqCode
	 * @param carAccessRecords
	 */
	void saveCarAccessRecord(String xqCode, List<Map<String,Object>> carAccessRecords) throws OssRenderException, IOException;
}
