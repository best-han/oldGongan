package com.windaka.suizhi.user.dao;

import com.windaka.suizhi.api.oss.sys.HtPermission;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 操作标识管理,方法级别权限
 * @author hjt
 * @version 1.0
 */
@Mapper
public interface PermissionDao {

	//@Options(useGeneratedKeys = true, keyProperty = "permissionId")
	@Insert("insert into ht_permission(permission_id,menu_id,permission, permission_name, cre_by, cre_time, upd_by, upd_time) values" +
			"(#{permissionId}, #{menuId}, #{permission}, #{permissionName}, #{creBy}, #{creTime}, #{updBy}, #{updTime})")
	int savePermission(HtPermission sysPermission);

	@Update("update ht_permission t set t.permission_name = #{permissionName}, t.permission = #{permission}, t.upd_by = #{updBy},t.upd_Time = #{updTime} where t.permission_id = #{permissionId}")
	int updatePermission(HtPermission sysPermission);

	/**
	 * 删除操作伪删除
	 * @param permissionId
	 * @return
	 */
	//@Delete("delete from ht_permission where permission_id = #{permissionId}")
	@Delete("update ht_permission set del_flag = 1 where permission_id = #{permissionId}")
	int deletePermissionById(String permissionId);

	@Select("select * from ht_permission t where t.permission_id = #{permissionId}")
    HtPermission queryById(String permissionId);

	@Select("select * from ht_permission t where t.permission = #{permission}")
    HtPermission queryByPermission(String permission);

	int totalRows(Map<String, Object> params);

	List<HtPermission> queryList(Map<String, Object> params);

}
