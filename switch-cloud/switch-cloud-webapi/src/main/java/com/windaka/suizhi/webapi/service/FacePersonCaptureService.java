package com.windaka.suizhi.webapi.service;

import com.windaka.suizhi.common.utils.PageUtil;
import com.windaka.suizhi.common.utils.PropertiesUtil;
import com.windaka.suizhi.webapi.dao.FacePersonCaptureDao;
import com.windaka.suizhi.webapi.model.CarAttr;
import com.windaka.suizhi.webapi.model.FacePersonAttr;
import com.windaka.suizhi.webapi.model.Water;
import com.windaka.suizhi.webapi.util.DateUtil;

import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FacePersonCaptureService {

    @Resource
    private FacePersonCaptureDao facePersonCaptureDao;

    
    public Map<String,Object> getNightReturnPerson(Map<String, Object> params) throws Exception{
    	List<FacePersonAttr> list = new ArrayList<FacePersonAttr>();
    	
    	int totalRows = facePersonCaptureDao.totalRows(params);
		Map<String,Object> mapResult=new HashMap<>();//返回结果map
		if (totalRows > 0) {
			PageUtil.pageParamConver(params, true);
			list = facePersonCaptureDao.getNightReturnPerson(params);
			for(FacePersonAttr face:list){
				face.setImg(PropertiesUtil.getLocalTomcatImageIp()+face.getImg());
			}
		}
		mapResult.put("list",list);
		mapResult.put("totalRows",totalRows);
		mapResult.put("currentPage", MapUtils.getInteger(params, PageUtil.PAGE));
    	return mapResult;
    }
    
    public Map<String,Object> getNightReturnCar(Map<String, Object> params) throws Exception{
    	List<CarAttr> list = new ArrayList<CarAttr>();
    	
    	int totalRows = facePersonCaptureDao.totalCarRows(params);
    	Map<String,Object> mapResult=new HashMap<>();//返回结果map
    	if (totalRows > 0) {
    		PageUtil.pageParamConver(params, true);
    		list = facePersonCaptureDao.getNightReturnCar(params);
    	}
    	mapResult.put("list",list);
    	mapResult.put("totalRows",totalRows);
    	mapResult.put("currentPage", MapUtils.getInteger(params, PageUtil.PAGE));
    	return mapResult;
    }
    
    public Map<String,Object> waterRate(Map<String, Object> params) throws Exception{
    	List<Water> list = new ArrayList<Water>();
    	
    	int totalRows = facePersonCaptureDao.totalWateRows(params);
    	Map<String,Object> mapResult=new HashMap<>();//返回结果map
    	if (totalRows > 0) {
    		PageUtil.pageParamConver(params, true);
    		list = facePersonCaptureDao.waterRate(params);
    		for(Water water :list){
    			/*String waterUsed = facePersonCaptureDao.selectWaterYield(water.getManageId());
    			waterUsed=waterUsed==null?"0":waterUsed;*/
    			String waterUsed = water.getWaterYield();
    			waterUsed=waterUsed==null?"0":waterUsed;
    			water.setWaterYield(waterUsed+"m³");
    			String str=facePersonCaptureDao.selectPhone(water.getOwnerId());
    			if(str!=null && str.trim().length()!=0&& str.trim().length()!=1 &&str.contains(",")){
    				water.setOwnerName(str.split(",")[0]);
    				if(str.endsWith(",")){
    					water.setPhone("");
    				}else{
    					water.setPhone(str.split(",")[1]);
    				}
        			
    			}else{
    				water.setOwnerName("");
        			water.setPhone("");
    			}
    			
    		}
    	}
    	mapResult.put("list",list);
    	mapResult.put("totalRows",totalRows);
    	mapResult.put("currentPage", MapUtils.getInteger(params, PageUtil.PAGE));
    	return mapResult;
    }
    
    public Map<String,Object> groupResearch(Map<String, Object> params) throws Exception{
    	List<Water> list = new ArrayList<Water>();
    	if(null==params.get("associateNum")||"".endsWith(params.get("associateNum").toString())){
    		params.put("associateNum", 5);
    	}
    	int totalRows = facePersonCaptureDao.totalGroupRows(params);
    	Map<String,Object> mapResult=new HashMap<>();//返回结果map
    	if (totalRows > 0) {
    		PageUtil.pageParamConver(params, true);
    		list = facePersonCaptureDao.groupResearch(params);
    		for(Water water :list){
    			water.setPersonNum(water.getPersonNum());
    			String str=facePersonCaptureDao.selectPhone(water.getOwnerId());
    			if(str!=null && str.trim().length()!=0&& str.trim().length()!=1 &&str.contains(",")){
    				water.setOwnerName(str.split(",")[0]);
    				if(str.endsWith(",")){
    					water.setPhone("");
    				}else{
    					water.setPhone(str.split(",")[1]);
    				}
        			
    			}else{
    				water.setOwnerName("");
        			water.setPhone("");
    			}
    		}
    	}
    	mapResult.put("list",list);
    	mapResult.put("totalRows",totalRows);
    	mapResult.put("currentPage", MapUtils.getInteger(params, PageUtil.PAGE));
    	return mapResult;
    }
    
    public Map<String,Object> dayRentalResearch(Map<String, Object> params) throws Exception{
    	List<Water> list = new ArrayList<Water>();
    	
    	int totalRows = facePersonCaptureDao.totalDayRentalResearch(params);
    	Map<String,Object> mapResult=new HashMap<>();//返回结果map
    	if (totalRows > 0) {
    		PageUtil.pageParamConver(params, true);
    		list = facePersonCaptureDao.dayRentalResearch(params);
    		for(Water water :list){
    			water.setBindNum(water.getRoomName().substring(0,1));
    			String str=facePersonCaptureDao.selectPhone(water.getOwnerId());
    			if(str!=null && str.trim().length()!=0&& str.trim().length()!=1 &&str.contains(",")){
    				water.setOwnerName(str.split(",")[0]);
    				if(str.endsWith(",")){
    					water.setPhone("");
    				}else{
    					water.setPhone(str.split(",")[1]);
    				}
        			
    			}else{
    				water.setOwnerName("");
        			water.setPhone("");
    			}
    		}
    	}
    	mapResult.put("list",list);
    	mapResult.put("totalRows",totalRows);
    	mapResult.put("currentPage", MapUtils.getInteger(params, PageUtil.PAGE));
    	return mapResult;
    }

}
