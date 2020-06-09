package com.windaka.suizhi.api.plat.sys;

import lombok.Data;

import java.util.Date;

/**
 * 平台子系统
 * pt_menu表中pmenu_id=mainmain
 * @author: hjt
 */
@Data
public class PlatApp {
    private String appId;
    private String appName;
    private Integer status;
    private Date creTime;
    private Date updTime;
}
