package com.windaka.suizhi.webapi.controller;


import cn.hutool.core.util.ObjectUtil;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.webapi.service.AppUserService;
import com.windaka.suizhi.webapi.service.SpecialBaseInfoService;
import com.windaka.suizhi.webapi.service.SubdistrictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class SpecialBaseInfoController extends BaseController {

	@Autowired
	private AppUserService appUserService;
	@Autowired
	private SpecialBaseInfoService specialBaseInfoService;
	/**
	 * 街道-特别关注基础信息
	 * @param
	 */
	@GetMapping("/special/baseInfo")
	public Map<String,Object> querySpecialBaseInfo(@RequestParam Map<String,Object> params) {
		try{
			LoginAppUser appUser= AppUserUtil.getLoginAppUser();
			if(ObjectUtil.isNull(params.get("xqCode"))){
				params.put("xqCode",appUserService.checkAuth(appUser.getUserId()));
			}
			Map<String,Object> map = specialBaseInfoService.querySpecialNum(params);
			return render(map);
		}catch (Exception e){
			log.info("[SpecialBaseInfoController.querySpecialBaseInfo,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}

}
