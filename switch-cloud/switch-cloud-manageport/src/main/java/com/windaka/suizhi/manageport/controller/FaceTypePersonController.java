package com.windaka.suizhi.manageport.controller;

import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.manageport.model.FaceTypePerson;
import com.windaka.suizhi.manageport.service.FaceTypePersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@Slf4j
@RestController
public class FaceTypePersonController extends BaseController {
	@Autowired
	private FaceTypePersonService faceTypePersonService;

	/**
	* @Description: 维护人脸-人脸类别
	* @Param: [params]
	* @return: java.util.Map<java.lang.String,java.lang.Object>
	*/
	@PostMapping("/face/faceType/person")
	public Map<String,Object> saveFaceTypePerson(@RequestBody FaceTypePerson faceTypePerson) {
		try{
			/*faceTypePerson.setXqCode(faceTypePerson.getXqCode());
			faceTypePerson.setPersonId(faceTypePerson.getPersonId());
			faceTypePerson.setFaceTypeCode(faceTypePerson.getFaceTypeCode());*/
			faceTypePersonService.saveFaceTypePerson(faceTypePerson);
			return render();
		}catch (Exception e){
			log.info("[FaceTypePersonController.saveFaceTypePerson,参数：{},异常信息{}]","",e.getMessage());
			return failRender(e);
		}
	}

	/**
	 * 人脸-人脸类别删除(同一小区可多个删除) hjt
	 * @param params
	 * @return
	 */
	@DeleteMapping("/face/faceType/person")
	public Map<String,Object> delFaceTypePerson(@RequestBody Map params) {
		try{
			faceTypePersonService.delFaceTypePerson(params);
			return render();
		}catch (Exception e){
			log.info("[FaceTypePersonController.delFaceTypePerson,参数：{},异常信息{}]","",e.getMessage());
			return failRender(e);
		}
	}
}
