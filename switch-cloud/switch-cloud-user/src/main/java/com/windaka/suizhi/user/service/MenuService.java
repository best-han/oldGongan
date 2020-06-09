package com.windaka.suizhi.user.service;

import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.user.model.Menu;

import java.util.List;

public interface MenuService {

	/**
	 * 保存功能目录
	 * @param menu
	 */
	void save(Menu menu) throws OssRenderException;

	/**
	 * 修改功能目录
	 * @param menu
	 */
	void update(Menu menu)throws OssRenderException;

	/**
	 * 删除功能目录
	 * 注意：此处为伪删除，将数据库表中的删除标志置为删除即可，具体删除标志根据数据库设计规则
	 * @param menuId
	 */
	void delete(String menuId)throws OssRenderException;


	/**
	 * 查询全部功能目录（未删除）
	 * @return
	 */
	List<Menu> findAll()throws OssRenderException;

	/**
	 * 根据功能目录id查询目录信息
	 * @param menuId
	 * @return
	 */
	Menu findById(String menuId)throws OssRenderException;


}
