package com.windaka.suizhi.webapi.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.PageUtil;
import com.windaka.suizhi.common.utils.TimesUtil;
import com.windaka.suizhi.webapi.dao.OpenRecordDao;
import com.windaka.suizhi.webapi.service.OpenRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description 开门记录
 * @Author wcl
 * @Date 2019/8/21 0021 上午 8:50
 */
@Slf4j
@Service
public class OpenRecordServiceImpl implements OpenRecordService {

	@Autowired
	private OpenRecordDao openRecordDao;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveOpenRecord(String xqCode,List<Map<String, Object>> openRecords) throws OssRenderException {

		if(xqCode==null|| StringUtils.isBlank(xqCode)){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"xqCode不能为空");
		}


		for(int i=0;i<openRecords.size();i++) {
			Map<String, Object> openRecord = openRecords.get(i);
			if(openRecord.get("swipeTime")==null|| openRecord.get("swipeTime")==""){
				openRecord.put("swipeTime",null);
			}
			if(openRecord.get("operateTime")==null|| openRecord.get("operateTime")==""){
				openRecord.put("operateTime",null);
			}
			if(openRecord.get("openType")!="42" || openRecord.get("openType")!="45" || openRecord.get("openType")!="49" || openRecord.get("openType")!="61" || openRecord.get("openType")!="1434" || openRecord.get("openType")!="1441"){
				openRecord.put("openType","0");
			}
			openRecordDao.saveOpenRecord(xqCode,openRecord);
		}
	}

	@Override
	public List<Map<String,Object>> queryOpenTypesOfPerson(Map<String,Object> params) throws OssRenderException {
//		String areaId = (String) params.get("areaId");
//		if (StringUtils.isBlank(areaId)) {
//			throw new OssRenderException(ReturnConstants.CODE_FAILED,"区域不能为空");
//		}
		if(params.get("areaId")!=null &&!params.get("areaId").toString().trim().equals(""))
			params.put("areaId",0);
		//所有的开门方式列表查出
		List<Map<String,Object>> list=openRecordDao.queryOpenType();
		//每一种开门方式中最近7天的数据，具体到每一天的数据都查出来
		SimpleDateFormat sdf1=new SimpleDateFormat("MM月dd日");
		SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal= Calendar.getInstance();
		//查询最近7天每一天的开门次数
		cal.add(Calendar.DATE,1);//含头不含尾，所以最后一天加1
		params.put("endTime",sdf2.format(cal.getTime()));
		cal.add(Calendar.DATE,-8);
		params.put("beginTime",sdf2.format(cal.getTime()));
		List<Map<String,Object>> sumOneDayList=openRecordDao.querySumOneDayOpenRecordList(params);


		String[] weekList=new String[7];//最近7天的日期作为结果值
		String[] weekListParam=new String[7];//作为查询传参
		cal= Calendar.getInstance();//重置
		for(int i=0;i<7;i++){
			Date date=cal.getTime();
			//System.out.println(sdf1.format(date));
			weekList[i]=sdf1.format(date);
			weekListParam[i]=sdf2.format(date);
			cal.add(Calendar.DATE,-1);
		}
		int sumNum;//某一种开门记录在最近7天的总开门次数
		int sumNumDays=0;//所有开门记录在最近7天的总开门次数
		for(Map map1:sumOneDayList){
			sumNumDays+=Integer.parseInt(map1.get("sumOneDay").toString());
		}
		for (Map map:list) {
			int[] num=new int[7];//开门次数（最近7天的）
			double[] percent={0,0,0,0,0,0,0};//百分比（最近7天的）
			//某种开门方式7天的数据
			params.put("openType",map.get("openType"));//查询参数
			sumNum=0;
			for(int i=0;i<7;i++){
				//Date date=cal.getTime();
				params.put("swipeTime",weekListParam[i]);//查询参数
				num[i]=openRecordDao.querySumOneDayOpenRecordByOpenType(params);//num[7]
				for(Map map1:sumOneDayList){//percent[7]
					if(map1.get("dayTime").equals(weekListParam[i])){
						percent[i]=new BigDecimal(num[i]).divide(
								new BigDecimal(Integer.parseInt(map1.get("sumOneDay").toString())),2,BigDecimal.ROUND_HALF_UP).doubleValue();
						break;
					}
				}
			}
			map.put("num",num);
			for (int i:num) {
				sumNum+=i;
			}
			map.put("sumNum",sumNum);
			if(sumNumDays!=0){//该开门记录在最近7天的总开门记录的百分比
				map.put("sumPercent",new BigDecimal(sumNum).divide(new BigDecimal(sumNumDays),2,BigDecimal.ROUND_HALF_UP).doubleValue());
			}else{
				map.put("sumPercent",0);
			}
			map.put("percent",percent);
			map.put("weekList",weekList);
		}
		return list;
	}

	@Override
	public Map<String,Object> queryOpenRecordList(Map<String,Object> params) throws OssRenderException{
		String areaId = (String) params.get("areaId");
		if (StringUtils.isBlank(areaId)) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"区域不能为空");
		}
		int totalRows = openRecordDao.queryTotalOpenRecord(params);
		List<Map<String,Object>> list = Collections.emptyList();
		if (totalRows > 0) {
			PageUtil.pageParamConver(params, true);
			list=openRecordDao.queryOpenRecordList(params);
		}
		Map<String,Object> mapResult=new HashMap<>();//返回结果map
		mapResult.put("list",list);
		mapResult.put("totalRows",totalRows);
		mapResult.put("currentPage", MapUtils.getInteger(params, PageUtil.PAGE));
		return mapResult;

	}

}
