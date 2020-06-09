package com.windaka.suizhi.user.dao;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户小区关系<br>
 * 用户和小区是多对多关系，ht_user_xq是中间表
 * @author hjt
 */
@Mapper
public interface UserXqDao {

	/**
	 * 删除单个用户的所有角色权限
	 * @param userId
	 * @return
	 */
/*
	@Delete("DELETE FROM ht_user_xq WHERE user_id = #{userId}")
	int deleteUserXq(@Param("userId") String userId);
*/

	/**
	 * 删除单个用户的所有绑定的小区
	 * @param userId
	 * @return
	 */
	@Update("update ht_user_xq  set status='1' WHERE user_id = #{userId}")
	int deleteUserXq(@Param("userId") String userId);

	/**
	 * 给用户分配小区
	 * @param userId
	 * @param xqCodes
	 * @return
	 */
	int saveUserXq(@Param("userId") String userId, @Param("xqCodes") Set<String> xqCodes);

	/**
	 * 删除该用户的关联的这些小区
	 * @param userId
	 * @return
	 */
	int deleteUserXqByXqCodes(@Param("userId") String userId, @Param("xqCodes") Set<String> xqCodes);

	/**
	 * 查询当下是否已存在用户和小区正常关联的信息
	 * @param userId
	 * @param xqCodes
	 * @return
	 */
	List<Map<String,Object>> queryUserXqList(@Param("userId") String userId, @Param("xqCodes") Set<String> xqCodes);

}
