package com.windaka.suizhi.webapi.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.webapi.dao.CarInfoDao;
import com.windaka.suizhi.webapi.model.CarInfo;
import com.windaka.suizhi.webapi.service.CarInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 车辆信息
 * @Author wcl
 * @Date 2019/8/20 0020 上午 11:29
 */
@Slf4j
@Service
public class CarInfoServiceImpl implements CarInfoService {
	@Autowired
	private CarInfoDao carInfoDao;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveCarInfo(String xqCode,List<Map<String,Object>> carInfos) throws OssRenderException {

		for(int i=0;i<carInfos.size();i++) {
			Map<String, Object> carInfo = carInfos.get(i);
			carInfo.put("personCode",carInfo.get("ownerId"));
			carInfo.remove("ownerId",carInfo.get("ownerId"));
			if (carInfo.get("parkType") == ""){
				carInfo.put("parkType",null);
			}
			if (carInfo.get("listType") == ""){
				carInfo.put("listType",null);
			}
			LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
			carInfo.put("creatTime",new Date());
			carInfo.put("creatBy",loginAppUser.getUserId());
			carInfo.put("updateTime",new Date());
			carInfo.put("updateBy",loginAppUser.getUserId());
			if(carInfo.get("status")==null|| carInfo.get("status")==""){
				carInfo.put("status","1");
			}

			carInfoDao.saveCarInfo(xqCode,carInfo);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateCarInfo(Map<String, Object> params) throws OssRenderException {
		String carCode = (String) params.get("carCode");
		if (StringUtils.isBlank(carCode)) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED, "carCode不能为空");
		}
		String xqCode = (String) params.get("xqCode");
		if (StringUtils.isBlank(xqCode)) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED, "xqCode不能为空");
		}
		if (carInfoDao.queryByCarInfo(carCode,xqCode) > 0) {
			LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
			CarInfo carInfo = new CarInfo();
			carInfo.setXqCode((String) params.get("xqCode"));
			carInfo.setCarCode((String) params.get("carCode"));
			carInfo.setCarNum((String) params.get("carNum"));
			carInfo.setCarNumColor((String) params.get("carNumColor"));
			carInfo.setCarType((String) params.get("carType"));
			carInfo.setCarBrand((String) params.get("carBrand"));
			carInfo.setCarColor((String) params.get("carColor"));
			carInfo.setCarMark((String) params.get("carMark"));
			carInfo.setCarStatus((String) params.get("carStatus"));
			carInfo.setCarPic((String) params.get("carPic"));
			carInfo.setPersonCode((String) params.get("personCode"));
			carInfo.setMemo((String) params.get("memo"));
			if (params.get("parkType")==""){
				carInfo.setParkType(null);
			}else {
				carInfo.setParkType(Integer.parseInt((String) params.get("parkType")));
			}
			carInfo.setParkinglotCode((String) params.get("parkinglotCode"));
			if (params.get("listType")==""){
				carInfo.setListType(null);
			}else {
				carInfo.setListType(Integer.parseInt((String) params.get("listType")));
			}
			carInfo.setStatus((String) params.get("status"));
			carInfo.setRemarks((String) params.get("remarks"));
			carInfo.setExtend_s1((String) params.get("extend_s1"));
			carInfo.setUpdateBy(loginAppUser.getUserId());
			carInfo.setUpdateTime(new Date());

			//更新数据
			carInfoDao.updateCarInfo(carInfo);
		}else {
			throw new OssRenderException(ReturnConstants.CODE_FAILED, "该车辆信息不存在");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteCarInfo(Map<String, Object> params) throws OssRenderException {
		String carCode = (String) params.get("carCode");
		if (StringUtils.isBlank(carCode)) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED, "carCode不能为空");
		}
		String xqCode = (String) params.get("xqCode");
		if (StringUtils.isBlank(xqCode)) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED, "xqCode不能为空");
		}

		if (carInfoDao.queryByCarInfo(carCode,xqCode) > 0) {
			carInfoDao.deleteCarInfo(xqCode,carCode);
		}else {
			throw new OssRenderException(ReturnConstants.CODE_FAILED, "该车辆信息不存在");
		}

	}
}
