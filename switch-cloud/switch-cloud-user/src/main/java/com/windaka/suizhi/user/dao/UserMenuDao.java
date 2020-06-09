package com.windaka.suizhi.user.dao;

import com.windaka.suizhi.api.oss.sys.HtMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: hjt
 * @Date: 2018/12/10 10:24
 * @Version 1.0
 */
@Mapper
public interface UserMenuDao {

    /**
     * 设置区域管理员OSS功能权限
     * @param userId
     * @return

    @Insert("INSERT INTO ht_user_menu(user_id,menu_id) " +
            "SELECT #{userId}, menu_id FROM ht_menu t WHERE t.`status`=1 AND t.del_flag=1 AND t.qd_sign='N'")
    int saveAreaManagerMenu(String userId);
     */
    /**
     * 删除用户OSS功能权限
     * @param userId
     * @return

    @Delete("DELETE FROM ht_user_menu WHERE user_id = #{userId}")
    int deleteUserMenu(String userId);
     */
    /**
    * @Author: hjt
    * @Description:  获取当前用户角色的菜单-普通用户
    */
    @Select("SELECT menu_id AS menuId  ,pmenu_id AS pmenuId   ,menu_name AS menuName," +
            " icon, isdir AS isDir, url , status, order_num AS orderNum" +
            ",cre_time AS creTime,upd_time AS updTime " +
            " FROM ht_menu a " +
            " WHERE a.del_flag = 1 AND a.status = 1 " +
            "   AND EXISTS(SELECT 1 FROM " +
            " (SELECT m.menu_id FROM ht_role_menu m " +
            " WHERE EXISTS(SELECT 1 FROM ht_role_user n WHERE m.role_id = n.role_id AND n.user_id = #{userId})) b " +
            " WHERE a.menu_id = b.menu_id) " +
            " ORDER BY order_num")
    List<HtMenu> queryUserRoleMenus(String userId);

    /**
    * @Author: hjt
    * @Description: 获取区域管理员的菜单
    */
//    @Select("SELECT menu_id AS menuId  ,pmenu_id AS pmenuId   ,menu_name AS menuName," +
//            "icon, isdir AS isDir, url , status, order_num AS orderNum" +
//            ",cre_time AS creTime,upd_time AS updTime" +
//            " FROM ht_menu a " +
//            " WHERE a.del_flag = 1 AND a.status = 1 " +
//            "   AND EXISTS(SELECT 1 FROM ht_user_menu b WHERE a.menu_id = b.menu_id AND b.user_id = #{userId}) " +
//            "ORDER BY order_num"order_num)
    List<HtMenu> queryAdminMenus();
    /**
    * @Author: hjt
    * @Date: 2018/12/21
    * @Description: userId查询此用户分配的menu菜单
    */
    Set<Map<String,Object>> queryAdminMenusForMap();
}
