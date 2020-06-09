package com.windaka.suizhi.webapi.controller;

import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.webapi.model.FaceTypePerson;
import com.windaka.suizhi.webapi.service.FaceTypePersonService;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class FaceTypePersonController extends BaseController {
	@Autowired
	private FaceTypePersonService faceTypePersonService;

	/**
	* @Description: 批量维护人脸-人脸类别
	* @Param: [params]
	* @return: java.util.Map<java.lang.String,java.lang.Object>
	* @Author: Ms.wcl
	* @Date: 2019/5/8 0008
	 * 人员布控-新增 ygy
	*/
	@PostMapping("/face/faceType")
	public Map<String,Object> saveFaceTypePerson(@RequestBody Map<String,Object> params) {
		try{
			LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
			params.put("userId",loginAppUser.getUserId());
			faceTypePersonService.saveFaceTypePerson(params);
			return render();
		}catch (Exception e){
			log.info("[FaceTypePersonController.saveFaceTypePerson,参数：{},异常信息{}]","",e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 人脸-人脸类别删除(同一小区可多个删除) hjt
	 * 人员布控-删除 ygy
	 * @param id
	 * @return
	 */
	@DeleteMapping("/face/faceType/{id}")
	public Map<String,Object> delFaceTypePerson(@PathVariable("id") String id) {
		try{
			Map<String,Object> params=new HashMap<>();
			LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
			params.put("userId",loginAppUser.getUserId());
			params.put("id",id);
			faceTypePersonService.delFaceTypePerson(params);
			return render();
		}catch (Exception e){
			log.info("[FaceTypePersonController.delFaceTypePerson,参数：{},异常信息{}]","",e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 人员布控-列表查询 ygy
	 * @param params
	 * @return
	 */
	@GetMapping("/face/faceType/list")
	public Map<String,Object> faceFaceTypeList(@RequestParam Map<String,Object> params){
		try {
			Map<String,Object> map=faceTypePersonService.faceFaceTypeList(params);
			return render(map);
		} catch (Exception e) {
			log.info("[FaceTypePersonController.faceFaceTypeList,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}

}
