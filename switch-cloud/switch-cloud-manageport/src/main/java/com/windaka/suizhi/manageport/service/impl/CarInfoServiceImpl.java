package com.windaka.suizhi.manageport.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.constants.CommonConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.*;
import com.windaka.suizhi.manageport.config.FtpProperties;
import com.windaka.suizhi.manageport.dao.CarInfoDao;
import com.windaka.suizhi.manageport.model.CarInfo;
import com.windaka.suizhi.manageport.service.CarInfoService;
import com.windaka.suizhi.manageport.service.FastdfsService;
import com.windaka.suizhi.manageport.util.SqlCreateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 车辆信息
 * @Author wcl
 * @Date 2019/8/20 0020 上午 11:29
 */
@Slf4j
@Service
public class CarInfoServiceImpl implements CarInfoService {
	@Autowired
	private CarInfoDao carInfoDao;

	@Autowired
	FtpProperties ftpProperties;

	@Autowired
	private FastdfsService fastdfsService;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveCarInfo(String xqCode,List<Map<String,Object>> carInfos) throws OssRenderException, IOException {

		for(int i=0;i<carInfos.size();i++) {
			Map<String, Object> carInfo = carInfos.get(i);
			//carInfo.put("personCode",carInfo.get("ownerId"));
			//carInfo.remove("ownerId",carInfo.get("ownerId"));
			if (carInfo.get("parkType") == ""){
				carInfo.put("parkType",null);
			}
			if (carInfo.get("listType") == ""){
				carInfo.put("listType",null);
			}
			LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
			/*carInfo.put("creatTime",new Date());
			carInfo.put("creatBy",loginAppUser.getUserId());
			carInfo.put("updateTime",new Date());
			carInfo.put("updateBy",loginAppUser.getUserId());*/
			if(carInfo.get("status")==null|| carInfo.get("status")==""){
				carInfo.put("status","1");
			}
			if (null != carInfo.get("carImg") && StringUtils.isNotBlank(carInfo.get("carImg").toString())) {
				byte[] byteArr= PicUtil.stringToInputStream(carInfo.get("carImg").toString());
				// 根据最大主键
				Integer id = carInfoDao.getMaxId();
				String fileName = PicUtil.getPicName("car_info", id==null?1:(id+1));
				//图片放入打包路径
			/*	FileUploadUtil.inputStreamToLocalFile(byteArr, CommonConstants.LOCAL_IMAGE_FILE_PATH,fileName);
				// 图片放入访问路径
				FileUploadUtil.inputStreamToLocalFile(byteArr,CommonConstants.LOCAL_PROJECT_IMAGE_PATH,fileName);
				carInfo.put("base64Img", fileName);*/

				//封装访问路径：年/月/日
				Date date=new Date();
				//车的图片需永久存储，以此路径防止定时任务清除
				String carPicRelativePath="car"+ File.separator +PicUtil.getPicRelativePath(date);
				// 图片放入打包路径
				FileUploadUtil.inputStreamToLocalFile(byteArr,
						CommonConstants.LOCAL_IMAGE_FILE_PATH + File.separator +carPicRelativePath, fileName);
				// 图片放入访问路径
				FileUploadUtil.inputStreamToLocalFile(byteArr,
						CommonConstants.LOCAL_PROJECT_IMAGE_PATH + File.separator +carPicRelativePath, fileName);
				carInfo.put("carImg", carPicRelativePath+ fileName);

			}
			carInfoDao.saveCarInfo(xqCode,carInfo);
			carInfo.put("xqCode",xqCode);

		}
		String []keyNames={"xqCode","manageId","carCode","carNum","carNumColor","carType","carBrand","carColor","carMark",
				"carStatus","carPic","ownerId","memo","parkType","parkingLotCode","listType",
				"status","creatBy","creatDate","updateBy","updateDate","remarks","parkingLotName","carImg","roomId",
				"carNumColorName","carTypeName","carBrandName","carColorName","carStatusName","parkTypeName","listTypeName"};
		String sqlContentFront="INSERT INTO car_info(xq_code,manage_id,car_code,car_num,car_num_color,car_type,car_brand,car_color,car_mark,car_status,car_pic,owner_id,memo,park_type" +
				",parking_lot_code,list_type,status,create_by,create_date,update_by,update_date,remarks,parking_lot_name,car_img,room_id," +
				"car_num_color_name,car_type_name,car_brand_name,car_color_name,car_status_name,park_type_name,list_type_name)" +
				" values";
		SaveUtil.listSqlSave(keyNames,sqlContentFront,xqCode,carInfos);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateCarInfo(Map<String, Object> params) throws OssRenderException, IOException {
		String manageId = (String) params.get("manageId");
		if (StringUtils.isBlank(manageId)) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED, "manageId不能为空");
		}
		String xqCode = (String) params.get("xqCode");
		if (StringUtils.isBlank(xqCode)) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED, "xqCode不能为空");
		}
		if (carInfoDao.queryByCarInfo(manageId,xqCode) > 0) {
			if (null != params.get("carImg") && StringUtils.isNotBlank(params.get("carImg").toString())) {
				byte[] byteArr  = PicUtil.stringToInputStream(params.get("carImg").toString());
				// 根据最大主键
				int id = carInfoDao.getIdByCondion(params);
				String fileName = PicUtil.getPicName("car_info", id);
				// 图片放到打包路径
			/*	FileUploadUtil.inputStreamToLocalFile(byteArr, CommonConstants.LOCAL_IMAGE_FILE_PATH,fileName);
				// 图片放到本地访问路径
				FileUploadUtil.inputStreamToLocalFile(byteArr,CommonConstants.LOCAL_PROJECT_IMAGE_PATH,fileName);
				params.put("carImg", fileName);*/

				//封装访问路径：年/月/日
				Date date=new Date();
				//车的图片需永久存储，以此路径防止定时任务清除
				String carPicRelativePath="car"+ File.separator +PicUtil.getPicRelativePath(date);
				// 图片放入打包路径
				FileUploadUtil.inputStreamToLocalFile(byteArr,
						CommonConstants.LOCAL_IMAGE_FILE_PATH + File.separator +carPicRelativePath, fileName);
				// 图片放入访问路径
				FileUploadUtil.inputStreamToLocalFile(byteArr,
						CommonConstants.LOCAL_PROJECT_IMAGE_PATH + File.separator +carPicRelativePath, fileName);
				params.put("carImg", carPicRelativePath+ fileName);

			}
			//更新数据
			carInfoDao.updateCarInfo(params);
		}else {
			throw new OssRenderException(ReturnConstants.CODE_FAILED, "该车辆信息不存在");
		}
		String sqlContent= SqlCreateUtil.getSqlByMybatis(CarInfoDao.class.getName()+".updateCarInfo",params);
		SqlFileUtil.InsertSqlToFile(sqlContent);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteCarInfo(Map<String, Object> params) throws OssRenderException, IOException {
		String manageId = (String) params.get("manageId");
		if (StringUtils.isBlank(manageId)) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED, "manageId不能为空");
		}
		String xqCode = (String) params.get("xqCode");
		if (StringUtils.isBlank(xqCode)) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED, "xqCode不能为空");
		}

		if (carInfoDao.queryByCarInfo(manageId,xqCode) > 0) {
			carInfoDao.deleteCarInfo(xqCode,manageId);
		}else {
			throw new OssRenderException(ReturnConstants.CODE_FAILED, "该车辆信息不存在");
		}
		String sqlContent= String.format("delete from car_info where xq_code='%s' and manage_id='%s'",xqCode,manageId);
		SqlFileUtil.InsertSqlToFile(sqlContent);

	}
}
