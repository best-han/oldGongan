package com.windaka.suizhi.webapi.service;

import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.common.exception.OssRenderException;

import java.util.List;
import java.util.Map;

public interface AbnormalRecordService {


	/**
     *  异常行为列表查询(分页)
	 * @param params
     * @return
     * @throws OssRenderException
	 */
	Page<Map<String, Object>> queryAbnormalRecordPageList(Map<String, Object> params) throws OssRenderException;
	/**
	 * 功能描述: 获取事件报警
	 * @auther: lixianhua
	 * @date: 2019/12/18 12:53
	 * @param:
	 * @return:
	 */
    Map<String, Object> queryAbnormalList(Map<String, Object> params) throws OssRenderException;
	/**
	 * 功能描述: 根据主键获取异常报警信息
	 * @auther: lixianhua
	 * @date: 2020/1/9 12:22
	 * @param:
	 * @return:
	 */
    Map<String, Object> queryAlarmAbnormalById(Integer id) throws OssRenderException;
}
