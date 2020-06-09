package com.windaka.suizhi.webapi.controller;

import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.webapi.service.CarAccessRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Description 车辆出入场记录
 * @Author wcl
 * @Date 2019/8/22 0022 下午 4:25
 */
@Slf4j
@RestController
public class CarAccessRecordController extends BaseController {

	@Autowired
	private CarAccessRecordService carAccessRecordService;

	/**
	 * 批量添加车辆出入场记录
	 * @param params
	 */
	@PostMapping("/tj/carAccess")
	public Map<String,Object> saveCarAccessRecord(@RequestBody Map<String, Object> params) {
		try{
			carAccessRecordService.saveCarAccessRecord((String) params.get("xqCode"),(List<Map<String, Object>>) params.get("dataList"));
			return render();
		}catch (Exception e){
			log.info("[CarAccessRecordController.saveCarAccessRecord,参数：{},异常信息：{}]",params,e.getMessage());
			return failRender(e);
		}
	}
}
