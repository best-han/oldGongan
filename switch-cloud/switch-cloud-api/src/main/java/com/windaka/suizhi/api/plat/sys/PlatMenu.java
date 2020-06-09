package com.windaka.suizhi.api.plat.sys;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: hjt
 * @create: 2018-12-12 08:46
 * @version: 1.0.0
 **/
@Data
@Accessors(chain = true)
public class PlatMenu implements Serializable {
    private static final long serialVersionUID = -5432898549747461577L;

    private String menuId;//功能目录ID
    private String menuName;//功能目录名称
    private String pmenuId;//父功能目录ID：mainmain为子系统
    private String icon;//菜单图标
    private String url;//功能URL:目录URL为空
    private Integer isdir;//是否目录:0目录,1功能
    private Integer orderNum;//显示顺序
    private Integer status;//目录状态:1正常 0停用
    private String creBy;//创建者USER_ID
    private Date creTime;//创建时间
    private String updBy;//更新者USER_ID
    private Date updTime;//更新时间
}
