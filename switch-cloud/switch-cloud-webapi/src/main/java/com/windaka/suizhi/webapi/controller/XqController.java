package com.windaka.suizhi.webapi.controller;


import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.webapi.model.WyInfo;
import com.windaka.suizhi.webapi.model.Xq;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.webapi.service.AppUserService;
import com.windaka.suizhi.webapi.service.XqService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class XqController extends BaseController {

	@Autowired
	private XqService xqService;
	@Autowired
	private AppUserService appUserService;


	/**
	 * 根据Code查询小区
	 * @param xqCode
	 */
	@GetMapping("/xq/{xqCode}")
	public Map<String,Object> queryByCode(@PathVariable String xqCode) {
		try{
			Map map = xqService.queryByCode(xqCode);
			return render(map);
		}catch (Exception e){
			log.info("[XqController.queryByCode,参数：{},异常信息{}]",xqCode,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 查询小区列表
	 * @param params
	 */
	@GetMapping("/xq/list")
	public Map<String,Object> queryXqList(@RequestParam Map<String, Object> params) {
		try{
			if (params.get("xqCode") == "" || params.get("xqCode") == null) {
				//验证当前用户查询权限
				LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
				params.put("xqCode", appUserService.checkAuth(loginAppUser.getUserId()));
			}
			Page<Map<String,Object>> page = xqService.queryXqList(params);
			Map<String,Object> map = new HashMap<>();
			map.put("list",page);
			return render(page);
		}catch (Exception e){
			log.info("[XqController.queryList,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 查询统计－小区信息
	 * @param params
	 *
	 *  @author pxl
	 *  @create: 2019-05-29 9:13
	 */
	@GetMapping("/tj/xqInfo")
	public Map<String,Object> queryCountInfo(@RequestParam Map<String, Object> params) {
		try{
			if (params.get("xqCode") == "") {
                //验证当前用户查询权限
                LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
                params.put("xqCode", appUserService.checkAuth(loginAppUser.getUserId()));
            }
			Map map = xqService.queryCountInfo(params);
			return render(map);
		}catch (Exception e){
			log.info("[XqController.queryCountInfo,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}

    /**
     * 小区信息概况  hjt
     */
 /*   @GetMapping("/tj/xqInfo/summary")
    public Map<String,Object> queryXqInfoSummary(@RequestParam Map<String, Object> params) {
        try{
            Map<String,Object> map= xqService.queryXqInfoSummary(params);
            return render(map);
        }catch (Exception e){
            log.info("[XqController.queryXqInfoSummary,参数：{},异常信息{}]",params,e.getMessage());
            return failRender(e);
        }
    }*/


}
