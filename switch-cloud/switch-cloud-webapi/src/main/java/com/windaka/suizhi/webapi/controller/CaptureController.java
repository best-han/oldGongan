package com.windaka.suizhi.webapi.controller;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.webapi.service.AppUserService;
import com.windaka.suizhi.webapi.service.CaptureService;
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
	@PostMapping("/capture")
	public Map<String,Object> saveCapture(@RequestBody Map<String, Object> params) {
		try{
			captureService.saveCapture(params);
			return render();
		}catch (Exception e){
			log.info("[CaptureController.saveCapture,参数：{},异常信息：{}]",params,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 修改摄像机设备
	 * @param params
	 */
	@PutMapping("/capture")
	public Map<String,Object> updateCapture(@RequestBody Map<String, Object> params) {
		try{
			captureService.updateCapture(params);
			return render();
		}catch (Exception e){
			log.info("[CaptureController.updateCapture,参数：{},异常信息：{}]",params,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 删除摄像机
	 * @param params
	 */
	@PostMapping("/capture/del")
	public Map<String,Object> deleteCapture(@RequestBody Map<String, Object> params) {
		try{
			String xqCode = (String) params.get("xqCode");
			ArrayList captureIds = (ArrayList) params.get("captureIds");
			captureService.deleteCapture(xqCode,captureIds);
			return render();
		}catch (Exception e){
			log.info("[CaptureController.deleteCapture,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 根据areaId查询小区摄像机列表
	 * @param areaId
	 */
	/*@GetMapping("/capture")
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
	}*/

	/**
	 * 街道端-地图-设备（摄像机）列表
	 * @param
	 * @return
	 */
	@GetMapping("/capture")
	public Map<String,Object> queryCaptureListByXqCode(@RequestParam Map<String, Object> params) {
		try{
			if (StringUtils.isBlank((String)params.get("areaId"))) {
				throw new OssRenderException(ReturnConstants.CODE_FAILED,"区域ID不能为空");
			}
			//List<Map<String, Object>> list=new ArrayList<>();
			if (params.get("xqCode")== null || params.get("xqCode") == "") {
				//验证当前用户查询权限
				LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
				params.put("xqCode", appUserService.checkAuth(loginAppUser.getUserId()));
			}
			List<Map<String,Object>> captureInfoList=captureService.queryCaptureDeviceList(params);
			return render(captureInfoList);
		}catch (Exception e){
			log.info("[CaptureController.queryCaptureListByXqCode,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 设备（摄像机）播放地址  hjt
	 * @param
	 * @return
	 */
	@GetMapping("/capture/play")
	public Map<String,Object> queryCaptureFLVByGbCode(@RequestParam Map<String, Object> params) {
		try{
			Map<String,Object> map=captureService.queryCaptureFLVByGbCode(params);
			return render(map);
		}catch (Exception e){
			log.info("[CaptureController.queryCaptureFLVByGbCode,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}
}
