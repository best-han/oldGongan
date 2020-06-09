package com.windaka.suizhi.manageport.controller;

import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.manageport.service.AnimalAttributeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 宠物属性 hjt
 */
@Slf4j
@RestController
public class AnimalAttributeController extends BaseController {
	@Autowired
	private AnimalAttributeService animalAttributeService;

	/**
	 * 批量添加宠物属性
	 * @param params
	 */
	@PostMapping("/animalAttribute")
	public Map<String,Object> saveAnimalAttribute(@RequestBody Map<String, Object> params) {
		try{
			animalAttributeService.saveAnimalAttribute(params);
			return render();
		}catch (Exception e){
			log.info("[AnimalAttributeController.saveAnimalAttribute,参数：{},异常信息：{}]",params,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 修改宠物属性
	 * @param params
	 */
	@PutMapping("/animalAttribute")
	public Map<String,Object> updateAnimalAttribute(@RequestBody Map<String, Object> params) {
		try{
			animalAttributeService.updateAnimalAttribute(params);
			return render();
		}catch (Exception e){
			log.info("[AnimalAttributeController.updateAnimalAttribute,参数：{},异常信息：{}]",params,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 删除宠物属性信息
	 * @param params
	 */
	@DeleteMapping("/animalAttribute")
	public Map<String,Object> deleteAnimalAttribute(@RequestBody Map<String, Object> params) {
		try{
			animalAttributeService.deleteAnimalAttribute(params);
			return render();
		}catch (Exception e){
			log.info("[AnimalAttributeController.deleteAnimalAttribute,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 宠物属性列表查询  hjt
	 * @param params
	 * @return
	 */
/*	@GetMapping("/tj/callRecord/list")
	public Map<String,Object> queryAnimalAttribute(@RequestParam Map<String, Object> params) {
		try{
			if (params.get("xqCode") == "") {
				//验证当前用户查询权限
				LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
				params.put("xqCode", appUserService.checkAuth(loginAppUser.getUserId()));
			}
			Map<String,Object> map = callRecordService.queryAnimalAttributeList(params);
			return render(map);
		}catch (Exception e){
			log.info("[CallRecordController.queryCallRecord,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}*/
}
