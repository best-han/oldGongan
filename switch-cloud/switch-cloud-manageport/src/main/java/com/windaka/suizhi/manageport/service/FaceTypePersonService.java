package com.windaka.suizhi.manageport.service;

import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.manageport.model.FaceTypePerson;

import java.io.IOException;
import java.util.Map;


public interface FaceTypePersonService {
	/**
	 * 批量人脸-人脸类别维护
	 *
	 * @param faceTypePeople
	 * @throws OssRenderException
	 */
	void saveFaceTypePerson(FaceTypePerson faceTypePeople) throws OssRenderException, IOException;

	/**
	 * 人脸-人脸类别删除(同一小区可多个删除) hjt
	 * @param params
	 * @throws OssRenderException
	 */
	void delFaceTypePerson(Map<String,Object> params) throws OssRenderException, IOException;
}
