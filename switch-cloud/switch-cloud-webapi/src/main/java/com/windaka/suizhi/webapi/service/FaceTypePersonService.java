package com.windaka.suizhi.webapi.service;

import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.webapi.model.FaceTypePerson;

import java.util.List;
import java.util.Map;


public interface FaceTypePersonService {
	/**
	 * 批量人脸-人脸类别维护
	 *人员布控-新增 ygy
	 * @param params
	 * @throws OssRenderException
	 */
	void saveFaceTypePerson(Map<String,Object> params) throws OssRenderException;

	/**
	 * 人脸-人脸类别删除(同一小区可多个删除) hjt
     * 人员布控-删除 ygy
	 * @param params
	 * @throws OssRenderException
	 */
	void delFaceTypePerson(Map<String,Object> params) throws OssRenderException;

    /**
     * 人员布控-列表查询 ygy
     * @param params
     * @return
     * @throws OssRenderException
     */
	Map<String,Object> faceFaceTypeList(Map<String,Object> params) throws OssRenderException;


}
