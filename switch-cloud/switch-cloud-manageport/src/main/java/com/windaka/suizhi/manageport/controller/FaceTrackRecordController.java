package com.windaka.suizhi.manageport.controller;

import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.manageport.service.AppUserService;
import com.windaka.suizhi.manageport.service.FaceTrackRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author wcl
 * @Date 2019/5/9 0009 上午 10:18
 */
@Slf4j
@RestController
public class FaceTrackRecordController extends BaseController {

	@Autowired
	private FaceTrackRecordService faceTrackRecordService;
	@Autowired
	private AppUserService appUserService;


	/**
	* @Description: 人脸轨迹上传
	* @Param: [params]
	* @return: java.util.Map<java.lang.String,java.lang.Object>
	* @Author: Ms.wcl
	* @Date: 2019/5/9 0009
	*/
	@PostMapping("/face/track")
	public Map<String,Object> saveFaceTrackRecord(@RequestBody Map<String,Object> params) {
		try{
			faceTrackRecordService.saveFaceTrackRecord((String) params.get("xqCode"), (List<Map<String, Object>>) params.get("list"));
			return render();
		}catch (Exception e){
			log.info("[FaceTrackRecordController.saveFaceTrackRecord,参数：{},异常信息{}]","",e.getMessage());
			return failRender(e);
		}
	}

	/** 
	* @Description: 人脸轨迹列表查询
	* @Param: [params] 
	* @return: java.util.Map<java.lang.String,java.lang.Object> 
	* @Author: Ms.wcl
	* @Date: 2019/5/9 0009
	*/ 
	@GetMapping("/face/track/list")
	public Map<String,Object> queryFaceTrackRecordList(@RequestParam Map<String,Object> params) {
		try{
			//验证当前用户查询权限
			LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
			params.put("xqCode",appUserService.checkAuth(loginAppUser.getUserId()));

			Map<String,Object> map = faceTrackRecordService.queryFaceTrackRecordList(params);
			return render(map);
		}catch (Exception e){
			log.info("[FaceTrackRecordController.queryFaceTrackRecordList,参数：{},异常信息{}]","",e.getMessage());
			return failRender(e);
		}
	}

/*	@PostMapping("/face/track/listByImg")
	public Map<String,Object> queryImageByImage(@RequestBody Map<String,Object> params){
		try{
			Map<String,Object> map = faceTrackRecordService.queryImgByImg(params);
			*//*Map<String,Object> map = new HashMap<>();
			map.put("list",page);*//*
			return render(map);
		}catch (Exception e){
			log.info("[FaceTrackRecordService.queryImgByImg,参数：{},异常信息{}]","",e.getMessage());
			return failRender(e);
		}

	}*/



}
