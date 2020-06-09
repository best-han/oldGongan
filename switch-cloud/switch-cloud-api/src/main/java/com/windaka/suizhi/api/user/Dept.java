package com.windaka.suizhi.api.user;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 部门表 ht_dept
 * @author: hjt
 * @create: 2018-12-06 10:00
 * @version: 1.0.0
 **/
@Data
@Accessors(chain = true)
public class Dept implements Serializable {

    private static final long serialVersionUID = -3599552410785844843L;

    private String deptId;//部门自增ID
    private String deptName;//部门名称
    private String areaId;//区域ID
    private Integer delFlag;//删除标志:1存在 0删除
    private String creBy;//创建者USER_ID
    private Date creTime;//创建时间
    private String updBy;//更新者USER_ID
    private Date updTime;//更新时间
}
