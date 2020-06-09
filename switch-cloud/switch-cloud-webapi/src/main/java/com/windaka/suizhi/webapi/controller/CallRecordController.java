package com.windaka.suizhi.webapi.controller;

import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.webapi.service.AppUserService;
import com.windaka.suizhi.webapi.service.CallRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Description 通话记录
 * @Author wcl
 * @Date 2019/8/21 0021 上午 10:07
 */
@Slf4j
@RestController
public class CallRecordController extends BaseController {
	@Autowired
	private CallRecordService callRecordService;
	@Autowired
	private AppUserService appUserService;

	/**
	 * 批量添加开门记录
	 * @param params
	 */
	@PostMapping("/callRecord")
	public Map<String,Object> saveCallRecord(@RequestBody Map<String, Object> params) {
		try{
			callRecordService.saveCallRecord((String) params.get("xqCode"),(List<Map<String, Object>>) params.get("dataList"));
			return render();
		}catch (Exception e){
			log.info("[CallRecordController.saveCallRecord,参数：{},异常信息：{}]",params,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 通话记录列表查询  hjt ygy
	 * @param params
	 * @return
	 */
	@GetMapping("/person/callRecord/list")
	public Map<String,Object> queryCallRecord(@RequestParam Map<String, Object> params) {
		try{
			if (params.get("xqCode") == "") {
				//验证当前用户查询权限
				LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
				params.put("xqCode", appUserService.checkAuth(loginAppUser.getUserId()));
			}
			Map<String,Object> map = callRecordService.queryCallRecordList(params);
			return render(map);
		}catch (Exception e){
			log.info("[CallRecordController.queryCallRecord,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}
}
