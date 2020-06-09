package com.windaka.suizhi.webapi.controller;


import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.webapi.service.AppUserService;
import com.windaka.suizhi.webapi.service.PersonService;
import com.windaka.suizhi.webapi.service.SubdistrictService;
import com.windaka.suizhi.webapi.service.XqService;
import com.windaka.suizhi.webapi.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
public class SubdistrictController extends BaseController {

	@Autowired
	private SubdistrictService subdistrictService;
	@Autowired
	private XqService xqService;
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private PersonService personService;


	/**
	 * 根据当前登录人查街道以及其下小区 hjt
	 * @param
	 */
	@GetMapping("/subdistrict/xq")
	public Map<String,Object> querySubdistrictAndXq() {
		try{
			List<Map<String,Object>> list = subdistrictService.querySubdistrictAndXq();
			return render(list);
		}catch (Exception e){
			log.info("[SubdistrictController.querySubdistrictAndXq,参数：{},异常信息{}]","",e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 街道-新增 hjt
	 * @param
	 */
	@PostMapping("/subdistrict")
	public Map<String,Object> saveSubdistrict(@RequestBody Map<String, Object> params) {
		try{
			subdistrictService.saveSubdistrict(params);
			return render();
		}catch (Exception e){
			log.info("[SubdistrictController.saveSubdistrict,参数：{},异常信息{}]","",e.getMessage());
			return failRender(e);
		}
	}
	/**
	 * 街道-删除 hjt
	 * @param
	 */
	@DeleteMapping("/subdistrict/{subdistrictId}")
	public Map<String,Object> deleteSubdistrict(@PathVariable String subdistrictId) {
		try{
			subdistrictService.deleteSubdistrict(subdistrictId);
			return render();
		}catch (Exception e){
			log.info("[SubdistrictController.deleteSubdistrict,参数：{},异常信息{}]","",e.getMessage());
			return failRender(e);
		}
	}
	/**
	 * 街道小区关系绑定 hjt
	 * @param
	 */
	@PostMapping("/subdistrict/xq")
	public Map<String,Object> saveXqSubdistrict(@RequestBody Map<String, Object> params) {
		try{
			subdistrictService.saveXqSubdistrict(params);
			return render();
		}catch (Exception e){
			log.info("[SubdistrictController.saveXqSubdistrict,参数：{},异常信息{}]","",e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 街道-小区信息统计（地图&楼栋数量户数等：前端查询） hjt
	 * @param params
	 * @return
	 */
	@GetMapping("/subdistrict/xqInfo/statistics")
	public Map<String,Object> queryCountInfo(@RequestParam Map<String, Object> params) {
		try{
			if (params.get("xqCode") == "" || params.get("xqCode")==null) {
				//验证当前用户查询权限
				LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
				params.put("xqCode", appUserService.checkAuth(loginAppUser.getUserId()));
			}
			Map map = xqService.queryCountInfo(params);
			return render(map);
		}catch (Exception e){
			log.info("[SubdistrictController.queryCountInfo,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 街道-左上角统计数据 hjt
	 * @param params
	 * @return
	 */
	@GetMapping("/subdistrict/statistics")
	public Map<String,Object> queryStatistics(@RequestParam Map<String, Object> params) {
		try{
			if (params.get("xqCode") == "" || params.get("xqCode")==null) {
				//验证当前用户查询权限
				LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
				params.put("xqCode", appUserService.checkAuth(loginAppUser.getUserId()));
			}
			Map map = subdistrictService.queryStatistics(params);
			return render(map);
//			Map resultMap=new HashMap();
//			resultMap.put("personTotalNum",1456);
//			resultMap.put("carTotalNum",1134);
//			resultMap.put("hsSum",2400);
//			resultMap.put("deviceSum",24);
//			resultMap.put("specialSum",52);
//			return render(resultMap);
		}catch (Exception e){
			log.info("[SubdistrictController.queryStatistics,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}
	@GetMapping("/subdistrict/statistics/today3")
	public Map<String,Object> queryStatisticsToday3(@RequestParam Map<String, Object> params) throws OssRenderException {
		if (params.get("xqCode") == "" || params.get("xqCode")==null) {
			//验证当前用户查询权限
			LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
			params.put("xqCode", appUserService.checkAuth(loginAppUser.getUserId()));
		}
		Map map = subdistrictService.queryStatisticsToday(params);
		return render(map);
	}
	/**
	 * 街道-右上角统计数据 hjt
	 * @param params
	 * @return
	 */
	@GetMapping("/subdistrict/statistics/today")
	public Map<String,Object> queryStatisticsToday(@RequestParam Map<String, Object> params) {
		try{
			if (params.get("xqCode") == "" || params.get("xqCode")==null) {
				//验证当前用户查询权限
				LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
				params.put("xqCode", appUserService.checkAuth(loginAppUser.getUserId()));
			}
			Map resultMap = subdistrictService.queryStatisticsToday(params);
			return render(resultMap);
		}catch (Exception e){
			log.info("[SubdistrictController.queryStatisticsToday,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 测试 hjt
	 * @param
	 * @return
	 */
	@PostMapping("/upzip")
	public Map<String,Object> upzip(@RequestParam("file")MultipartFile multipartFile) {
		try{

			//ZipUtil.unZip(multipartFile,"F:\\switch_cloud_file");
			return render();
		}catch (Exception e){
			log.info("[SubdistrictController.upzip,参数：{},异常信息{}]",multipartFile,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 首页-社区基础要素 dee
	 * @param params
	 * @return
	 */
	@GetMapping("/subdistrict/baseInfo")
	public Map<String,Object> getSubdistrictBaseInfo(@RequestParam Map<String, Object> params){
		try{
			if(params.get("areaId")==null||params.get("areaId").toString().trim().equals(""))
				params.put("areaId",0);
			Map resultMap = subdistrictService.getSubdistrictBaseInfo(params);
			return render(resultMap);
		}catch (Exception e){
			log.info(e.toString());
			return failRender(e);
		}
	}

}
