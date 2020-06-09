package com.windaka.suizhi.upcapture.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.constants.CommonConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.FileUploadUtil;
import com.windaka.suizhi.common.utils.PicUtil;
import com.windaka.suizhi.common.utils.SqlFileUtil;
import com.windaka.suizhi.upcapture.dao.AbnormalRecordDao;
import com.windaka.suizhi.upcapture.service.AbnormalRecordService;
import com.windaka.suizhi.upcapture.util.FileUtil;
import com.windaka.suizhi.upcapture.util.SqlCreateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;

/**
 * 异常行为 hjt
 */
@Slf4j
@Service
public class AbnormalRecordServiceImpl implements AbnormalRecordService {
	@Autowired
	private AbnormalRecordDao abnormalRecordDao;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveAbnormalRecord(Map<String, Object> params) throws OssRenderException, IOException {
		if (null != params.get("base64Img") && StringUtils.isNotBlank(params.get("base64Img").toString())) {
			byte[] byteArr = PicUtil.stringToInputStream(params.get("base64Img").toString());
			// 根据manageId主键
			Integer id = abnormalRecordDao.getMaxId();
			String fileName = PicUtil.getPicName("record_abnormal", null == id?1:(id+1));
			// 图片放入打包路径
		/*	FileUploadUtil.inputStreamToLocalFile(byteArr, CommonConstants.LOCAL_IMAGE_FILE_PATH, fileName);
			// 图片放入访问路径
			FileUploadUtil.inputStreamToLocalFile(byteArr, CommonConstants.LOCAL_PROJECT_IMAGE_PATH, fileName);
			params.put("base64Img", fileName);*/

			//封装访问路径：年/月/日
			Date date=new Date();
			// 图片放入打包路径
			FileUploadUtil.inputStreamToLocalFile(byteArr,
					CommonConstants.LOCAL_IMAGE_FILE_PATH + File.separator +PicUtil.getPicRelativePath(date), fileName);
			// 图片放入访问路径
			FileUploadUtil.inputStreamToLocalFile(byteArr,
					CommonConstants.LOCAL_PROJECT_IMAGE_PATH + File.separator +PicUtil.getPicRelativePath(date), fileName);
			params.put("base64Img", PicUtil.getPicRelativePath(date)+ fileName);
		}
		abnormalRecordDao.saveAbnormalRecord(params);
		String sql= SqlCreateUtil.getSqlByMybatis(AbnormalRecordDao.class.getName()+".saveAbnormalRecord",params);
		SqlFileUtil.InsertSqlToFile(sql);
	}


}
