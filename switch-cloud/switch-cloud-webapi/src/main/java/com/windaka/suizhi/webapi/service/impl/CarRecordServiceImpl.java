package com.windaka.suizhi.webapi.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.common.utils.PageUtil;
import com.windaka.suizhi.common.utils.PropertiesUtil;
import com.windaka.suizhi.common.utils.TimesUtil;
import com.windaka.suizhi.webapi.dao.CarRecordDao;
import com.windaka.suizhi.webapi.dao.XqDao;
import com.windaka.suizhi.webapi.service.CarRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description TODO
 * @Author wcl
 * @Date 2019/5/9 0009 上午 10:17
 */
@Slf4j
@Service
public class CarRecordServiceImpl implements CarRecordService {
	@Autowired
	CarRecordDao carRecordDao;
	@Autowired
	XqDao xqDao;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveCarAlarmRecords(Map<String, Object> map) throws OssRenderException {
		if(ObjectUtil.isNull(map.get("xqCode"))){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区Code不能为空");
		}
		//String xqCode=map.get("xqCode").toString();
		List<Map<String,Object>> list=(List<Map<String,Object>>)map.get("list");
		if(ObjectUtil.isNull(list) || list.size()<1){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"数据不能为空");
		}
		checkParamsAndReturn(list);
		LoginAppUser loginAppUser= AppUserUtil.getLoginAppUser();
		map.put("creBy",loginAppUser.getUserId());
		carRecordDao.saveCarAlarmRecords(map);
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveCarJeevesRecords(Map<String, Object> map) throws OssRenderException {
		if(ObjectUtil.isNull(map.get("xqCode"))){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区Code不能为空");
		}
		List<Map<String,Object>> list=(List<Map<String,Object>>)map.get("list");
		if(ObjectUtil.isNull(list) || list.size()<1){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"数据不能为空");
		}
		checkParamsAndReturn(list);
		LoginAppUser loginAppUser= AppUserUtil.getLoginAppUser();
		map.put("creBy",loginAppUser.getUserId());
		carRecordDao.saveCarJeevesRecords(map);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateCarRecord(Map<String, Object> map) throws OssRenderException {
		LoginAppUser loginAppUser= AppUserUtil.getLoginAppUser();
		map.put("updBy",loginAppUser.getUserId());
		map.put("clUser",loginAppUser.getCname());
		String faceTypeCode=(String)map.get("faceTypeCode");
		int i=0;
		if("51".equals(faceTypeCode)) {//车辆报警记录
			i=carRecordDao.updateCarAlarmRecord(map);
		}else{//52车辆占道
			i=carRecordDao.updateCarJeevesRecord(map);
		}
		if(i==0){
			throw new OssRenderException(ReturnConstants.MSG_OPER_FAILED,"无对应操作数据");
		}
	}

	@Override
	public Map queryCarRecord(Map<String, Object> params) throws OssRenderException{
		String faceTypeCode=(String)params.get("faceTypeCode");
		Map map = new HashMap();
		if("51".equals(faceTypeCode)) {//车辆报警记录
			map=carRecordDao.queryCarAlarmRecord(params.get("alarmId").toString());
		}else{//52车辆占道
			map=carRecordDao.queryCarJeevesRecord(params.get("alarmId").toString());
		}
		return map;
	}

	/**
	 * 校验上传参数是否为空，为空设置默认值
	 * @param list
	 * @return
	 */
	public void checkParamsAndReturn(List<Map<String,Object>> list){
		for(Map map:list){
			if(ObjectUtils.isEmpty(map.get("status"))){
				map.put("status","0");//状态（0正常 1删除 2停用）
			}
			if(ObjectUtils.isEmpty(map.get("clStatus"))){
				map.put("clStatus","0");//处理状态(0：未处理，1：已处理)
			}
			if(ObjectUtils.isEmpty(map.get("capTime"))){
				map.put("capTime",null);//
			}
			if(ObjectUtils.isEmpty(map.get("clTime"))){
				map.put("clTime",null);//
			}
		}
	}

	@Override
	public Map<String,Object> queryCarAccessRecordList(Map<String,Object> params) throws OssRenderException{
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
			cal.add(Calendar.DATE,-1);
			params.put("beginTime",sdf2.format(cal.getTime()));
		}
		int totalRows = carRecordDao.queryTotalCarAccessRecord(params);
		List<Map<String,Object>> list = Collections.emptyList();
		if (totalRows > 0) {
			PageUtil.pageParamConver(params, true);
			list=carRecordDao.queryCarAccessRecordList(params);
		}
		Map<String,Object> mapResult=new HashMap<>();//返回结果map
		mapResult.put("list",list);
		mapResult.put("totalRows",totalRows);
		mapResult.put("currentPage", MapUtils.getInteger(params, PageUtil.PAGE));
		return mapResult;
	}

	@Override
	public Map<String, Object> queryCarOverview(Map<String, Object> params)throws OssRenderException{
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
			cal.add(Calendar.DATE,-1);
			params.put("beginTime",sdf2.format(cal.getTime()));
		}
		Map<String, Object> mapResult = new HashMap();
		params.put("carDirect", "8");
		int inNum = this.carRecordDao.queryTotalCarAccessRecord(params);
		mapResult.put("inNum", Integer.valueOf(inNum));
		params.put("carDirect", "9");
		int outNum = this.carRecordDao.queryTotalCarAccessRecord(params);
		mapResult.put("outNum", Integer.valueOf(outNum));
		int strangeNum = this.carRecordDao.totalStrangCar(params);
		mapResult.put("strangeNum", Integer.valueOf(strangeNum));
		int jeevesNum = this.carRecordDao.totalJeevesCar(params);
		mapResult.put("jeevesNum", Integer.valueOf(jeevesNum));
		Map map = this.xqDao.queryCountInfo(params);
		if (MapUtils.isEmpty(map)) {
			mapResult.put("parkingNum", Integer.valueOf(0));
		} else {
			mapResult.put("parkingNum", map.get("parkingNum"));
		}
		return mapResult;
	}
	/**
	 * 功能描述: 获取报警车辆信息
	 * @auther: lixianhua
	 * @date: 2019/12/18 8:43
	 * @param:
	 * @return:
	 */
	@Override
	public Map<String, Object> queryAlarmCarList(Map<String, Object> params) throws OssRenderException {
		if (StringUtils.isBlank((String) params.get("limit"))) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED, "每页数量不能为空");
		}
		if (StringUtils.isBlank((String) params.get("page"))) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED, "当前页数不能为空");
		}
		Map<String, Object> resultMap = new HashMap<String, Object>(8);
		// 获取车辆报警总数
		Integer totalNum = carRecordDao.queryTotalNum(params);
		resultMap.put("totalRows", totalNum);
		resultMap.put("currentPage",  Integer.parseInt(params.get("page").toString()));
		if (totalNum>0) {
			params.put("start", (Integer.parseInt(params.get("page").toString()) - 1) * Integer.parseInt(params.get("limit").toString()));
			params.put("end", Integer.parseInt(params.get("page").toString()) * Integer.parseInt(params.get("limit").toString()));
			params.put("prefixUrl", PropertiesUtil.getLocalTomcatImageIp());
			// 获取车辆报警信息
			List<Map<String, Object>> list = carRecordDao.queryCarAlarmList(params);
			resultMap.put("list", list);
		}
		return resultMap;
	}
	/**
	 * 功能描述: 根据主键获取车辆报警信息
	 * @auther: lixianhua
	 * @date: 2020/1/9 11:04
	 * @param:
	 * @return:
	 */
	@Override
	public Map<String, Object> queryAlarmCarById(Integer id) throws OssRenderException {
		if (null == id) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED, "主键不能为空");
		}
		Map<String,Object> condition = new HashMap<>(8);
		condition.put("id",id);
		condition.put("prefixUrl",PropertiesUtil.getLocalTomcatImageIp());
		Map<String,Object> result = this.carRecordDao.queryCarAlarmByCondition(condition);
		return result;
	}


}
