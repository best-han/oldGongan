package com.windaka.suizhi.api.user;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 区域信息实体
 * @author: hjt
 */
@Data
//开启链式调用
@Accessors(chain = true)
public class Area implements Serializable {
    private static final long serialVersionUID = 7443655181690659169L;
    //区域自增ID
    private String areaId;
    //区域名称
    private String areaName;
    //联系电话
    private String phone;
    //代理状态:1正常 0停止
    private Integer status;
    //删除标志:1存在 0删除
    private Integer delFlag;
    //创建者USER_ID
    private String creBy;
    //创建时间
    private Date creTime;
    //更新者USER_ID
    private String updBy;
    //更新时间
    private Date updTime;

    //物业编码
    private String wyCode;
    //小区编码
    private String xqCode;

}
