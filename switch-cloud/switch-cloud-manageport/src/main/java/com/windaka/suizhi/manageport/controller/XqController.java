package com.windaka.suizhi.manageport.controller;


import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.manageport.model.WyInfo;
import com.windaka.suizhi.manageport.model.Xq;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.manageport.service.AppUserService;
import com.windaka.suizhi.manageport.service.CarGroupService;
import com.windaka.suizhi.manageport.service.XqService;
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
	@Autowired
	private CarGroupService carGroupService;

	/**
	 * 添加小区
	 * @param params
	 */
	@PostMapping("/xq")
	public Map<String,Object> saveXq(@RequestBody Map<String, Object> params) {
		try{
			/*Xq xq = new Xq();
			xq.setCode((String) params.get("code"));
			xq.setName((String) params.get("name"));
			xq.setAddr((String) params.get("addr"));
			xq.setMemo((String) params.get("memo"));
			xq.setZdmj( params.get("zdmj"));
			xq.setJzmj(params.get("jzmj"));
			xq.setLdmj( params.get("ldmj"));
			xq.setDlmj( params.get("dlmj"));
			xq.setLysl( params.get("lysl"));
			xq.setFzr((String) params.get("fzr"));
			xq.setGsmc((String) params.get("gsmc"));
			xq.setFrdb((String) params.get("frdb"));
			xq.setLxr((String) params.get("lxr"));
			xq.setLxdh((String) params.get("lxdh"));
			xq.setZnj(params.get("znj"));
			xq.setSsgs((String) params.get("ssgs"));
			xq.setCqts( params.get("cqts"));
			xq.setLptype((String) params.get("lptype"));
			xq.setSfatsf(params.get("sfatsf"));
			xq.setXqlxdh((String) params.get("xqLxdh"));
			xq.setSsoRegionalcode((String) params.get("ssoRegionalcode"));
			xq.setSsoCode((String) params.get("ssoCode"));
			xq.setStatus((String) params.get("status"));
			xq.setCreate_by((String) params.get("createBy"));
			xq.setCreate_date(params.get("createDate"));
			xq.setUpdate_by((String) params.get("updateBy"));
			xq.setUpdate_date(params.get("updateDate"));
			xq.setRemarks((String) params.get("remarks"));
			xq.setDys((String) params.get("dys"));
			xq.setHs((String) params.get("hs"));
			xq.setParking((String)params.get("parking"));*/
			xqService.saveXq(params);
			//管理端的车辆库，有一个默认的白名单类型，前台页面不维护，系统自带数据，无法通过管理端维护到云端；通过刚建项目时新增，默认白名单type是 1
			Map<String,Object> mapParams=new HashMap<>();
			Map<String,Object> map=new HashMap<>();
			map.put("type",1);
			map.put("typeName","白名单");
			map.put("name","白名单");
			map.put("status","0");
			List<Map<String,Object>> list=new ArrayList<>();
			list.add(map);
			mapParams.put("xqCode",params.get("code"));
			mapParams.put("list",list);
			carGroupService.saveCarGroups(mapParams);

			return render();
		}catch (Exception e){
			log.info("[XqController.saveXq,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 修改小区
	 * @param xq
	 */
	@PutMapping("/xq")
	public Map<String,Object> updateXq(@RequestBody Xq xq) {
		try{
			xqService.updateXq(xq);
			return render();
		}catch (Exception e){
			log.info("[XqController.updateXq,参数：{},异常信息{}]",xq,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 删除小区
	 * @param xqCode
	 */
	@DeleteMapping("/xq/{xqCode}")
	public Map<String,Object> deleteXq(@PathVariable String xqCode) {
		try{
			xqService.deleteXq(xqCode);
			return render();
		}catch (Exception e){
			log.info("[XqController.deleteXq,参数：{},异常信息{}]",xqCode,e.getMessage());
			return failRender(e);
		}
	}

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
			//验证当前用户查询权限
			LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
			params.put("xqCode",appUserService.checkAuth(loginAppUser.getUserId()));
			Page<Map<String,Object>> page = xqService.queryXqList(params);
			return render(page);
		}catch (Exception e){
			log.info("[XqController.queryList,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}
	/**
	 * 小区查询（列表查询）安保端app使用
	 * @param params
	 */
	@GetMapping("/xq/list/app")
	public Map<String,Object> queryXqListForApp(@RequestParam Map<String, Object> params) {
		try{
			//验证当前用户查询权限
			LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
			params.put("xqCode",appUserService.checkAuth(loginAppUser.getUserId()));
			List<Map<String,Object>> list = xqService.queryXqListForApp(params);
			return render(list);
		}catch (Exception e){
			log.info("[XqController.queryXqListForApp,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 内部调用  hjt
	 * @param
	 * @return
	 */
	@GetMapping(value = "/xqList-anon/internal", params = {"areaId","xqCode"})
	public Map<String,Object> queryListXqInfo(String areaId, String xqCode) {
		try{
			Map<String,Object> map = new HashMap<>();
			List<Map<String,Object>> list=new ArrayList<>();
			if(StringUtils.isNotBlank(xqCode)){
				map=xqService.queryByCode(xqCode);
			}else{
				list=xqService.queryListXqByAreaId(areaId);
			}
			return render(list);
		}catch (Exception e){
			log.info("[XqController.queryListXqInfo,参数：{},异常信息{}]",areaId,xqCode,e.getMessage());
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
	 * 物业以及小区列表展示（前端查询）hjt
	 * @param params
	 * @return
	 */
	@GetMapping("/wyAndXq/list")
	public Map<String,Object> queryWyAndXqList(@RequestParam Map<String, Object> params) {
		try{
			List<WyInfo> list= xqService.queryWyAndXqList();
			return render(list);
		}catch (Exception e){
			log.info("[XqController.queryWyAndXqList,参数：{},异常信息{}]",params,e.getMessage());
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
