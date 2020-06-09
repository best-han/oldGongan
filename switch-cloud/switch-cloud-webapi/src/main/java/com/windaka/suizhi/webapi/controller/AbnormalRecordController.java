package com.windaka.suizhi.webapi.controller;

import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.webapi.service.AbnormalRecordService;
import com.windaka.suizhi.webapi.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 异常行为记录
 * @Author hjt
 * @Date 2019/11/1
 */
@Slf4j
@RestController
public class AbnormalRecordController extends BaseController {

	@Autowired
	private AbnormalRecordService abnormalRecordService;

	@Autowired
	private DictService dictService;

	/**
	 * 列表查询（分页）
	 * @param params
	 * @return
	 */
	@GetMapping("/abnormalRecord/list")
	public Map<String,Object> queryAbnormalRecordPageList(@RequestParam Map<String, Object> params) {
		try {
			Page<Map<String,Object>> page =abnormalRecordService.queryAbnormalRecordPageList(params);
			Map<String,Object> map = new HashMap<>();
			map.put("list",page);
			return render(page);
		}catch (Exception e){
			log.info("[AbnormalRecordController.queryAbnormalRecordPageList,参数：{},异常信息：{}]",params,e.getMessage());
			return failRender(e);
		}
	}
	/**
	 * 功能描述: 事件报警
	 * @auther: lixianhua
	 * @date: 2019/12/25 15:20
	 * @param:
	 * @return:
	 */
	@GetMapping(value = "/alarm/abnormal")
	public Map<String,Object> queryAbnormalList(@RequestParam Map<String,Object> params) {
		try{
			Map<String,Object> map = abnormalRecordService.queryAbnormalList(params);
			return render(map);
		}catch (Exception e){
			log.info("[DeviceController.queryDeviceInfo,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}
	/**
	 * 功能描述: 根据主键获取异常报警信息
	 * @auther: lixianhua
	 * @date: 2020/1/9 16:26
	 * @param:
	 * @return:
	 */
	@GetMapping(value = "/alarm/abnormalInfo/{id}")
	public Map<String, Object> queryAbnormalAlarmInfo(@PathVariable("id") Integer id) {
		try {
			Map<String, Object> map = abnormalRecordService.queryAlarmAbnormalById(id);
			return render(map);
		} catch (Exception e) {
			log.info("[AbnormalRecordController.queryAbnormalAlarmInfo,参数：{},异常信息{}]", id, e.getMessage());
			return failRender(e);
		}
	}
	/**
	 * 功能描述: 事件类型查询
	 * @auther: lixianhua
	 * @date: 2019/12/25 14:26
	 * @param:
	 * @return:
	 */
	@GetMapping(value = "/library/eventList")
	public Map<String,Object> queryEventList(@RequestParam Map<String,Object> params) {
		try{
			params.put("dictCode","event_name");
			params.put("dictLabels","4,0");
			List<Map<String,Object>> map = dictService.getEventList(params);
			return render(map);
		}catch (Exception e){
			log.info("[DeviceController.queryEventList,参数：{},异常信息{}]",params,e.getMessage());
			return failRender(e);
		}
	}
}
