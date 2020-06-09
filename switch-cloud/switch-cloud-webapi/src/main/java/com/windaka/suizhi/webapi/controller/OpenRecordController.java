package com.windaka.suizhi.webapi.controller;

import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.webapi.service.AppUserService;
import com.windaka.suizhi.webapi.service.OpenRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Description 开门记录
 * @Author wcl
 * @Date 2019/8/21 0021 上午 9:03
 */
@Slf4j
@RestController
public class OpenRecordController extends BaseController {

	@Autowired
	private OpenRecordService openRecordService;
	@Autowired
	private AppUserService appUserService;


	/**
	 * 开门方式统计  hjt
	 * @param params
	 * @return
	 */
	@GetMapping("/tj/personInfo/openType")
	public Map<String,Object> queryOpenTypesOfPerson(@RequestParam Map<String, Object> params) {
		try{
//			if (params.get("xqCode") == "") {
//				//验证当前用户查询权限
//				LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
//				params.put("xqCode", appUserService.checkAuth(loginAppUser.getUserId()));
//			}
			List<Map<String,Object>> list = openRecordService.queryOpenTypesOfPerson(params);
			return render(list);
		}catch (Exception e){
			log.info("[OpenRecordController.queryOpenTypesOfPerson,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}
	/**
	 * 开门记录列表查询  hjt  ygy
	 * @param params
	 * @return
	 */
	@GetMapping("/person/access/list")
	public Map<String,Object> queryOpenRecord(@RequestParam Map<String, Object> params) {
		try{
			if (params.get("xqCode") == "") {
				//验证当前用户查询权限
				LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
				params.put("xqCode", appUserService.checkAuth(loginAppUser.getUserId()));
			}
			Map<String,Object> map = openRecordService.queryOpenRecordList(params);
			return render(map);
		}catch (Exception e){
			log.info("[OpenRecordController.queryOpenRecord,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}

}
