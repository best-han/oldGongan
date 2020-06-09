package com.windaka.suizhi.webapi.service.impl;

import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.PageUtil;
import com.windaka.suizhi.common.utils.PropertiesUtil;
import com.windaka.suizhi.webapi.dao.AbnormalRecordDao;
import com.windaka.suizhi.webapi.service.AbnormalRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 异常行为 hjt
 */
@Slf4j
@Service
public class AbnormalRecordServiceImpl implements AbnormalRecordService {
	@Autowired
	private AbnormalRecordDao abnormalRecordDao;


	@Override
	public Page<Map<String, Object>> queryAbnormalRecordPageList(Map<String, Object> params) throws OssRenderException{
		int totalRows=abnormalRecordDao.totalRows(params);
		List<Map<String,Object>> list= Collections.emptyList();
		if (totalRows > 0) {
			PageUtil.pageParamConver(params, true);
			list = abnormalRecordDao.queryAbnormalRecordPageList(params);
		}
		return new Page<>(totalRows, MapUtils.getInteger(params,PageUtil.PAGE), list);
	}
	/**
	 * 功能描述: 获取事件报警信息
	 * @auther: lixianhua
	 * @date: 2019/12/18 12:54
	 * @param:
	 * @return:
	 */
	@Override
	public Map<String, Object> queryAbnormalList(Map<String, Object> params) throws OssRenderException {
		if (StringUtils.isBlank((String) params.get("limit"))) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED, "每页数量不能为空");
		}
		if (StringUtils.isBlank((String) params.get("page"))) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED, "当前页数不能为空");
		}
		Map<String, Object> resultMap = new HashMap<String, Object>(8);
		// 获取车辆报警总数
		Integer totalNum = abnormalRecordDao.queryTotalNum(params);
		resultMap.put("totalRows", totalNum);
		resultMap.put("currentPage", Integer.parseInt(params.get("page").toString()));
		if (totalNum>0) {
			params.put("start", (Integer.parseInt(params.get("page").toString()) - 1) * Integer.parseInt(params.get("limit").toString()));
			params.put("end", Integer.parseInt(params.get("page").toString()) * Integer.parseInt(params.get("limit").toString()));
			params.put("prefixUrl", PropertiesUtil.getLocalTomcatImageIp());
			// 获取车辆报警信息
			List<Map<String, Object>> list = abnormalRecordDao.queryAbnormalAlarmList(params);
			resultMap.put("list", list);
		}
		return resultMap;
	}
	/**
	 * 功能描述: 根据主键获取异常信息
	 * @auther: lixianhua
	 * @date: 2020/1/9 12:22
	 * @param:
	 * @return:
	 */
	@Override
	public Map<String, Object> queryAlarmAbnormalById(Integer id) throws OssRenderException {
		if (null == id) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED, "主键不能为空");
		}
		Map<String,Object> condition = new HashMap<>(8);
		condition.put("id",id);
		condition.put("prefixUrl",PropertiesUtil.getLocalTomcatImageIp());
		Map<String,Object> result = this.abnormalRecordDao.queryAbnormalByCondition(condition);
		return result;
	}

}
