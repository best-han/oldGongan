package com.windaka.suizhi.webapi.controller;

import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.webapi.service.CarInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Description 车辆信息
 * @Author wcl
 * @Date 2019/8/20 0020 下午 2:10
 */
@Slf4j
@RestController
public class CarInfoController extends BaseController {
	@Autowired
	private CarInfoService carInfoService;

	/**
	 * 添加车辆信息
	 * @param params
	 */
	@PostMapping("/car")
	public Map<String,Object> saveCarInfo(@RequestBody Map<String, Object> params) {
		try{
			carInfoService.saveCarInfo((String) params.get("xqCode"),(List<Map<String, Object>>) params.get("dataList"));
			return render();
		}catch (Exception e){
			log.info("[CarInfoController.saveCarInfo,参数：{},异常信息：{}]",params,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 修改车辆信息
	 * @param params
	 */
	@PutMapping("/car")
	public Map<String,Object> updateCarInfo(@RequestBody Map<String, Object> params) {
		try{
			carInfoService.updateCarInfo(params);
			return render();
		}catch (Exception e){
			log.info("[CarInfoController.updateCarInfo,参数：{},异常信息：{}]",params,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 删除车辆信息
	 * @param params
	 */
	@DeleteMapping("/car")
	public Map<String,Object> deleteCarInfo(@RequestBody Map<String, Object> params) {
		try{
			carInfoService.deleteCarInfo(params);
			return render();
		}catch (Exception e){
			log.info("[CarInfoController.deleteCarInfo,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}
}
