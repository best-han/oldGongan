package com.windaka.suizhi.mpi.service.impl;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.strands.SuspendableCallable;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.*;

import com.windaka.suizhi.mpi.config.DesConfig;
import com.windaka.suizhi.mpi.config.SearchByImageConfig;
import com.windaka.suizhi.mpi.dao.FaceTrackRecordDao;
import com.windaka.suizhi.mpi.dao.XqServerDao;
import com.windaka.suizhi.mpi.service.FaceTrackRecordService;
import com.windaka.suizhi.mpi.utils.VedioTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * @Description TODO
 * @Author wcl
 * @Date 2019/5/9 0009 上午 10:16
 */
@Slf4j
@Service
public class FaceTrackRecordServiceImpl implements FaceTrackRecordService {

	@Autowired
	private SearchByImageConfig searchByImageConfig;

	@Autowired
	private RestTemplate restTemplate;//这样就可以直接调用api使用了或者直接new对象也一样

	@Autowired
	private FaceTrackRecordDao faceTrackRecordDao;
	@Autowired
	private XqServerDao xqServerDao;


	@Transactional(rollbackFor = Exception.class)
	@Override
	public Map<String, Object> queryFaceTrackRecordList(Map<String, Object> params) throws OssRenderException {

		//当前开始和结束日期是否正确
		String beginTime = (String)params.get("beginTime");
		String endTime = (String)params.get("endTime");
		if(!StringUtils.isBlank(beginTime)){
			if(TimesUtil.checkDateFormat(beginTime)){
				//params.put("beginTime",beginTime+" 00:00:00");
			}else{
				throw new OssRenderException(ReturnConstants.CODE_FAILED,"开始时间格式不正确");
			}
		}else {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"开始时间不能为空");
		}
		if(!StringUtils.isBlank(endTime)){
			if(TimesUtil.checkDateFormat(endTime)){
				//params.put("endTime",endTime+" 23:59:59");
			}else{
				throw new OssRenderException(ReturnConstants.CODE_FAILED,"结束时间格式不正确");
			}
		}else {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"结束时间不能为空");
		}
		String areaId = (String) params.get("areaId");
		if (StringUtils.isBlank(areaId)) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"区域不能为空");
		}
		String personCode = (String) params.get("personCode");
		if (StringUtils.isBlank(personCode)) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"personCode不能为空");
		}
		String contrastValue = (String) params.get("contrastValue");
		if (StringUtils.isBlank(contrastValue)) {
			params.put("contrastValue","0");
		}

		int totalRows = faceTrackRecordDao.totalRows(params);
		List<Map<String,Object>> list = Collections.emptyList();
		Map<String,Object> mapResult=new HashMap<>();//返回结果map
		if (totalRows > 0) {
			PageUtil.pageParamConver(params, true);
			list = faceTrackRecordDao.queryFaceTrackRecordList(params);
			for(Map map : list){
				map.put("capVedioBeginTime", VedioTimeUtil.capVedioBeginTime());
				map.put("capVideoEndTime", VedioTimeUtil.capVideoEndTime());
			}
			//计算经纬度中间值（找地图中间点）
			Double jing=0.0;
			Double wei=0.0;
			String deviceLocation="";
			List<Map<String,Object>> listAll = faceTrackRecordDao.queryFaceTrackRecordListAll(params);
			for(Map map : listAll){
				deviceLocation=(String)map.get("deviceLocation");
				jing+=Double.parseDouble(deviceLocation.substring(0,deviceLocation.indexOf(",")).trim());
				wei+=Double.parseDouble(deviceLocation.substring(deviceLocation.indexOf(",")+1).trim());
			}
			String center=String.format("%.6f",jing/totalRows)+","+String.format("%.6f",wei/totalRows);
			mapResult.put("center",center);
		}
		mapResult.put("list",list);
		mapResult.put("totalRows",totalRows);
		mapResult.put("currentPage", MapUtils.getInteger(params, PageUtil.PAGE));
		return mapResult;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	@SuppressWarnings("all")
	public Map<String, Object> queryImgByImg(Map<String, Object> params) throws OssRenderException{


		if(!params.containsKey("areaId")|| params.get("areaId").equals("")){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"缺少areaId参数");
		}
		if(!params.containsKey("txImg")|| params.get("txImg").equals("")){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"缺少txImg参数");
		}
		if(!params.containsKey("beginTime")|| params.get("beginTime").equals("")){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"缺少beginTime参数");
		}
		if(!TimesUtil.checkDateFormat((String) params.get("beginTime"))){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"beginTime时间格式不正确");
		}
		if(!params.containsKey("endTime")|| params.get("endTime").equals("")){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"缺少endTime参数");
		}
		if(!TimesUtil.checkDateFormat((String) params.get("endTime"))){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"endTime时间格式不正确");
		}
		if(!params.containsKey("page")|| params.get("page").equals("")){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"缺少pagesize参数");
		}
		if(!params.containsKey("limit")|| params.get("limit").equals("")){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"缺少pagenum参数");
		}
		if(!params.containsKey("contrastValue")|| params.get("contrastValue").equals("")){
			params.put("contrastValue","60");
		}

//		//验证当前用户查询权限
//		TJUtil.getInstance(htUserXQDao).checkAuth(params);
		String base64Im=(String)params.get("txImg");
		String fileType=base64Im.split(";")[0].split("/")[1];
		if((!fileType.equalsIgnoreCase("jpg"))&&(!fileType.equalsIgnoreCase("png"))&&(!fileType.equalsIgnoreCase("jpeg"))){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"只支持jpg，jpeg和png格式图片");
		}
		List<Map<String,Object>> xqServersTemp=null;
		String[] xqCodes=((String)params.get("xqCode")).split(",");
		List<String> xqServers=new ArrayList<>();
		try{
			xqServersTemp=xqServerDao.queryAllXqServerUrlByAreaId((String) params.get("areaId"));
			for(int i=0;i<xqServersTemp.size();i++){
				Map<String,Object> xqUrlMap=xqServersTemp.get(i);
				if(xqCodes!=null){
					for(int j=0;j<xqCodes.length;j++){
						if(((String)xqUrlMap.get("xqCode")).equals(xqCodes[j])){
							xqServers.add((String)xqUrlMap.get("url"));
						}
					}
				}
			}
		}catch(Exception e){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"没有可查的服务");
		}
//		xqServers.add("http://10.10.5.90:9002");
//		xqServers.add("http://10.10.5.90:9002");
//		xqServers.add("http://10.10.5.90:9002");
		Hashtable  personCodeHt=new Hashtable();//为了去除重复的personCode
		Map<String,Object> pages=null;//最终的返回结果
		if(xqServers.size()>0){


			boolean hasPerson=false;
			String personCode="";
			String personCodes="";
			boolean hasServer=false;

			final CountDownLatch countDownLatch=new CountDownLatch(xqServers.size());
			for(int i=0;i<xqServers.size();i++){//遍历请求小区服务
				final int j=i;
				Fiber<Integer> fiber = new Fiber<Integer>((SuspendableCallable) () -> {
					//这里用于测试内存占用量
					String url=xqServers.get(j)+searchByImageConfig.getSearchPath();
					if(url!=null&&!url.equals("")){
						MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.MULTIPART_FORM_DATA);
						Map<String,Object> requestParam=new HashMap<>();
						requestParam.put("contrastValue",Integer.parseInt((String) params.get("contrastValue")));
						String requestJson=JSONObject.toJSONString(requestParam);
						String desParam= DesUtil.encode(requestJson, DesConfig.DES_KEY);
						param.add("data",desParam);
						param.add("txImg", params.get("txImg"));
						HttpEntity<MultiValueMap<String,Object>> formEntity = new HttpEntity<>(param,headers);
						ResponseEntity<String> responseEntity=null;
						JSONObject resultJson=null;

//						ListenableFuture<ResponseEntity<String>> forEntity = (ListenableFuture<ResponseEntity<String>>) restTemplate.exchange(url, HttpMethod.POST,formEntity, String.class);
//						forEntity.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {
//							@Override
//							public void onFailure(Throwable ex) {
////								logger.error(String.format("回调GET请求 %s 失败，%s", url, ex.getMessage()));
//								countDownLatch.countDown();
//							}
//
//							@Override
//							public void onSuccess(ResponseEntity<String> result) {
////								logger.info(String.format("回调GET请求 %s 成功，返回值为%s", url, result.getBody()));
//								JSONObject resultJson=null;
//								resultJson=JSONObject.parseObject(result.getBody());
//								if(resultJson.getBoolean("success")){//表示获取成功
//									JSONArray datas=resultJson.getJSONArray("data");
//									if(datas.size()>0){
//										for(int k=0;k<datas.size();k++){
//											JSONObject dataJson=(JSONObject)datas.get(j);
//											String personCodeTemp=((String)(dataJson.get("personID")));
//											if(!personCodeHt.containsKey(personCodeTemp)){//如果不存在这个id
//												personCodeHt.put(personCodeTemp,personCodeTemp);
//											}
//										}
//
//									}
//								}
//								countDownLatch.countDown();
//							}
//						});

						try{
							log.info("*********************************请求管理端url入口"+j+":"+url);
							log.info("*********************************请求管理端入口参数"+j+":"+param);
							log.info("*********************************请求管理端入口开始时间"+j+":"+TimesUtil.getCurrentDateTime());
							responseEntity = restTemplate.postForEntity(url,formEntity, String.class);
							resultJson=JSONObject.parseObject(responseEntity.getBody());
							log.info("*********************************请求管理端url出口"+j+":"+url);
							log.info("*********************************请求管理端出口结果"+j+":"+resultJson);
							log.info("*********************************请求管理端出口开始时间"+j+":"+TimesUtil.getCurrentDateTime());
							if(resultJson.getBoolean("success")){//表示获取成功
								JSONArray datas=resultJson.getJSONArray("data");
								if(datas.size()>0){
									for(int k=0;k<datas.size();k++){
										JSONObject dataJson=(JSONObject)datas.get(k);
										String personCodeTemp=((String)(dataJson.get("personID")));
										if(!personCodeHt.containsKey(personCodeTemp)){//如果不存在这个id
											personCodeHt.put(personCodeTemp,personCodeTemp);
										}
									}

								}
							}
							countDownLatch.countDown();
						}catch (Exception e){
							log.info("[FaceTrackRecordServiceImpl.queryImgByImg,url：{},异常信息{}]",url,e.getMessage());
							countDownLatch.countDown();
						}

					}
					return 0;
				});
				//开始执行
				fiber.start();

			}
			while(countDownLatch.getCount()>0);
			//遍历value
			Enumeration<String> element = personCodeHt.elements();
			while( element. hasMoreElements() ){
				personCodes+="'"+element.nextElement()+"'"+",";
			}
			if(!personCodes.equals("")){
				personCodes=personCodes.substring(0,personCodes.length()-1);
				params.put("personCode",personCodes);
//								        countAll+=faceTrackRecordDao.totalRows(params);
										/*Map<String,Object> faceList=queryFaceTrackRecordList(params);
										if(faceList!=null && ((ArrayList)faceList.get("list")).size()>0){
											pages.getList().addAll((ArrayList)faceList.get("list"));
											countAll+=Integer.parseInt((String)faceList.get("totalRows"));
											pages.setTotalRows(countAll);
										}*/
				pages=queryFaceTrackRecordList(params);
			}else{
				throw new OssRenderException(ReturnConstants.CODE_FAILED,"查无此人");
			}

//			if(!hasServer){
//				throw new OssRenderException(ReturnConstants.CODE_FAILED,"没有可查的服务");
//			}
//			if(!hasPerson){
//				throw new OssRenderException(ReturnConstants.CODE_FAILED,"查无此人");
//			}

		}else{
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"没有可查的服务");
		}
        if(pages!=null){
			return pages;
		}else{
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"查无此人");
		}

	}



}
