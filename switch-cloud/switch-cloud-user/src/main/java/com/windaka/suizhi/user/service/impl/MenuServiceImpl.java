package com.windaka.suizhi.user.service.impl;

import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.user.dao.MenuDao;
import com.windaka.suizhi.user.model.Menu;
import com.windaka.suizhi.user.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void save(Menu menu) throws OssRenderException {
		menu.setCreTime(new Date());
		menu.setUpdTime(menu.getCreTime());

		menuDao.save(menu);
		log.info("新增菜单：{}", menu);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void update(Menu menu) throws OssRenderException{
		menu.setUpdTime(new Date());

		menuDao.update(menu);
		log.info("修改菜单：{}", menu);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void delete(String menuId) throws OssRenderException{
		Menu menu = menuDao.findById(menuId);

		menuDao.deleteByPmenuId(menuId);
		menuDao.delete(menuId);
		//roleMenuDao.delete(null, menuId);

		log.info("删除菜单：{}", menu);
	}

	@Override
	public List<Menu> findAll()	throws OssRenderException {
		return menuDao.findAll();
	}

	@Override
	public Menu findById(String menuIdd)throws OssRenderException {
		return menuDao.findById(menuIdd);
	}

}
