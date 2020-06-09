package com.windaka.suizhi.user.dao;

import com.windaka.suizhi.user.model.Menu;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MenuDao {

	@Insert("insert into ht_menu(pmenu_id, menu_name, isdir, url, order_num, status, cre_time, upd_time) "
			+ "values (#{pmenuId}, #{menuName}, #{isDir}, #{url}, #{orderNum}, #{status}, #{creTime}, #{updTime})")
	int save(Menu menu);

	int update(Menu menu);

	@Select("select * from ht_menu t where t.menu_id = #{menuId}")
	Menu findById(String menuId);

	//@Delete("delete from ht_menu where menu_id = #{menuId}")
	@Update("update ht_menu set del_flag=0 where menu_id=#{menuId}")
	int delete(String menuId);

	//@Delete("delete from ht_menu where pmenu_id = #{pmenuId}")
	@Update("update ht_menu set del_flag=0 where pmenu_id=#{pmenuId}")
	int deleteByPmenuId(String pmenuId);

	@Select("select menu_id menuId,menu_name menuName,pmenu_id pmenuId,url,isDir,order_num orderNum,status,cre_time creTime,upd_time updTime\n" +
			" from ht_menu t where del_flag=1 order by t.pmenu_id,t.order_num")
	List<Menu> findAll();
}
