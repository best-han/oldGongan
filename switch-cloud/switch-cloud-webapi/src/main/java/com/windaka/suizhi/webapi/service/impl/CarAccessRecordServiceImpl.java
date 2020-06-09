package com.windaka.suizhi.webapi.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.webapi.dao.CarAccessRecordDao;
import com.windaka.suizhi.webapi.service.CarAccessRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Description 车辆出入场记录
 * @Author wcl
 * @Date 2019/8/22 0022 下午 4:24
 */
@Slf4j
@Service
public class CarAccessRecordServiceImpl implements CarAccessRecordService {

	@Autowired
	private CarAccessRecordDao carAccessRecordDao;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveCarAccessRecord(String xqCode, List<Map<String, Object>> carAccessRecords) throws OssRenderException {

		if(xqCode==null|| StringUtils.isBlank(xqCode)){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"xqCode不能为空");
		}

		for(int i=0;i<carAccessRecords.size();i++) {
			Map<String, Object> carAccessRecord = carAccessRecords.get(i);
			if(carAccessRecord.get("capTime")==null|| carAccessRecord.get("capTime")==""){
				carAccessRecord.put("capTime","0000-00-00 00:00:00");
			}
			carAccessRecordDao.saveCarAccessRecord(xqCode,carAccessRecord);
		}
	}
}
