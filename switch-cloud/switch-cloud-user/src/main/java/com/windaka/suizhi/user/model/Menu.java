package com.windaka.suizhi.user.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Menu implements Serializable {

	private static final long serialVersionUID = 749360940290141180L;

	private String menuId;		//菜单id
	private String pmenuId;		//父菜单id
	private String menuName; 	//菜单名称
	private Integer isDir;		//是否是目录
	private String url;			//连接地址
	private Integer status;		//目录状态
	private Integer orderNum;	//排序号
	private Date creTime;		//创建时间
	private Date updTime;		//修改时间

	private List<Menu> child;
}
