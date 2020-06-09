package com.windaka.suizhi.manageport.controller;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.manageport.service.AppUserService;
import com.windaka.suizhi.manageport.service.CaptureService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 摄像机设备
 * @Author wcl
 * @Date 2019/7/16 0016 下午 2:23
 */
@Slf4j
@RestController
public class CaptureController extends BaseController {

	@Autowired
	private CaptureService captureService;

	@Autowired
	protected AppUserService appUserService;

	/**
	 * 添加摄像机设备
	 * @param params
	 */
	@PostMapping("/capture/device")
	public Map<String,Object> saveCaptureDevice(@RequestBody Map<String, Object> params) {
		try{
			captureService.saveCaptureDevice(params);
			return render();
		}catch (Exception e){
			log.info("[CaptureController.saveCaptureDevice,参数：{},异常信息：{}]",params,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 修改摄像机设备
	 * @param params
	 */
	@PutMapping("/capture/device")
	public Map<String,Object> updateCaptureDevice(@RequestBody Map<String, Object> params) {
		try{
			captureService.updateCaptureDevice(params);
			return render();
		}catch (Exception e){
			log.info("[CaptureController.updateCaptureDevice,参数：{},异常信息：{}]",params,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 删除摄像机
	 * @param params
	 */
	@PostMapping("/capture/device/del")
	public Map<String,Object> deleteCaptureDevice(@RequestBody Map<String, Object> params) {
		try{
			String xqCode = (String) params.get("xqCode");
			ArrayList manageIds = (ArrayList) params.get("manageIds");
			captureService.deleteCaptureDevice(xqCode,manageIds);
			return render();
		}catch (Exception e){
			log.info("[CaptureController.deleteCaptureDevice,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 根据areaId查询小区摄像机列表
	 * @param areaId
	 */
	@GetMapping("/capture")
	public Map<String,Object> queryCaptureListByAreaId(@RequestParam String areaId) {
		try{
			if (StringUtils.isBlank(areaId)) {
				throw new OssRenderException(ReturnConstants.CODE_FAILED,"区域ID不能为空");
			}
			//验证当前用户查询权限
			LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
			String xqCodes = appUserService.checkAuth(loginAppUser.getUserId());
			String[] xq = xqCodes.split(",");
			List xqList = new ArrayList<>();
			for (int i = 0; i < xq.length; i++) {
				xqList.add(xq[i]);
			}
//			List<Map<String,Object>> xqList=captureService.queryXqListByAreaId(areaId);
			List<Map<String,Object>> list = new ArrayList<>();
			for(int i=0; i<xqList.size(); i++){
				String xqCode = (String) xqList.get(i);
				List<Map<String,Object>> captureInfoList=captureService.queryCaptureListByxqCode(xqCode);
				String xqName = captureService.queryXqByXqCode(xqCode);
				Map<String,Object> map = new HashMap<>();
				map.put("xqCode",xqCode);
				map.put("xqName",xqName);
				map.put("captures",captureInfoList);
				map.put("state","false");
				list.add(map);
			}

			int tjOnline = captureService.queryCaptureOnline(xqCodes);
			int tjNotOnline = captureService.queryCaptureNotOnline(xqCodes);
			Map<String,Object> result = new HashMap<String, Object>();
			result.put("list",list);
			result.put("tjOnline",tjOnline);
			result.put("tjNotOnline",tjNotOnline);
			return render(result);
		}catch (Exception e){
			log.info("[CaptureController.queryCaptureListByAreaId,参数：{},异常信息{}]",areaId,e.getMessage());
			return failRender(e);
		}
	}
}
