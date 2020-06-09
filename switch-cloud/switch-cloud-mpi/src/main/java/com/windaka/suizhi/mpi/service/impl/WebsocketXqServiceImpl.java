package com.windaka.suizhi.mpi.service.impl;

import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.PageUtil;
import com.windaka.suizhi.mpi.dao.WebsocketUserDao;
import com.windaka.suizhi.mpi.dao.WebsocketXqDao;
import com.windaka.suizhi.mpi.service.WebsocketXqService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class WebsocketXqServiceImpl implements WebsocketXqService {

	@Autowired
	private WebsocketXqDao xqDao;
	@Autowired
	private WebsocketUserDao userDao;

	/**
	 * 内部调用  hjt
	 * @param
	 * @return
	 */
	@Transactional
	@Override
	public List<Map<String,Object>> queryListXqInfo(String areaId, String xqCode,String userId) throws OssRenderException {
		Map<String,Object> map = new HashMap<>();
		List<Map<String,Object>> list=new ArrayList<>();
        String isAdmin=userDao.queryIsSuperByUserId(userId);
        if(isAdmin.equalsIgnoreCase("1")){//是超管
			if(StringUtils.isNotBlank(xqCode)){
				map=queryByCode(xqCode);
				list.add(map);
			}else{
				list=queryListXqByAreaId(areaId);
			}
		}else{//不是超管
			if(StringUtils.isNotBlank(xqCode)){
                map=queryByxqCodeUserId(xqCode,userId);
				list.add(map);
			}else{
				list=queryListXqByAreaIdUserId(areaId,userId);
			}
		}
		return list;

	}


	public Map<String, Object> queryByCode(String xqCode) throws OssRenderException {
		Map map = xqDao.queryByxqCode(xqCode);
		if(map==null){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"查询的小区不存在");
		}
		return map;
	}
	public Map<String, Object> queryByxqCodeUserId(String xqCode,String userId) throws OssRenderException {
		Map map = xqDao.queryByxqCodeUserId(xqCode,userId);
		if(map==null){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"查询的小区不存在");
		}
		return map;
	}
	public Page<Map<String, Object>> queryXqList(Map<String, Object> params) throws OssRenderException {
		int totalRows = xqDao.totalRows(params);
		List<Map<String,Object>> list = Collections.emptyList();
		if (totalRows > 0) {
			PageUtil.pageParamConver(params, true);
			list = xqDao.queryXqList(params);
		}
		return new Page<>(totalRows, MapUtils.getInteger(params,PageUtil.PAGE), list);
	}
	public List<Map<String, Object>> queryListXqByAreaId(String areaId) throws OssRenderException {
		List<Map<String,Object>> list = xqDao.queryListXqByAreaId(areaId);
		/*if (list.size() < 1) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"该区域无小区");
		}*/
		return list;
	}
	public List<Map<String, Object>> queryListXqByAreaIdUserId(String areaId,String userId) throws OssRenderException {
		List<Map<String,Object>> list = xqDao.queryListXqByAreaIdUserId(areaId,userId);
		/*if (list.size() < 1) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"该区域无小区");
		}*/
		return list;
	}



}
