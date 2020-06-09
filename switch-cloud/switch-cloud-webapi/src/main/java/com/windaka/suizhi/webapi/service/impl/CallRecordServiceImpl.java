package com.windaka.suizhi.webapi.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.PageUtil;
import com.windaka.suizhi.common.utils.TimesUtil;
import com.windaka.suizhi.webapi.dao.CallRecordDao;
import com.windaka.suizhi.webapi.service.CallRecordService;
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
