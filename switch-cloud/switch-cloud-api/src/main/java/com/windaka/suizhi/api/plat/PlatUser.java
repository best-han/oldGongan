package com.windaka.suizhi.api.plat;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @program: sjwl-api
 * @description: 企业管理员实体类
 * @author: hjt
 * @create: 2018-12-04 16:05
 * @version 1.0
 */
@Data
public class PlatUser implements Serializable {
    private static final long serialVersionUID = 7443655181690659169L;
    private String userId;//用户ID
    private String entId;//企业ID
    private String username;//登录账号
    private String password;//登录密码
    private String userType;//账号类别: 01企业管理员(航信维护） 02部门管理员(企业管理员维护) 03普通账号(会员账号维护)
    private Integer enabled;//帐号状态:1正常 0注销
    private Integer delFlag;//删除标志:1存在 0删除
    private String loginIp;//最后登陆IP
    private Date loginTime;//最后登陆时间
    private String creBy;//创建者
    private Date creTime;//创建时间
    private String updBy;//更新者
    private Date updTime;//更新时间

}
