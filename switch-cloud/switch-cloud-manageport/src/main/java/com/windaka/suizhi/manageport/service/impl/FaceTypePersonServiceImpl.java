package com.windaka.suizhi.manageport.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.SqlFileUtil;
import com.windaka.suizhi.manageport.dao.FaceTypePersonDao;
import com.windaka.suizhi.manageport.model.FaceTypePerson;
import com.windaka.suizhi.manageport.service.FaceTypePersonService;
import com.windaka.suizhi.manageport.util.SqlCreateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
public class FaceTypePersonServiceImpl implements FaceTypePersonService {
	@Autowired
	private FaceTypePersonDao faceTypePersonDao;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveFaceTypePerson(FaceTypePerson faceTypePerson) throws OssRenderException, IOException {
		faceTypePersonDao.saveFaceTypePerson(faceTypePerson);
		String sqlContent= SqlCreateUtil.getSqlByBean(FaceTypePersonDao.class.getName()+".saveFaceTypePerson",faceTypePerson);
		SqlFileUtil.InsertSqlToFile(sqlContent);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void delFaceTypePerson(Map<String,Object> params) throws OssRenderException, IOException {
		String xqCode=(String)params.get("xqCode");
		if (StringUtils.isBlank(xqCode)) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区Code不能为空");
		}
		List personIds=(List<String>) params.get("personIds");
		if(CollectionUtils.isEmpty(personIds)){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"personIds不能为空");
		}
		faceTypePersonDao.delFaceTypePerson(params);
		String sqlContent= SqlCreateUtil.getSqlByMybatis(FaceTypePersonDao.class.getName()+".delFaceTypePerson",params);
		SqlFileUtil.InsertSqlToFile(sqlContent);
	}


}
