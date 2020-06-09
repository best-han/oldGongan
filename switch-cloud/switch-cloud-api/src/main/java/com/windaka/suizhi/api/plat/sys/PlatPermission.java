package com.windaka.suizhi.api.plat.sys;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: hjt
 * @create: 2018-12-12 08:51
 * @version: 1.0.0
 **/
@Data
@Accessors(chain = true)
public class PlatPermission implements Serializable {
    private static final long serialVersionUID = 8695418996327692637L;

    private String permissionId;//操作标识ID
    private String menuId;//功能目录ID
    private String permission;//操作标识
    private String permissionName;//操作标识名称
    private Integer flowSign;//流量包标志：1需要购买
    private String creBy;//创建者USER_ID
    private Date creTime;//创建时间
    private String updBy;//更新者USER_ID
    private Date updTime;//更新时间
    private Integer status;//目录状态:1正常 0停用
}
