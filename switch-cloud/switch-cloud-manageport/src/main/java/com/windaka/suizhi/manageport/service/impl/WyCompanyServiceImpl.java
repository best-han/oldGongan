package com.windaka.suizhi.manageport.service.impl;

import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.common.utils.PageUtil;
import com.windaka.suizhi.manageport.dao.WyCompanyDao;
import com.windaka.suizhi.manageport.model.WyCompany;
import com.windaka.suizhi.manageport.service.WyCompanyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class WyCompanyServiceImpl implements WyCompanyService {
	@Autowired
	private WyCompanyDao wyCompanyDao;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveWy(WyCompany wyCompany) throws OssRenderException {
		String companyCode = wyCompany.getCompanyCode();
		if (StringUtils.isBlank(companyCode)) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"物业公司Code不能为空");
		}
		if(wyCompanyDao.queryByCode(wyCompany.getCompanyCode()) != null){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"物业公司已存在");
		}
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		if(StringUtils.isBlank(wyCompany.getCreateBy())){
			wyCompany.setCreateBy(loginAppUser.getUserId());
		}
		if(wyCompany.getCreateDate()==null){
			wyCompany.setCreateDate(new Date());
		}
		if(StringUtils.isBlank(wyCompany.getUpdateBy())){
			wyCompany.setUpdateBy(loginAppUser.getUserId());
		}
		if(wyCompany.getUpdateDate()==null){
			wyCompany.setUpdateDate(new Date());
		}
		//添加物业公司
		wyCompanyDao.saveWy(wyCompany);

	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateWy(WyCompany wyCompany) throws OssRenderException {
		if (wyCompany == null || StringUtils.isBlank(wyCompany.getCompanyCode())) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"物业公司Code不能为空");
		}
		if (wyCompanyDao.queryByCode(wyCompany.getCompanyCode())==null) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"该物业公司不存在");
		}
		wyCompanyDao.updateWy(wyCompany);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteWy(String companyCode) throws OssRenderException {
		WyCompany wyCompany = wyCompanyDao.queryByCode(companyCode);
		if (wyCompany == null) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"查询的物业公司不存在");
		}

		int a = wyCompanyDao.deleteWy(companyCode);
		if(a<=0){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"物业公司删除失败，请稍后再试！");
		}
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Map<String, Object> queryByCode(String companyCode) throws OssRenderException {
		Map map = wyCompanyDao.queryBywyCode(companyCode);
		if(map==null){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"查询的物业公司不存在");
		}
		return map;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Page<Map<String, Object>> queryWyList(Map<String, Object> params) throws OssRenderException {
		int totalRows = wyCompanyDao.totalRows(params);
		List<Map<String,Object>> list = Collections.emptyList();
		if (totalRows > 0) {
			PageUtil.pageParamConver(params, true);
			list = wyCompanyDao.queryWyList(params);
		}
		return new Page<>(totalRows, MapUtils.getInteger(params,PageUtil.PAGE), list);
	}
}
