package com.windaka.suizhi.webapi.service.impl;

import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.webapi.dao.WyInfoDao;
import com.windaka.suizhi.webapi.model.WyInfo;
import com.windaka.suizhi.webapi.service.WyInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class WyInfoServiceImpl implements WyInfoService {
	@Autowired
	private WyInfoDao wyInfoDao;

	@Override
	public void saveWyInfo(WyInfo wyInfo) throws OssRenderException {
		//添加物业公司
		wyInfoDao.saveWyInfo(wyInfo);
	}

	@Override
	public void updateWyInfo(WyInfo wyInfo) throws OssRenderException {
		wyInfoDao.updateWyInfo(wyInfo);
	}

	@Override
	public void deleteWyInfo(String wyCode) throws OssRenderException {
		wyInfoDao.deleteWyInfo(wyCode);
	}

}
