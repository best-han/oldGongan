package com.windaka.suizhi.manageport.dao;

import com.windaka.suizhi.manageport.model.WyInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface WyInfoDao {
	/**
	 * 新增物业公司
	 * @param wyInfo
	 * @return
	 */
	@Insert("insert into ht_wy_info(wy_code,wy_name,parent_code,del_flag,cre_by,cre_time,upd_by,upd_time) values(#{wyCode},#{wyName},#{parentCode},'1',#{createBy},#{createDate},#{updateBy},#{updateDate})")
	int saveWyInfo(WyInfo wyInfo);

	/**
	 * 修改物业公司
	 * @param wyInfo
	 * @return
	 */
	@Update("update ht_wy_info set wy_name=#{wyName},parent_code=#{parentCode},cre_by=#{createBy},cre_time=#{createDate},upd_by=#{updateBy},upd_time=#{updateDate}  where wy_code=#{wyCode}")
	int updateWyInfo(WyInfo wyInfo);
	/**
	 * 删除物业公司
	 * @param companyCode
	 * @return
	 */
	@Update("update ht_wy_info set del_flag='0' where wy_code=#{companyCode}")
	int deleteWyInfo(@Param("companyCode") String companyCode);

}
