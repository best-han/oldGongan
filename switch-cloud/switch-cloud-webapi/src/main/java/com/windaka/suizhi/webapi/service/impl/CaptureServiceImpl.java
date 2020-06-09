package com.windaka.suizhi.webapi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.common.utils.PropertiesUtil;
import com.windaka.suizhi.common.utils.SqlFileUtil;
import com.windaka.suizhi.webapi.dao.CaptureDao;
import com.windaka.suizhi.webapi.model.Capture;
import com.windaka.suizhi.webapi.service.CaptureService;
import com.windaka.suizhi.webapi.util.SqlCreateUtil;
import lombok.extern.slf4j.Slf4j;
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

import java.io.IOException;
import java.util.*;

/**
 * @Description 摄像机设备
 * @Author wcl
 * @Date 2019/7/16 0016 下午 2:23
 */
@Slf4j
@Service
public class CaptureServiceImpl implements CaptureService {
	@Autowired
	private CaptureDao captureDao;

	@Autowired
	private RestTemplate restTemplate;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveCapture(Map<String, Object> params) throws OssRenderException, IOException {
		String captureId = (String) params.get("captureId");
		if (StringUtils.isBlank(captureId)) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"摄像机主键ID不能为空");

		}

		String xqCode = (String) params.get("xqCode");
		if (StringUtils.isBlank(xqCode)) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区Code不能为空");
		}

		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		Capture capture = new Capture();
		capture.setCaptureId(captureId);
		capture.setXqCode((String) params.get("xqCode"));
		capture.setDeviceAddr((String) params.get("deviceAddr"));
		capture.setDeviceName((String) params.get("deviceName"));
		capture.setDeviceLocation((String) params.get("deviceLocation"));
		capture.setCapDevCode((String) params.get("capDevCode"));
		capture.setCapDevChannel((String) params.get("capDevChannel"));
		capture.setStatus((String) params.get("status"));
		capture.setDchnlRtsp((String) params.get("dchnlRtsp"));

		//captureId已存在时，更新数据
		if (captureDao.queryByCaptureId(captureId) > 0)
		{
			capture.setUpdateBy(loginAppUser.getUserId());
			capture.setUpdateTime(new Date());

			//更新数据
			captureDao.updateCapture(capture);
		}else {
			capture.setCreatBy(loginAppUser.getUserId());
			capture.setCreatTime(new Date());
			capture.setUpdateBy(loginAppUser.getUserId());
			capture.setUpdateTime(new Date());

			//保存数据
			captureDao.saveCapture(capture);

		}

	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateCapture(Map<String, Object> params) throws OssRenderException, IOException {
		String captureId = (String) params.get("captureId");
		if (StringUtils.isBlank(captureId)) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED, "摄像机主键ID不能为空");
		}
		if (captureDao.queryByCaptureId(captureId) > 0) {
			LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
			Capture capture = new Capture();
			capture.setCaptureId(captureId);
			capture.setXqCode((String) params.get("xqCode"));
			capture.setDeviceAddr((String) params.get("deviceAddr"));
			capture.setDeviceName((String) params.get("deviceName"));
			capture.setDeviceLocation((String) params.get("deviceLocation"));
			capture.setCapDevCode((String) params.get("capDevCode"));
			capture.setCapDevChannel((String) params.get("capDevChannel"));
			capture.setStatus((String) params.get("status"));
			capture.setDchnlRtsp((String) params.get("dchnlRtsp"));
			capture.setUpdateBy(loginAppUser.getUserId());
			capture.setUpdateTime(new Date());

			//更新数据
			captureDao.updateCapture(capture);

		}else {
			throw new OssRenderException(ReturnConstants.CODE_FAILED, "该设备不存在");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteCapture(String xqCode, ArrayList captureIds) throws OssRenderException, IOException {
		if (StringUtils.isBlank(xqCode)) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区Code不能为空");
		}
		if (captureIds.size() > 0) {
			//可批量设备
			for (int i = 0; i < captureIds.size(); i++) {
				String captureId = (String) captureIds.get(i);
				captureDao.deleteCapture(xqCode,captureId);


			}
		}else {
			throw new OssRenderException(ReturnConstants.CODE_FAILED, "captureId不能为空");
		}

	}



	@Override
	public String queryXqByXqCode(String xqCode) throws OssRenderException {
		String xqName = captureDao.queryXqByXqCode(xqCode);
		return xqName;
	}


	@Override
	public List<Map<String, Object>> queryXqListByAreaId(String areaId) throws OssRenderException {
		List<Map<String,Object>> list = captureDao.queryXqListByAreaId(areaId);
		return list;
	}

	@Override
	public List<Map<String, Object>> queryCaptureListByxqCode(String xqCode) throws OssRenderException {
		List<Map<String,Object>> list = captureDao.queryCaptureListByxqCode(xqCode);
		return list;
	}

	@Override
	public List<Map<String, Object>> queryCaptureDeviceList(Map<String,Object> params) throws OssRenderException {
		List<Map<String,Object>> list = captureDao.queryCaptureDeviceList(params);
		return list;
	}

	@Override
	public Map<String, Object> queryCaptureFLVByGbCode(Map<String,Object> params) throws OssRenderException {
		if(ObjectUtils.isEmpty(params.get("gbCode"))){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"gbCode为空");
		}
		String gbCode=params.get("gbCode").toString();
		if(ObjectUtils.isEmpty(params.get("gbCodeseq"))){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"gbCodeseq为空");
		}
		String gbCodeseq=params.get("gbCodeseq").toString();
		/*HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> params1= new LinkedMultiValueMap<>();
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params1, headers);*/
/*		ResponseEntity<String> response = restTemplate.getForEntity(PropertiesUtil.CAPTURE_DEVICE_URI+"api/v1/device/list", String.class);
		String responseBody = response.getBody();
		JSONObject contentJson = JSONObject.parseObject(responseBody);
		Map<String,Object> contentMap=(Map<String,Object>) contentJson;
		List<Map<String,Object>> deviceList=(List<Map<String,Object>>) contentMap.get("DeviceList");

		String serial=null;
		for(Map map:deviceList){
			if(deviceName.equals(map.get("Name"))){
				serial= map.get("ID").toString();
				break;
			}
		}
		if(StringUtils.isBlank(serial)){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"无对应的设备serial");
		}*/
		Map<String, String> params1= new HashMap<>();
		params1.put("serial",gbCode);
		params1.put("channel",gbCodeseq);
		Map<String,Object> contentMap = restTemplate.getForObject(PropertiesUtil.CAPTURE_DEVICE_URI+"api/v1/stream/start?serial={serial}&channel={channel}", Map.class,params1);
		/*responseBody = response.getBody();
		contentJson = JSONObject.parseObject(responseBody);
		contentMap=(Map<String,Object>) contentJson;*/
		String FLV=contentMap.get("FLV").toString();
		Map<String, Object> resultMap=new HashMap<>();
		resultMap.put("FLV",FLV);
		return resultMap;
	}

	@Override
	public int queryCaptureOnline(String xqCode) throws OssRenderException {
		int tjOnline = captureDao.queryCaptureOnline(xqCode);
		return tjOnline;
	}

	@Override
	public int queryCaptureNotOnline(String xqCode) throws OssRenderException {
		int tjNotOnline = captureDao.queryCaptureNotOnline(xqCode);
		return tjNotOnline;
	}


}
