package com.windaka.suizhi.manageport.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.common.utils.SqlFileUtil;
import com.windaka.suizhi.manageport.dao.CaptureDao;
import com.windaka.suizhi.manageport.model.Capture;
import com.windaka.suizhi.manageport.service.CaptureService;
import com.windaka.suizhi.manageport.util.SqlCreateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

/**
 * @Description 摄像机设备
 * @Author wcl
 * @Date 2019/7/16 0016 下午 2:23
 */
@Slf4j
@Service
public class CaptureServiceImpl implements CaptureService {
	@Autowired
	private CaptureDao captureDao;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveCaptureDevice(Map<String, Object> params) throws OssRenderException, IOException {
		String manageId = (String) params.get("manageId");
		if (StringUtils.isBlank(manageId)) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"摄像机主键ID不能为空");

		}

		String xqCode = (String) params.get("xqCode");
		if (StringUtils.isBlank(xqCode)) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区Code不能为空");
		}

		/*LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		Capture capture = new Capture();
		capture.setCaptureId(captureId);
		capture.setXqCode((String) params.get("xqCode"));
		capture.setDeviceAddr((String) params.get("deviceAddr"));
		capture.setDeviceName((String) params.get("deviceName"));
		capture.setDeviceLocation((String) params.get("deviceLocation"));
		capture.setCapDevCode((String) params.get("capDevCode"));
		capture.setCapDevChannel((String) params.get("capDevChannel"));
		capture.setStatus((String) params.get("status"));
		capture.setDchnlRtsp((String) params.get("dchnlRtsp"));*/

		//captureId已存在时，更新数据
		if (captureDao.queryByCaptureDeviceId(manageId) > 0)
		{
			/*capture.setUpdateBy(loginAppUser.getUserId());
			capture.setUpdateTime(new Date());*/

			//更新数据
			captureDao.updateCaptureDevice(params);
			String updateCaptureDevice= SqlCreateUtil.getSqlByMybatis(CaptureDao.class.getName()+".updateCaptureDevice",params);
			SqlFileUtil.InsertSqlToFile(updateCaptureDevice);
		}else {
			/*capture.setCreatBy(loginAppUser.getUserId());
			capture.setCreatTime(new Date());
			capture.setUpdateBy(loginAppUser.getUserId());
			capture.setUpdateTime(new Date());
*/
			//保存数据
			captureDao.saveCaptureDevice(params);
			String saveCaptureDevice= SqlCreateUtil.getSqlByMybatis(CaptureDao.class.getName()+".saveCaptureDevice",params);
			SqlFileUtil.InsertSqlToFile(saveCaptureDevice);
		}

	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateCaptureDevice(Map<String, Object> params) throws OssRenderException, IOException {
		String manageId = (String) params.get("manageId");
		if (StringUtils.isBlank(manageId)) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED, "摄像机主键ID不能为空");
		}
		if (captureDao.queryByCaptureDeviceId(manageId) > 0) {
			/*LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
			Capture capture = new Capture();
			capture.setCaptureId(captureId);
			capture.setXqCode((String) params.get("xqCode"));
			capture.setDeviceAddr((String) params.get("deviceAddr"));
			capture.setDeviceName((String) params.get("deviceName"));
			capture.setDeviceLocation((String) params.get("deviceLocation"));
			capture.setCapDevCode((String) params.get("capDevCode"));
			capture.setCapDevChannel((String) params.get("capDevChannel"));
			capture.setStatus((String) params.get("status"));
			capture.setDchnlRtsp((String) params.get("dchnlRtsp"));
			capture.setUpdateBy(loginAppUser.getUserId());
			capture.setUpdateTime(new Date());*/

			//更新数据
			captureDao.updateCaptureDevice(params);
//			String updateCaptureDevice= SqlCreateUtil.getSqlByMybatis(CaptureDao.class.getName()+".updateCaptureDevice",params);
			String []colNames={"dchnlCode","deviceName",
			"dchnlPwd","dchnlRtsp","elevatorControlIp","entranceGuardIp","entranceGuardSn","deviceType","access",
			"communityCode","communityBuildingCode","communityCellCode","registerState","deviceLocation","deviceAddrName",
			"serverDchnlId","status","createBy","createDate","updateBy","updateDate",
			"remarks","dchnlDeviceCode","dchnlDeviceChannel","channelStateCar","deviceCodeCar","monitoringLocation",
			"deviceStatus","nvrInnerUrl","nvrOutUrl","serviceId","point1","point2",
			"point3","point4","deviceNamePy","deviceTypeSub",
			"xqCode","manageId"};

			int n=colNames.length;
			String []colValues=new String[n];
			for (int i=0;i<n;i++)
			{
				colValues[i]=SqlFileUtil.keyAddValue(params,colNames[i]);
			}

			String updateCaptureDevice=" update face_capture_device  SET dchnl_code="+colValues[0]+",device_name="+colValues[1]+"\n" +
					"        ,dchnl_pwd="+colValues[2]+",dchnl_rtsp="+colValues[3]+",elevator_control_ip="+colValues[4]+",entrance_guard_ip="+colValues[5]+",entrance_guard_sn="+colValues[6]+",device_type="+colValues[7]+",access="+colValues[8]+"\n" +
					"         ,community_code="+colValues[9]+",community_building_code="+colValues[10]+",community_cell_code="+colValues[11]+",register_state="+colValues[12]+",device_location="+colValues[13]+",device_addr_name="+colValues[14]+"\n" +
					"          ,server_dchnl_id="+colValues[15]+",status="+colValues[16]+",create_by="+colValues[17]+",create_date="+colValues[18]+",update_by="+colValues[19]+",update_date="+colValues[20]+"\n" +
					"           ,remarks="+colValues[21]+",dchnl_device_code="+colValues[22]+",dchnl_device_channel="+colValues[23]+",channel_state_car="+colValues[24]+",device_code_car="+colValues[25]+",monitoring_location="+colValues[26]+"\n" +
					"            ,device_status="+colValues[27]+",nvr_inner_url="+colValues[28]+",nvr_out_url="+colValues[29]+",service_id="+colValues[30]+",point1="+colValues[31]+",point2="+colValues[32]+"\n" +
					"            ,point3="+colValues[33]+",point4="+colValues[34]+",device_name_py="+colValues[35]+",device_type_sub="+colValues[36]+"\n" +
					"        where xq_code="+colValues[37]+" and manage_id="+colValues[38];
	//		SqlFileUtil.InsertSqlToFile(updateCaptureDevice);
		}else {
			throw new OssRenderException(ReturnConstants.CODE_FAILED, "该设备不存在");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteCaptureDevice(String xqCode, ArrayList manageIds) throws OssRenderException, IOException {
		if (StringUtils.isBlank(xqCode)) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区Code不能为空");
		}
		if (manageIds.size() > 0) {
			//可批量设备
			for (int i = 0; i < manageIds.size(); i++) {
				String manageId = (String) manageIds.get(i);
				captureDao.deleteCaptureDevice(xqCode,manageId);
				String deleteCaptureDevice=String.format("delete from face_capture_device where xq_code='%s' and manage_id ='%s'",xqCode,manageId);
				SqlFileUtil.InsertSqlToFile(deleteCaptureDevice);

			}
		}else {
			throw new OssRenderException(ReturnConstants.CODE_FAILED, "manageId不能为空");
		}

	}



	@Override
	public String queryXqByXqCode(String xqCode) throws OssRenderException {
		String xqName = captureDao.queryXqByXqCode(xqCode);
		return xqName;
	}


	@Override
	public List<Map<String, Object>> queryXqListByAreaId(String areaId) throws OssRenderException {
		List<Map<String,Object>> list = captureDao.queryXqListByAreaId(areaId);
		return list;
	}

	@Override
	public List<Map<String, Object>> queryCaptureListByxqCode(String xqCode) throws OssRenderException {
		List<Map<String,Object>> list = captureDao.queryCaptureListByxqCode(xqCode);
		return list;
	}

	@Override
	public int queryCaptureOnline(String xqCode) throws OssRenderException {
		int tjOnline = captureDao.queryCaptureOnline(xqCode);
		return tjOnline;
	}

	@Override
	public int queryCaptureNotOnline(String xqCode) throws OssRenderException {
		int tjNotOnline = captureDao.queryCaptureNotOnline(xqCode);
		return tjNotOnline;
	}

}
