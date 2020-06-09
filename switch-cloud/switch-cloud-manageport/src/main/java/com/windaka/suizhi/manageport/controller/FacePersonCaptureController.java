package com.windaka.suizhi.manageport.controller;

import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.manageport.model.FacePersonAttr;
import com.windaka.suizhi.manageport.service.FacePersonCaptureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Map;
import javax.annotation.Resource;

/**
 * 
 * @project: switch-cloud-manageport
 * @Description: 
 * @author: yangkai
 * @date: 2019年12月5日 下午4:23:40
 */
@Slf4j
@Controller
@RequestMapping("/person")
public class FacePersonCaptureController extends BaseController {

	@Resource
	private FacePersonCaptureService facePersonCaptureService;
	
	@PostMapping("/addFacePersonCapture")
	@ResponseBody
	public Map<String,Object> addFacePersonCapture(@RequestBody FacePersonAttr facePersonAttr) {
		try{
			String xqCode=facePersonAttr.getXqCode();
			if(xqCode!=null&&!xqCode.equals(""))
			{
				facePersonAttr.setCaptureTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(facePersonAttr.getCaptureTimeStr()));
				facePersonCaptureService.addFacePersonAttr(facePersonAttr);
			}
			else
			{
				log.info("[FacePersonCaptureController.addFacePersonCapture,参数：{},异常信息：{}]",facePersonAttr,"小区不能为空");
			}
			return render();
		}catch (Exception e){
			e.printStackTrace();
			log.info("[FacePersonCaptureController.addFacePersonCapture,参数：{},异常信息：{}]",facePersonAttr,e.getMessage());
			//log.info(e.toString());
			return failRender(e);
		}
	}

}
