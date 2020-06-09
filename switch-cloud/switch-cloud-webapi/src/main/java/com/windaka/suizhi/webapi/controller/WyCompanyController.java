package com.windaka.suizhi.webapi.controller;

import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.webapi.model.WyCompany;
import com.windaka.suizhi.webapi.model.WyInfo;
import com.windaka.suizhi.webapi.service.WyCompanyService;
import com.windaka.suizhi.webapi.service.WyInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class WyCompanyController extends BaseController {
	@Autowired
	private WyCompanyService wyCompanyService;
	@Autowired
	private WyInfoService wyInfoService;

	/**
	 * 添加物业公司
	 * @param wyCompany
	 */
	@PostMapping("/wy")
	public Map<String,Object> saveWy(@RequestBody WyCompany wyCompany) {
		try{
			wyCompanyService.saveWy(wyCompany);
			WyInfo wyInfo = new WyInfo();
			wyInfo.setWyCode(wyCompany.getCompanyCode());
			wyInfo.setWyName(wyCompany.getCompanyName());
			wyInfo.setParentCode(wyCompany.getParentCode());
			wyInfo.setCreateBy(wyCompany.getCreateBy());
			wyInfo.setCreateDate(wyCompany.getCreateDate());
			wyInfo.setUpdateBy(wyCompany.getUpdateBy());
			wyInfo.setUpdateDate(wyCompany.getUpdateDate());
			wyInfoService.saveWyInfo(wyInfo);
			return render();
		}catch (Exception e){
			log.info("[WyCompanyController.saveWy,参数：{},异常信息{}]",wyCompany,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 修改物业公司
	 * @param wyCompany
	 */
	@PutMapping("/wy")
	public Map<String,Object> updateWy(@RequestBody WyCompany wyCompany) {
		try{
			wyCompanyService.updateWy(wyCompany);
			WyInfo wyInfo = new WyInfo();
			wyInfo.setWyCode(wyCompany.getCompanyCode());
			wyInfo.setWyName(wyCompany.getCompanyName());
			wyInfo.setParentCode(wyCompany.getParentCode());
			wyInfo.setCreateBy(wyCompany.getCreateBy());
			wyInfo.setCreateDate(wyCompany.getCreateDate());
			wyInfo.setUpdateBy(wyCompany.getUpdateBy());
			wyInfo.setUpdateDate(wyCompany.getUpdateDate());
			wyInfoService.updateWyInfo(wyInfo);
			return render();
		}catch (Exception e){
			log.info("[WyCompanyController.updateWy,参数：{},异常信息{}]",wyCompany,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 删除物业公司
	 * @param companyCode
	 */
	@DeleteMapping("/wy/{companyCode}")
	public Map<String,Object> deleteWy(@PathVariable String companyCode) {
		try{
			wyCompanyService.deleteWy(companyCode);
			wyInfoService.deleteWyInfo(companyCode);
			return render();
		}catch (Exception e){
			log.info("[WyCompanyController.deleteWy,参数：{},异常信息{}]",companyCode,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 根据Code查询物业公司
	 * @param companyCode
	 */
	@GetMapping("/wy/{companyCode}")
	public Map<String,Object> queryByCode(@PathVariable String companyCode) {
		try{
			Map map = wyCompanyService.queryByCode(companyCode);
			return render(map);
		}catch (Exception e){
			log.info("[WyCompanyController.queryByCode,参数：{},异常信息{}]",companyCode,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 查询物业公司列表
	 * @param params
	 */
	@GetMapping("/wy/list")
	public Map<String,Object> queryWyList(@RequestParam Map<String, Object> params) {
		try{
			Page page = wyCompanyService.queryWyList(params);
			Map<String,Object> map = new HashMap<>();
			map.put("list",page);
			return render(page);
		}catch (Exception e){
			log.info("[WyCompanyController.queryList,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}
	
}
