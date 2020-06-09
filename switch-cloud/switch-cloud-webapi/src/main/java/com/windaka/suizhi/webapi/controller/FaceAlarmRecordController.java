package com.windaka.suizhi.webapi.controller;

import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.webapi.service.AppUserService;
import com.windaka.suizhi.webapi.service.CarRecordService;
import com.windaka.suizhi.webapi.service.FaceAlarmRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author wcl
 * @Date 2019/5/9 0009 上午 10:18
 */
@Slf4j
@RestController
public class FaceAlarmRecordController extends BaseController {
	@Autowired
	private FaceAlarmRecordService faceAlarmRecordService;
	@Autowired
	private CarRecordService carRecordService;
	@Autowired
	protected AppUserService appUserService;

	/** 
	* @Description: 人脸报警记录上传
	* @Param: [params] 
	* @return: java.util.Map<java.lang.String,java.lang.Object> 
	* @Author: Ms.wcl
	* @Date: 2019/5/9 0009
	*/ 
	@PostMapping("/face/alarmRecord")
	public Map<String,Object> saveFaceTrackRecord(@RequestBody Map<String,Object> params) {
		try{
			String xqCode = (String) params.get("xqCode");
			faceAlarmRecordService.saveFaceTrackRecord(xqCode, (List<Map<String, Object>>) params.get("list"));
			return render();
		}catch (Exception e){
			log.info("[FaceAlarmRecordController.saveFaceTrackRecord,参数：{},异常信息{}]","",e.getMessage());
			return failRender(e);
		}
	}

	/**
	* @Description:  人脸/车辆报警记录信息处理
	* @Param: [params]
	* @return: java.util.Map<java.lang.String,java.lang.Object>
	* @Author: Ms.wcl
	* @Date: 2019/5/10 0010
	*/
	@PutMapping("/face/alarmRecord")
	public Map<String,Object> updateFaceTrackRecord(@RequestParam Map<String,Object> params) {
		try{
			String faceTypeCode=(String)params.get("faceTypeCode");
			if(StringUtils.isBlank(faceTypeCode)){
				throw new OssRenderException(ReturnConstants.MSG_OPER_FAILED,"faceTypeCode");
			}
			String s=faceTypeCode.substring(0,1);
			if("5".equals(s)){//车辆报警记录
				carRecordService.updateCarRecord(params);
			}else{
				faceAlarmRecordService.updateFaceAlarmRecord(params);
			}
			return render();
		}catch (Exception e){
			log.info("[FaceAlarmRecordController.updateFaceTrackRecord,参数：{},异常信息{}]","",e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * @Description: 报警记录列表查询
	 * @Param: [params]
	 * @return: java.util.Map<java.lang.String,java.lang.Object>
	 * @Author: Ms.wcl
	 * @Date: 2019/5/9 0009
	 */
	@GetMapping("/face/alarmRecord/list")
	public Map<String,Object> queryAlarmRecordList(@RequestParam Map<String,Object> params) {
		try{
			//验证当前用户查询权限
			LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
			params.put("xqCode",appUserService.checkAuth(loginAppUser.getUserId()));

			Page<Map<String,Object>> page = faceAlarmRecordService.queryAlarmRecordList(params);
			Map<String,Object> map = new HashMap<>();
			map.put("list",page);
			return render(page);
		}catch (Exception e){
			log.info("[FaceAlarmRecordController.queryAlarmRecordList,参数：{},异常信息{}]","",e.getMessage());
			return failRender(e);
		}
	}

	/** 
	* @Description: 查询报警记录
	* @Param: [alarmId] 
	* @return: java.util.Map<java.lang.String,java.lang.Object> 
	* @Author: Ms.wcl
	* @Date: 2019/5/10 0010
	*/ //追加车辆  hjt
	@GetMapping("/face/alarmRecord")
	public Map<String,Object> searchAlarmRecord(@RequestParam Map<String,Object> params) {
		try{
			String faceTypeCode=(String)params.get("faceTypeCode");
			if(StringUtils.isBlank(faceTypeCode)){
				throw new OssRenderException(ReturnConstants.MSG_OPER_FAILED,"faceTypeCode为空");
			}
			String alarmId=(String)params.get("alarmId");
			if(StringUtils.isBlank(alarmId)){
				throw new OssRenderException(ReturnConstants.MSG_OPER_FAILED,"alarmId为空");
			}
			String s=faceTypeCode.substring(0,1);
			Map map=new HashMap();
			if("5".equals(s)){//车辆报警记录
				map = carRecordService.queryCarRecord(params);
			}else{
				map = faceAlarmRecordService.queryByAlarmId(alarmId);
			}
			map.put("faceTypeCode",faceTypeCode);//前端要求追加以作判断
			return render(map);
		}catch (Exception e){
			log.info("[FaceAlarmRecordController.queryByAlarmId,参数：{},异常信息{}]","",e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 人名/车牌号模糊查询
	 * @param params
	 * @return
	 */
	@GetMapping("/face/searchPersonOrCar/list")
	public Map<String,Object> queryPersonOrCar(@RequestParam Map<String,Object> params) {
		try{
			LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
			params.put("xqCode",appUserService.checkAuth(loginAppUser.getUserId()));
			List<Map<String,Object>> list = faceAlarmRecordService.queryPersonOrCar(params);
			return render(list);
		}catch (Exception e){
			log.info("[FaceAlarmRecordController.queryPersonOrCar,参数：{},异常信息{}]","",e.getMessage());
			return failRender(e);
		}
	}
	/**
	 * 功能描述: 获取人员报警信息
	 * @auther: lixianhua
	 * @date: 2019/12/18 9:59
	 * @param:
	 * @return:
	 */
	@GetMapping(value = "/alarm/person")
	public Map<String,Object> queryPersonAlarmList(@RequestParam Map<String,Object> params) {
		try{
			Map<String,Object> map = faceAlarmRecordService.queryAlarmPersonList(params);
			return render(map);
		}catch (Exception e){
			log.info("[DeviceController.queryDeviceInfo,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 功能描述: 根据主键获取人员报警信息
	 * @auther: lixianhua
	 * @date: 2020/1/9 8:50
	 * @param:
	 * @return:
	 */
	@GetMapping(value = "/alarm/personInfo/{id}")
	public Map<String,Object> queryPersonAlarmInfo(@PathVariable("id") Integer id) {
		try{
			Map<String,Object> map = faceAlarmRecordService.queryAlarmPersonById(id);
			return render(map);
		}catch (Exception e){
			log.info("[DeviceController.queryPersonAlarmInfo,参数：{},异常信息{}]",id,e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 功能描述: 获取报警处理信息
	 * @auther: lixianhua
	 * @date: 2019/12/19 9:02
	 * @param:
	 * @return:
	 */
	@GetMapping(value = "/alarm/handleInfo")
	public Map<String,Object> queryHandleInfo(@RequestParam Map<String,Object> params) {
		try{
			Map<String,Object> map = faceAlarmRecordService.queryHandleInfo(params);
			return render(map);
		}catch (Exception e){
			log.info("[DeviceController.queryDeviceInfo,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}


	/**
	 * 功能描述: 更新报警处理信息
	 * @auther: lixianhua
	 * @date: 2019/12/19 9:03
	 * @param:
	 * @return:
	 */
	@PutMapping(value = "/alarm/saveHandle")
	public Map<String,Object> addHandleInfo(@RequestBody Map<String,Object> params) {
		try{
			 faceAlarmRecordService.saveAlarmHandleInfo(params);
			return render();
		}catch (Exception e){
			e.printStackTrace();
			log.info("[DeviceController.queryDeviceInfo,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}
	/**
	 * 功能描述: 获取首页预警信息
	 * @auther: lixianhua
	 * @date: 2019/12/29 12:16
	 * @param:
	 * @return:
	 */
	@GetMapping(value = "/alarm/homeList")
	public Map<String,Object> homeAlarmInfo(@RequestParam Map<String,Object> params) {
		try{
			List<Map<String,Object>> list = faceAlarmRecordService.queryHomeAlarmList(params);
			return render(list);
		}catch (Exception e){
			log.info("[DeviceController.homeAlarmInfo,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}
}
