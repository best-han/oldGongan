package com.windaka.suizhi.user.controller;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.controller.BaseController;
//import com.windaka.suizhi.log.api.annotation.LogAnnotation;
import com.windaka.suizhi.user.model.Menu;
import com.windaka.suizhi.user.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menus")
public class MenuController extends BaseController {

	@Autowired
	private MenuService menuService;

	/**
	 * 菜单树ztree
	 */
//	@PreAuthorize("hasAnyAuthority('back:menu:set2role','back:menu:query')")
	@GetMapping("/tree")
	public Map<String,Object> findMenuTree() {
		try {
			List<Menu> all = menuService.findAll();
			List<Menu> mainMenuList = all.stream().filter(m ->m.getPmenuId().equals("mainmain"))
					.collect(Collectors.toList());
			mainMenuList.forEach(m ->{
				setChild(m,all);
			});
			return render(mainMenuList);
		}catch (Exception e){
			return failRender(e);
		}
	}
    /**
     * 递归设置菜单树
     * @param menu
     * @param menus
     */
    private void setChild(Menu menu, List<Menu> menus) {
        List<Menu> child = menus.stream().filter(m -> m.getPmenuId().equals(menu.getMenuId()))
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(child)) {
            menu.setChild(child);
            //递归设置子元素，多级菜单支持
            child.parallelStream().forEach(c -> {
                setChild(c, menus);
            });
        }
    }

	/**
	 * 添加菜单
	 * 
	 * @param menu
	 */
	//@LogAnnotation(module = "添加菜单")
	@PreAuthorize("hasAuthority('back:menu:save')")
	@PostMapping
	public Map<String,Object> save(@RequestBody Menu menu) {
		try {
			menuService.save(menu);
			return render(menu, ReturnConstants.CODE_SUCESS,ReturnConstants.MSG_OPER_SUCCESS);
		}catch (Exception e){
			return failRender(e);
		}
	}

	/**
	 * 修改菜单
	 * 
	 * @param menu
	 */
	//@LogAnnotation(module = "修改菜单")
	@PreAuthorize("hasAuthority('back:menu:update')")
	@PutMapping
	public Map<String,Object> update(@RequestBody Menu menu) {
		try {
			menuService.update(menu);
			return render(menu, ReturnConstants.CODE_SUCESS,ReturnConstants.MSG_OPER_SUCCESS);
		}catch (Exception e){
			return failRender(e);
		}

	}

	/**
	 * 删除菜单
	 * 
	 * @param menuId
	 */
	//@LogAnnotation(module = "删除菜单")
	@PreAuthorize("hasAuthority('back:menu:delete')")
	@DeleteMapping("/{menuId}")
	public Map<String,Object> delete(@PathVariable String menuId){
	try {
		menuService.delete(menuId);
		return render();
	}catch (Exception e){
		return failRender(e);
	}
	}

	/**
	 * 查询所有菜单
	 */
	//@PreAuthorize("hasAuthority('back:menu:query')")
	@GetMapping("/all")
	public Map<String,Object> findAll() {
		try {
			List<Menu> all = menuService.findAll();
			List<Menu> list = new ArrayList<>();
			setSortTable("mainmain", all, list);
			return render(list);
		}catch (Exception e){
			return failRender(e);
		}

	}

	/**
	 * 菜单table
	 * 
	 * @param pmenuId
	 * @param all
	 * @param list
	 */
	private void setSortTable(String pmenuId, List<Menu> all, List<Menu> list) {
		all.forEach(a -> {
			if (a.getPmenuId().equals(pmenuId)) {
				list.add(a);
				setSortTable(a.getMenuId(), all, list);
			}
		});
	}

	@PreAuthorize("hasAuthority('back:menu:query')")
	@GetMapping("/{menuId}")
	public Map<String,Object> findById(@PathVariable String menuId){
	try {
		Menu menu = menuService.findById(menuId);
		return render(menu);
	}catch (Exception e){
		return failRender(e);
	}

	}

}
