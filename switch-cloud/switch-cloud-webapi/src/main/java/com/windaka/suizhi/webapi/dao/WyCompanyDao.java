package com.windaka.suizhi.webapi.dao;

import com.windaka.suizhi.webapi.model.WyCompany;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface WyCompanyDao {
	/**
	 * 新增物业公司
	 * @param wyCompany
	 * @return
	 */
	int saveWy(WyCompany wyCompany);

	/**
	 * 修改物业公司
	 * @param wyCompany
	 * @return
	 */
	int updateWy(WyCompany wyCompany);
	/**
	 * 删除物业公司
	 * @param companyCode
	 * @return
	 */
	@Update("update ht_company set status=1 where company_code=#{companyCode}")
	int deleteWy(@Param("companyCode") String companyCode);

	/**
	 * 根据Code查询物业公司
	 * @param companyCode
	 * @return
	 */
	@Select("SELECT * FROM ht_company t WHERE t.status=0 AND t.company_code=#{companyCode}")
	WyCompany queryByCode(@Param("companyCode") String companyCode);

	/**
	 * 根据Code查询物业公司
	 * @param companyCode
	 * @return
	 */
	@Select("SELECT * FROM ht_company t WHERE t.status=0 AND t.company_code=#{companyCode}")
	Map<String,Object> queryBywyCode(@Param("companyCode") String companyCode);

	/**
	 * 查询物业公司列表总条数
	 * @param params
	 * @return
	 */
	int totalRows(@Param("params") Map<String, Object> params);

	/**
	 * 查询物业公司列表
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryWyList(@Param("params") Map<String, Object> params);
}
