package com.windaka.suizhi.manageport.service.impl;

import com.windaka.suizhi.api.common.GlobalConstant;
import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.common.utils.PageUtil;
import com.windaka.suizhi.common.utils.SqlFileUtil;
import com.windaka.suizhi.manageport.dao.XqWyRelationDao;
import com.windaka.suizhi.manageport.model.WyInfo;
import com.windaka.suizhi.manageport.model.Xq;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.manageport.dao.XqDao;
import com.windaka.suizhi.manageport.service.XqService;
import com.windaka.suizhi.manageport.util.SqlCreateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
public class XqServiceImpl implements XqService {

	@Autowired
	private XqDao xqDao;
	@Autowired
	private XqWyRelationDao xqWyRelationDao;



	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveXq(Map<String,Object> params) throws OssRenderException, IOException {
 		//if (xq == null || StringUtils.isBlank(xq.getCode())) {
        if (params == null || StringUtils.isBlank((String)params.get("code"))) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区Code不能为空");
		}
		if (params == null || StringUtils.isBlank((String)params.get("name"))) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区名称不能为空");
		}
		if(xqDao.queryByCode((String)params.get("code")) != null){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区已存在");
		}
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		/*if(StringUtils.isBlank(xq.getCreate_by())){
			xq.setCreate_by(loginAppUser.getUserId());
		}
		if(xq.getCreate_date()==null){
			xq.setCreate_date(new Date());
		}
		if(StringUtils.isBlank(xq.getUpdate_by())){
			xq.setUpdate_by(loginAppUser.getUserId());
		}
		if(xq.getUpdate_date()==null){
			xq.setUpdate_date(new Date());
		}*/

		//添加小区
		xqDao.saveXq(params);
		String saveXq= SqlCreateUtil.getSqlByMybatis(XqDao.class.getName()+".saveXq",params);
		SqlFileUtil.InsertSqlToFile(saveXq);
		xqWyRelationDao.saveXqWyRelation((String)params.get("code"),(String)params.get("wyCode"));
		String saveXqWyRelation=String.format("insert into ht_xq_wy(xq_code,wy_code,status) VALUES('%s','%s','0')",params.get("code"),params.get("wyCode"));
		SqlFileUtil.InsertSqlToFile(saveXqWyRelation);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateXq(Xq xq) throws OssRenderException, IOException {
		if (xq == null || StringUtils.isBlank(xq.getCode())) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区不存在");
		}
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		if(xq.getUpdate_by()==null){
			xq.setUpdate_by(loginAppUser.getUserId());
		}
		if(xq.getUpdate_date()==null){
			xq.setUpdate_date(new Date());
		}
		xqDao.updateXq(xq);
		String updateXq=SqlCreateUtil.getSqlByBean(XqDao.class.getName()+".updateXq",xq);
		SqlFileUtil.InsertSqlToFile(updateXq);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteXq(String xqCode) throws OssRenderException, IOException {
		Xq xq = xqDao.queryByCode(xqCode);
		if (xq == null) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区不存在");
		}
		int a = xqDao.deleteXq(xqCode);
		String deleteXq="update ht_xq_info set status='1' where code ='"+xqCode+"'";
		SqlFileUtil.InsertSqlToFile(deleteXq);
		if(a<=0){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区删除失败，请稍后再试！");
		}

		xqWyRelationDao.deleteXqWyRelation(xqCode);
		String deleteXqWyRelation=" delete from ht_xq_wy where xq_code='"+xqCode+"'";
		SqlFileUtil.InsertSqlToFile(deleteXqWyRelation);

	}

	@Override
	public Map<String, Object> queryByCode(String xqCode) throws OssRenderException {
		Map map = xqDao.queryByxqCode(xqCode);
		if(map==null){
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"查询的小区不存在");
		}
		return map;
	}

	@Override
	public Page<Map<String, Object>> queryXqList(Map<String, Object> params) throws OssRenderException {
		params.put("status","0");
		int totalRows = xqDao.totalRows(params);
		List<Map<String,Object>> list = Collections.emptyList();
		if (totalRows > 0) {
			PageUtil.pageParamConver(params, true);
			list = xqDao.queryXqList(params);
			for(Map map:list){
				map.put("manageLoginUrl",map.get("url"));
			}
		}
		return new Page<>(totalRows, MapUtils.getInteger(params,PageUtil.PAGE), list);
	}
	@Override
	public List<Map<String, Object>> queryXqListForApp(Map<String, Object> params) throws OssRenderException {
		params.put("status","0");
		List<Map<String,Object>> list = xqDao.queryXqListForApp(params);
		for(Map map:list){
			map.put("manageLoginUrl",map.get("url"));
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> queryListXqByAreaId(String areaId) throws OssRenderException {
		List<Map<String,Object>> list = xqDao.queryListXqByAreaId(areaId);
		/*if (list.size() < 1) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"该区域无小区");
		}*/
		return list;
	}
	/**
	 * 根据小区Codes，获取小区对应的物业Codes(多个物业用逗号进行分隔)
	 * @param xqCodes 小区Codes
	 * @return
	 *
	 * @author pxl
	 * @create 2019-05-27 16:39
	 */
	@Override
	public String queryWYCodesByXQCodes(@Param("xqCodes") String xqCodes){
		return xqWyRelationDao.queryWYCodesByXQCodes(xqCodes);
	}

	/**
	 * 查询列表,查询统计－小区信息
	 * @param params
	 *
	 * @author pxl
	 * @create 2019-05-29 09:14
	 */
	public Map<String,Object> queryCountInfo(Map<String, Object> params) throws OssRenderException{
		String areaId = (String) params.get("areaId");
		String wyCode = (String) params.get("wyCode");
		String xqCode = (String) params.get("xqCode");
		if (StringUtils.isBlank(areaId)) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"区域不能为空");
		}
//        if (StringUtils.isBlank(wyCode)) {
//            throw new OssRenderException(ReturnConstants.CODE_FAILED,"物业编码不能为空");
//        }
//        if (StringUtils.isBlank(xqCode)) {
//            throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区编码不能为空");
//        }

		Map map = xqDao.queryCountInfo(params);
		if(map==null){
			//throw new OssRenderException(ReturnConstants.CODE_FAILED,"查询的数据不存在");
			map = new HashMap<String,Object>();
			map.put("amountSum",0);
			map.put("areaSum",0);
			map.put("lyslSum",0);
			map.put("dysSum",0);
			map.put("hsSum",0);
		}
		return map;
	}
	@Override
	public List<WyInfo> queryWyAndXqList() throws OssRenderException{
		List<WyInfo> list=xqWyRelationDao.queryWyAndXqList();
		return list;
	}
}
