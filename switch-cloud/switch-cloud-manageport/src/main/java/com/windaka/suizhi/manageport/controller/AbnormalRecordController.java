package com.windaka.suizhi.manageport.controller;

import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.manageport.service.AbnormalRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 异常行为记录
 * @Author hjt
 * @Date 2019/11/1
 */
@Slf4j
@RestController
public class AbnormalRecordController extends BaseController {

	@Autowired
	private AbnormalRecordService abnormalRecordService;
	/**
	 * 修改
	 * @param params
	 */
	@PutMapping("/abnormalRecord")
	public Map<String,Object> updateAbnormalRecord(@RequestBody Map<String, Object> params) {
		try{
			/*if(ObjectUtil.isNotNull(params.get("handleStatus")) && !"0".equals(params.get("handleStatus"))){
				List<Map<String,Object>> list=abnormalRecordService.queryAbnormalRecordList(params);
				if(list==null || list.size()==0){
					throw new OssRenderException(ReturnConstants.CODE_FAILED,"无此条记录！");
				}
				if(list.get(0).get("handleStatus").equals("1")){
					throw new OssRenderException(ReturnConstants.CODE_FAILED,"此记录已被其他人处理");
				}*/
				abnormalRecordService.updateAbnormalRecord(params);
			//+}
			return render();
		}catch (Exception e){
			log.info("[AbnormalRecordController.updateAbnormalRecord,参数：{},异常信息：{}]",params,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 列表查询
	 * @param params
	 * @return
	 */
	@GetMapping("/abnormalRecord")
	public Map<String,Object> queryAbnormalRecordPageList(@RequestParam Map<String, Object> params) {
		try {
			Page<Map<String,Object>> page =abnormalRecordService.queryAbnormalRecordPageList(params);
			Map<String,Object> map = new HashMap<>();
			map.put("list",page);
			return render(page);
		}catch (Exception e){
			log.info("[AbnormalRecordController.queryAbnormalRecordPageList,参数：{},异常信息：{}]",params,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 删除摄像机
	 * @param params
	 */
/*	@PostMapping("/capture/del")
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

	*//**
	 * 根据areaId查询小区摄像机列表
	 * @param areaId
	 *//*
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
	}*/
}
