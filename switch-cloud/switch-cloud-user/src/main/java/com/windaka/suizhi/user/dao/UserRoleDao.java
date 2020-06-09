package com.windaka.suizhi.user.dao;

import com.windaka.suizhi.api.user.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户角色关系<br>
 * 用户和角色是多对多关系，sys_role_user是中间表
 * @author hjt
 */
@Mapper
public interface UserRoleDao {

	/**
	 * 删除单个用户的所有角色权限
	 * @param userId
	 * @return
	 */
	@Delete("DELETE FROM ht_role_user WHERE user_id = #{userId}")
	int deleteUserRole(@Param("userId") String userId);

	/**
	 * 根据角色ID，删除此觉得和所有用户的对应关系
	 * @param roleId
	 * @return
	 */
	@Delete("DELETE FROM ht_role_user WHERE role_id = #{roleId}")
	int deleteUserRoleByRoleId(@Param("roleId") String roleId);

	/**
	 * 给用户赋予角色权限
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	int saveUserRoles(@Param("userId") String userId, @Param("roleIds") Set<String> roleIds);

	/**
	 * 根据用户ID获取角色-返回实体
	 * @param userId
	 * @return
	 */
	@Select("SELECT a.* FROM ht_role a " +
			"WHERE a.del_flag=1 " +
			"AND EXISTS(SELECT 1 FROM ht_role_user b WHERE a.role_id = b.role_id AND b.user_id =#{userId})")
	Set<Role> queryRolesByUserIdForRole(String userId);

    /**
     * 根据用户ID获取角色-返回List<Map>
     * @param userId
     * @return
     */
    @Select("SELECT a.role_id roleId,a.role_name roleName\n" +
			" FROM (SELECT * FROM ht_role WHERE del_flag = 1 ) a\n" +
			" INNER JOIN (SELECT role_id FROM ht_role_user WHERE user_id = #{userId}) b\n" +
			" ON a.role_id = b.role_id\n" +
			" ORDER BY a.id")
    List<Map<String,Object>> queryRolesByUserId(@Param("userId") String userId);
    
    /**
     * 根据用户ID获取小区code
     * @param userId
     * @return
     */
    @Select("select xq_code from ht_user_xq where user_id= #{userId} and status =0")
    List<String> queryXqCodeByUserId(@Param("userId") String userId);

	/**
	 * 根据userId查询其角色
	 * @param userId
	 * @return
	 */
/*	@Select("SELECT a.role_id roleId,a.role_name roleName\n" +
			" FROM ht_role a \n" +
			" INNER JOIN  ht_role_user b ON a.role_id = b.role_id\n"  +
			" and b.user_id = #{userId} \n" +
			" WHERE a.del_flag = 1 ")
	Map<String,Object> queryRoleByUserId(String userId);*/

    /**
    * @Author: hjt
    * @Description: 根据角色查询用户
    */
	@Select("SELECT user_id userId,role_id roleId FROM ht_role_user WHERE role_id = #{roleId} ")
	List<Map<String,Object>> queryUserByRoleId(String userId);
}
