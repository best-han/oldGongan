package com.windaka.suizhi.manageport.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.PageUtil;
import com.windaka.suizhi.common.utils.TimesUtil;
import com.windaka.suizhi.manageport.dao.CallRecordDao;
import com.windaka.suizhi.manageport.service.CallRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description 通话记录
 * @Author wcl
 * @Date 2019/8/21 0021 上午 10:02
 */
@Slf4j
@Service
public class CallRecordServiceImpl implements CallRecordService {

	@Autowired
	private CallRecordDao callRecordDao;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveCallRecord(String xqCode, List<Map<String, Object>> callRecords) throws OssRenderException {
		if(xqCode==null|| StringUtils.isBlank(xqCode)){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"xqCode不能为空");
		}

		for(int i=0;i<callRecords.size();i++) {
			Map<String, Object> callRecord = callRecords.get(i);
			if(callRecord.get("callTime")==""){
				callRecord.put("callTime",null);
			}

			callRecordDao.saveCallRecord(xqCode,callRecord);
		}
	}

	@Override
	public Map<String,Object> queryCallRecordList(Map<String,Object> params) throws OssRenderException{
		String areaId = (String) params.get("areaId");
		if (StringUtils.isBlank(areaId)) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"区域不能为空");
		}
		//当前开始和结束日期是否正确
		String beginTime = (String)params.get("beginTime");
		String endTime = (String)params.get("endTime");
		SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal= Calendar.getInstance();
		if(!StringUtils.isBlank(endTime)){
			if(!TimesUtil.checkDateFormat(endTime)){
				throw new OssRenderException(ReturnConstants.CODE_FAILED,"结束时间格式不正确");
			}
		}else {//默认最近7天
			//throw new OssRenderException(ReturnConstants.CODE_FAILED,"结束时间不能为空");
			cal.add(Calendar.DATE,1);//含头不含尾，所以最后一天加1
			params.put("endTime",sdf2.format(cal.getTime()));
		}
		if(!StringUtils.isBlank(beginTime)){
			if(!TimesUtil.checkDateFormat(beginTime)){
				throw new OssRenderException(ReturnConstants.CODE_FAILED,"开始时间格式不正确");
			}
		}else {//默认最近7天
			//throw new OssRenderException(ReturnConstants.CODE_FAILED,"开始时间不能为空");
			cal.add(Calendar.DATE,-8);
			params.put("beginTime",sdf2.format(cal.getTime()));
		}
		int totalRows = callRecordDao.queryTotalCallRecord(params);
		List<Map<String,Object>> list = Collections.emptyList();
		if (totalRows > 0) {
			PageUtil.pageParamConver(params, true);
			list=callRecordDao.queryCallRecordList(params);
		}
		Map<String,Object> mapResult=new HashMap<>();//返回结果map
		mapResult.put("list",list);
		mapResult.put("totalRows",totalRows);
		mapResult.put("currentPage", MapUtils.getInteger(params, PageUtil.PAGE));
		return mapResult;
	}
}
