package com.windaka.suizhi.manageport.dao;

import com.windaka.suizhi.manageport.model.Xq;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;


@Mapper
public interface XqDao {
	/**
	 * 新增小区
	 * @param xq
	 * @return
	 */
	//int saveXq(Xq xq);
	int saveXq(Map<String, Object> params);

	/**
	 * 修改小区
	 * @param xq
	 * @return
	 */
	int updateXq(Xq xq);
	/**
	 * 小区删除
	 * @param xqCode
	 * @return
	 */
	@Update("update ht_xq_info set status=1 where code = #{xqCode}")
	int deleteXq(@Param("xqCode") String xqCode);

	/**
	 * 根据Code查询小区
	 * @param xqCode
	 * @return
	 */
	@Select("SELECT * FROM ht_xq_info t WHERE t.status =0 AND t.code = #{xqCode}")
	Xq queryByCode(@Param("xqCode") String xqCode);

	/**
	 * 根据Code查询小区
	 * @param xqCode
	 * @return
	 */
	Map<String,Object> queryByxqCode(@Param("xqCode") String xqCode);

	/**
	 * 查询小区列表总条数
	 * @param params
	 * @return
	 */
	int totalRows(@Param("params") Map<String, Object> params);

	/**
	 * 查询小区列表
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryXqList(@Param("params") Map<String, Object> params);

	/**
	 * 小区查询（列表查询）安保端app使用
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryXqListForApp(@Param("params") Map<String, Object> params);

	/**
	 * 根据areaId查询小区列表
	 * @param
	 * @return
	 */
	List<Map<String,Object>> queryListXqByAreaId(String areaId);

	/**
	 * 查询统计－小区信息
	 * @param params
	 * @return
	 *
	 * @author pxl
	 * @create 2019-05-28 16:37
	 */
	Map<String,Object> queryCountInfo(@Param("params") Map<String, Object> params);

}
