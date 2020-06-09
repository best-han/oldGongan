package com.windaka.suizhi.webapi.controller;

import java.util.Map;
import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.webapi.service.FacePersonCaptureService;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @project: switch-cloud-webapi
 * @Description: 大数据页面
 * @author: kakaxi
 * @date: 2019年12月10日 上午10:00:51
 */
@Slf4j
@RestController
@RequestMapping("/bigData")
public class BigDataController extends BaseController{
	
	@Resource
	FacePersonCaptureService facePersonCaptureService;
	/**
	 * 
	 * @Description: 频繁夜归人员
	 * @param: @param params
	 * @param: @return      
	 * @return: Map<String,Object>
	 */
	@PostMapping("/nightReturnPerson")
	public Map<String,Object> nightReturnPerson(@RequestBody Map<String, Object> params) {
		
		try {
			Map<String,Object> map = facePersonCaptureService.getNightReturnPerson(params);
			return render(map);
		} catch (Exception e) {
			log.info("[CallRecordController.nightReturnPerson,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}
	
	/**
	 * 
	 * @Description: 频繁夜归车辆
	 * @param: @param params
	 * @param: @return      
	 * @return: Map<String,Object>
	 */
	@GetMapping("/nightReturnCar")
	public Map<String,Object> nightReturnCar(@RequestParam Map<String, Object> params) {
		try {
//			Map<String,Object> mapResult = new HashMap<String, Object>();
			Map<String,Object> map = facePersonCaptureService.getNightReturnCar(params);
			/*List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			Map<String,Object> map1=new HashMap<String, Object>();
			map1.put("name", "张伟");
			map1.put("xqName", "凤凰城");
			map1.put("identityStr", "自住");
			map1.put("dateStr", "2019-12-13 23:12:16");
			map1.put("times", "2");
			map1.put("carNum", "鲁CPB117");
			list.add(map1);
			
			Map<String,Object> map2=new HashMap<String, Object>();
			map2.put("name", "原涛");
			map2.put("xqName", "凤凰城");
			map2.put("identityStr", "出租");
			map2.put("dateStr", "2019-12-13 02:08:26");
			map2.put("times", "1");
			map2.put("carNum", "鲁B2L2Y3");
			list.add(map2);
			
			Map<String,Object> map3=new HashMap<String, Object>();
			map3.put("name", "崔建明");
			map3.put("xqName", "凤凰城");
			map3.put("identityStr", "出租");
			map3.put("dateStr", "2019-12-12 23:42:22");
			map3.put("times", "1");
			map3.put("carNum", "鲁B39ZK9");
			list.add(map3);
			
			
			
			
			mapResult.put("list",list);
			mapResult.put("totalRows",3);
			mapResult.put("currentPage", MapUtils.getInteger(params, PageUtil.PAGE));*/
			return render(map);
		} catch (Exception e) {
			log.info("[CallRecordController.nightReturnCar,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}
	
	/**
	 * 
	 * @Description: 用水异常研判
	 * @param: @param params
	 * @param: @return      
	 * @return: Map<String,Object>
	 */
	@GetMapping("/waterRate")
	public Map<String,Object> waterRate(@RequestParam Map<String, Object> params) {
		try {
			Map<String,Object> map = facePersonCaptureService.waterRate(params);
			return render(map);
		} catch (Exception e) {
			log.info("[CallRecordController.waterRate,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}
	
	/**
	 * 
	 * @Description: 群租房研判
	 * @param: @param params
	 * @param: @return      
	 * @return: Map<String,Object>
	 */
	@PostMapping("/groupResearch")
	public Map<String,Object> groupResearch(@RequestBody Map<String, Object> params) {
		try {
//			Map<String,Object> mapResult = new HashMap<String, Object>();
			Map<String,Object> map = facePersonCaptureService.groupResearch(params);
			/*List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			Map<String,Object> map1=new HashMap<String, Object>();
			map1.put("xqName", "凤凰城");
			map1.put("houseInfo", "7号楼1单元1001");
			map1.put("roomName", "1001");
			map1.put("purposeName", "出租");
			map1.put("ownerName", "吴海龙");
			map1.put("personNum", "5");
			map1.put("phone", "13176519398");
			list.add(map1);
			
			Map<String,Object> map2=new HashMap<String, Object>();
			map2.put("xqName", "凤凰城");
			map2.put("houseInfo", "18号楼1单元202");
			map2.put("roomName", "202");
			map2.put("purposeName", "出租");
			map2.put("ownerName", "申晓 ");
			map2.put("personNum", "3");
			map2.put("phone", "13810636805");
			list.add(map2);
			
			Map<String,Object> map3=new HashMap<String, Object>();
			map3.put("xqName", "凤凰城");
			map3.put("houseInfo", "23号楼2单元1403");
			map3.put("roomName", "1403");
			map3.put("purposeName", "出租");
			map3.put("ownerName", "陈娟 ");
			map3.put("personNum", "2");
			map3.put("phone", "13513706388");
			list.add(map3);
			
			Map<String,Object> map4=new HashMap<String, Object>();
			map4.put("xqName", "凤凰城");
			map4.put("houseInfo", "20号楼2单元903");
			map4.put("roomName", "903");
			map4.put("purposeName", "出租");
			map4.put("ownerName", "王蛟");
			map4.put("personNum", "3");
			map4.put("phone", "13267432575");
			list.add(map4);
			
			Map<String,Object> map5=new HashMap<String, Object>();
			map5.put("xqName", "凤凰城");
			map5.put("houseInfo", "16号楼2单元503");
			map5.put("roomName", "503");
			map5.put("purposeName", "出租");
			map5.put("ownerName", "宋桂香");
			map5.put("personNum", "3");
			map5.put("phone", "13561600900");
			list.add(map5);
			
			Map<String,Object> map6=new HashMap<String, Object>();
			map6.put("xqName", "凤凰城");
			map6.put("houseInfo", "11号楼2单元1902");
			map6.put("roomName", "1902");
			map6.put("purposeName", "出租");
			map6.put("ownerName", "王雪健");
			map6.put("personNum", "4");
			map6.put("phone", "18661437200");
			list.add(map6);
			
			
			mapResult.put("list",list);
			mapResult.put("totalRows",6);
			mapResult.put("currentPage", MapUtils.getInteger(params, PageUtil.PAGE));
			return render(mapResult);*/
			return render(map);
		} catch (Exception e) {
			log.info("[CallRecordController.groupResearch,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}
	
	@PostMapping("/dayRentalResearch")
	public Map<String,Object> dayRentalResearch(@RequestBody Map<String, Object> params) {
		try {
			Map<String,Object> map = facePersonCaptureService.dayRentalResearch(params);
			/*Map<String,Object> mapResult = new HashMap<String, Object>();
			
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			Map<String,Object> map1=new HashMap<String, Object>();
			map1.put("name", "张伟");
			map1.put("xqName", "凤凰城");
			map1.put("houseInfo", "1号楼二单元201户");
			map1.put("identityStr", "出租");
			map1.put("tel", "13826201530");
			map1.put("times", "2");
			map1.put("houseName", "201户");
			list.add(map1);
			
			Map<String,Object> map2=new HashMap<String, Object>();
			map2.put("name", "曹国峰");
			map2.put("xqName", "凤凰城");
			map2.put("houseInfo", "8号楼一单元101户");
			map2.put("identityStr", "出租");
			map2.put("tel", "18699257788");
			map2.put("times", "5");
			map2.put("houseName", "101户");
			list.add(map2);
			
			Map<String,Object> map3=new HashMap<String, Object>();
			map3.put("name", "王桂芹 ");
			map3.put("xqName", "凤凰城");
			map3.put("houseInfo", "17号楼二单元302户");
			map3.put("identityStr", "出租");
			map3.put("tel", "18765972195");
			map3.put("times", "1");
			map3.put("houseName", "302户");
			list.add(map3);
			
			Map<String,Object> map4=new HashMap<String, Object>();
			map4.put("name", "袁殿俊 ");
			map4.put("xqName", "凤凰城");
			map4.put("houseInfo", "11号楼二单元1001户");
			map4.put("identityStr", "出租");
			map4.put("tel", "13756721540");
			map4.put("times", "2");
			map4.put("houseName", "1001户");
			list.add(map4);
			Map<String,Object> map5=new HashMap<String, Object>();
			map5.put("name", "王文庭");
			map5.put("xqName", "凤凰城");
			map5.put("houseInfo", "15号楼二单元903户");
			map5.put("identityStr", "出租");
			map5.put("tel", "13987201630");
			map5.put("times", "1");
			map5.put("houseName", "903户");
			list.add(map5);
			
			
			mapResult.put("list",list);
			mapResult.put("totalRows",5);
			mapResult.put("currentPage", MapUtils.getInteger(params, PageUtil.PAGE));
			return render(mapResult);*/
			return render(map);
		} catch (Exception e) {
			log.info("[CallRecordController.nightReturnCar,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}

}
