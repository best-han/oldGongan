package com.windaka.suizhi.mpi.controller;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.mpi.service.AppUserService;
import com.windaka.suizhi.mpi.service.FaceTrackRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Description TODO
 * @Author wcl
 * @Date 2019/5/9 0009 上午 10:18
 */
@Slf4j
@RestController
public class FaceTrackRecordController extends BaseController {

	@Autowired
	private FaceTrackRecordService faceTrackRecordService;

	@Autowired
	private AppUserService appUserService;

	@PostMapping("/face/track/listByImg")
	public Map<String,Object> queryImageByImage(@RequestParam Map<String,Object> params){
		try{
			//获取当前用户权限下的所有小区Code
			String userId = (String) params.get("userId");
			if(StringUtils.isBlank(userId)){
				throw new OssRenderException(ReturnConstants.CODE_FAILED,"userId为空");
			}
			params.put("xqCode",appUserService.checkAuth(userId));
			Map<String,Object> map = faceTrackRecordService.queryImgByImg(params);
			return render(map);
		}catch (Exception e){
			log.info("[FaceTrackRecordService.queryImgByImg,参数：{},异常信息{}]","",e.getMessage());
			return failRender(e);
		}

	}



}
