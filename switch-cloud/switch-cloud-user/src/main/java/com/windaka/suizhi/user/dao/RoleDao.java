package com.windaka.suizhi.user.dao;

import com.windaka.suizhi.api.oss.sys.HtMenu;
import com.windaka.suizhi.api.user.Role;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;

/**
 * OSS角色管理
 * 所有增删改查都只能是本区域的，并且是未删除的
 * @author hjt
 */
@Mapper
public interface RoleDao {

	/**
	 * 新增角色
	 * @param role
	 * @return
	 */
	@Insert("INSERT INTO ht_role(role_id, role_name, description, cre_by, cre_time, upd_by, upd_time) " +
			" VALUES (#{roleId}, #{roleName}, #{description}, #{creBy}, now(), #{updBy}, now())")
	int saveRole(Role role);

	/**
	 * 修改角色
	 * @param role
	 * @return
	 */
	@Update("UPDATE ht_role t SET t.role_name = #{roleName} ,t.description = #{description} ,t.upd_by = #{updBy}, t.upd_Time = #{updTime} " +
			" WHERE t.role_id = #{roleId}")
	int updateRole(Role role);

	/**
	 * 查询角色
	 * @param roleId
	 * @return
	 */
	@Select("SELECT * FROM ht_role t WHERE t.del_flag = 1 AND t.role_id = #{roleId}")
	Role queryById(@Param("roleId") String roleId);

	/**
	 * 查询角色
	 * @param roleId
	 * @return
	 */
	@Select("SELECT t.role_id roleId,t.role_name roleName,date_format(t.cre_time,'%Y-%m-%d %H:%i:%s') creTime FROM ht_role t WHERE t.del_flag = 1 AND t.role_id = #{roleId}")
	Map<String,Object> queryByRoleId(@Param("roleId") String roleId);

    /**
     * 查询角色
     * @param roleName
     * @return
     */
    @Select("SELECT t.role_id roleId,t.role_name roleName,date_format(t.cre_time,'%Y-%m-%d %H:%i:%s') creTime FROM ht_role t WHERE t.del_flag = 1 AND t.role_name = #{roleName}")
    Map<String,Object> queryByRoleName(@Param("roleName") String roleName);

	/**
	 * 删除角色-伪删除
	 * @param roleId
	 * @return
	 */
	@Update("UPDATE ht_role t SET del_flag=0, t.upd_by = #{updBy}, t.upd_Time = now() WHERE role_id = #{roleId}")
	int deleteRole(String roleId);

	/**
	* @Author: hjt
	* @Date: 2018/12/21
	* @Description: 角色列表总条数
	*/ 
	int totalRows(@Param("params") Map<String, Object> params);
	/**
	* @Author: hjt
	* @Date: 2018/12/21
	* @Description: 角色列表
	*/
	List<Map<String,Object>> queryRoleList(@Param("params") Map<String, Object> params);

	/**
	 * 查询最大ID
	 * @param
	 * @return
	 */
	@Select("select MAX(id) from ht_role t")
	String maxId();

	/**
	 * 查询menu_id是否已存在
	 * @param
	 * @return
	 */
	@Select("SELECT COUNT(*) FROM ht_role_menu_permission WHERE menu_id=#{menuId}")
	int menuId(@Param("menuId") String menuId);


	/**
	 * 查询permission_id是否已存在
	 * @param
	 * @return
	 */
	@Select("SELECT COUNT(*) FROM ht_role_menu_permission WHERE permission_id=#{permissionId}")
	int permissionId(@Param("permissionId") String permissionId);

	/**
	* @Description: 菜单管理
	* @Param: [roleId, menuIds]
	* @return: int
	* @Author: Ms.wcl
	* @Date: 2019/4/26
	*/
	@Insert("INSERT INTO ht_role_menu_permission(role_id,menu_id) VALUES (#{roleId},#{menuId})")
	int saveRoleMenu(@Param("roleId") String roleId, @Param("menuId") String menuId);

	/**
	 * @Description: 按钮管理
	 * @Param: [roleId, permissionIds]
	 * @return: int
	 * @Author: Ms.wcl
	 * @Date: 2019/4/26
	 */
	@Insert("INSERT INTO ht_role_menu_permission(role_id,permission_id) VALUES (#{roleId},#{permissionIds})")
	int saveRolePermission(@Param("roleId") String roleId, @Param("permissionIds") String permissionIds);

	/**
	 * 根据ID查询权限菜单
	 * @param
	 * @return
	 */
	@Select("select menu_id,menu_name,status from ht_menu where menu_id=(select menu_id from ht_role_menu where role_id=#{roleId})")
	Map<String,Object> queryMenu(@Param("roleId") String roleId);

	/**
	 * 根据角色ID，查询权限菜单列表
	 * @param
	 * @return
	 *
	 * @author pxl
	 * @create: 2019-05-08 11:47
	 */
	@Select("select menu_id as menuId,menu_name as menuName,pmenu_id as pmenuId,case status when true then 1 else 0 end status" +
			" from ht_menu where menu_id in (select menu_id from ht_role_menu_permission where role_id=#{roleId})")
	List<Map<String,Object>> queryMenuList(@Param("roleId") String roleId);

	/**
	 * 根据ID查询权限菜单list的perList
	 * @param
	 * @return
	 */
	@Select("select menu_id as menuId,permission_id as permissionId,permission_name as permissionName,case status when true then 1 else 0 end status from ht_permission " +
			"where menu_id in (select menu_id from ht_role_menu_permission where role_id=#{roleId})")
	List<Map<String,Object>> queryPerList(@Param("roleId") String roleId);

	/**
	 * 根据ID查询权限菜单的子菜单
	 * @param
	 * @return
	 */
	@Select("select menu_id,menu_name,status from ht_menu where pmenu_id=(select menu_id from ht_role_menu_permission where role_id=#{roleId})")
	List<Map<String,Object>> queryChildMenu(@Param("roleId") String roleId);

	/**
	 * 根据ID查询权限菜单的子菜单
	 * @param
	 * @return
	 */
	@Select("select menu_id,menu_name,status from ht_menu where pmenu_id=(select menu_id from ht_role_menu where role_id=#{roleId})")
	List<HtMenu> queryChildMe(@Param("roleId") String roleId);

	/**
	 * 根据ID查询权限菜单list的perList
	 * @param
	 * @return
	 */
	@Select("select permission_id,permission_name,status from ht_permission where menu_id=(select menu_id from ht_role_menu where role_id=#{roleId})")
	List<Map<String,Object>> queryMenuPer(@Param("roleId") String roleId);

	/**
	 * 根据ID查询权限菜单childMenu的perList
	 * @param
	 * @return
	 */
	@Select("select permission_id,permission_name,status from ht_permission where menu_id=#{childMenuId}")
	List<Map<String,Object>> queryChildMenuPer(@Param("childMenuId") String childMenuId);


}
