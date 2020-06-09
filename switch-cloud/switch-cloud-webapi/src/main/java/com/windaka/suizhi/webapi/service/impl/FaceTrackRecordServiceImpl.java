package com.windaka.suizhi.webapi.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.*;
import com.windaka.suizhi.webapi.config.DesConfig;
import com.windaka.suizhi.webapi.dao.FaceTrackRecordDao;
import com.windaka.suizhi.webapi.dao.XqServerDao;
import com.windaka.suizhi.webapi.service.FaceTrackRecordService;
import com.windaka.suizhi.webapi.util.VedioTimeUtil;
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
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import java.util.*;

/**
 * @Description TODO
 * @Author wcl
 * @Date 2019/5/9 0009 上午 10:16
 */
@Slf4j
@Service
public class FaceTrackRecordServiceImpl implements FaceTrackRecordService {

	@Autowired
	private RestTemplate restTemplate;//这样就可以直接调用api使用了或者直接new对象也一样

	@Autowired
	private FaceTrackRecordDao  faceTrackRecordDao;
	@Autowired
	private XqServerDao xqServerDao;


	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveFaceTrackRecord(String xqCode, List<Map<String, Object>> params) throws OssRenderException {
		if (StringUtils.isBlank(xqCode)) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区Code不能为空");
		}

		for (int i = 0; i < params.size(); i++) {
			Map<String, Object> param = params.get(i);
			LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
			if(ObjectUtils.isEmpty(params.get(i).get("creatBy"))){
				params.get(i).put("creatBy",loginAppUser.getUserId());
			}
			if(ObjectUtils.isEmpty(params.get(i).get("updateBy"))){
				params.get(i).put("updateBy",loginAppUser.getUserId());
			}
			faceTrackRecordDao.saveFaceTrackRecord(xqCode,param);
		}

	}

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
		if(!params.containsKey("endTime")|| params.get("endTime").equals("")){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"缺少endTime参数");
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

        String base64Im=(String)params.get("txImg");
		String fileType=base64Im.split(";")[0].split("/")[1];
		if((!fileType.equalsIgnoreCase("jpg"))&&(!fileType.equalsIgnoreCase("png"))&&(!fileType.equalsIgnoreCase("jpeg"))){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"只支持jpg，jpeg和png格式图片");
		}

		//Page<Map<String,Object>> pages=new Page<Map<String,Object>>();//hjt
		Map<String,Object> pages=new HashMap<>();
		List<Map<String,Object>> list=new ArrayList<>();
		//pages.setList(list);
		int countAll=0;
		List<String> xqServers=null;
		try{
			xqServers=xqServerDao.queryAllXqServerUrlByAreaId((String) params.get("areaId"));
		}catch(Exception e){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"没有可查的服务");
		}

		Hashtable  personCodeHt=new Hashtable();
		if(xqServers!=null){
			if(xqServers.size()>0){

				boolean hasPerson=false;
				String personCode="";
				boolean hasServer=false;
				for(int i=0;i<xqServers.size();i++){
					String url=xqServers.get(i);
					if(!url.equals("")&&RegexUtils.checkURL(url)){
						hasServer=true;
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
						try{
							responseEntity = restTemplate.postForEntity(url,formEntity, String.class);
							resultJson=JSONObject.parseObject(responseEntity.getBody());
						}catch (Exception e){
							log.info("[FaceTrackRecordService.queryImgByImg,参数：{},异常信息{}]","",e.getMessage());
							continue;
						}
						if(resultJson.getBoolean("success")){//表示获取成功
							JSONArray datas=resultJson.getJSONArray("data");
							if(datas.size()>0){
								hasPerson=true;
								for(int j=0;j<datas.size();j++){
									JSONObject dataJson=(JSONObject)datas.get(0);
									personCode=((String)(dataJson.get("personID")));
									if(!personCodeHt.containsKey(personCode)){//如果不存在这个id
										personCodeHt.put(personCode,personCode);
										params.put("personCode",personCode);
//								        countAll+=faceTrackRecordDao.totalRows(params);
										//hjt
										/*Map<String,Object> faceList=queryFaceTrackRecordList(params);
										if(faceList!=null && ((ArrayList)faceList.get("list")).size()>0){
											pages.getList().addAll((ArrayList)faceList.get("list"));
											countAll+=Integer.parseInt((String)faceList.get("totalRows"));
											pages.setTotalRows(countAll);
										}*/
										pages=queryFaceTrackRecordList(params);
									}else{
										continue;
									}

								}

							}
						}else{
							continue;
						}
					}

				}
				if(!hasServer){
					throw new OssRenderException(ReturnConstants.CODE_FAILED,"没有可查的服务");
				}
				if(!hasPerson){
					throw new OssRenderException(ReturnConstants.CODE_FAILED,"查无此人");
				}

			}else{
				throw new OssRenderException(ReturnConstants.CODE_FAILED,"没有可查的服务");
			}
		}
		return pages;

	}



}
