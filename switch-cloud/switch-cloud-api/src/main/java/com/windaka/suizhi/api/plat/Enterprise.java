package com.windaka.suizhi.api.plat;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description:企业信息实体
 * @author: hjt
 * @create: 2018-12-03
 * @version 1.0
 *
 */
@Data
public class Enterprise implements Serializable {
    private static final long serialVersionUID = 7443655181690659169L;
    private String entId;//企业ID
    private String pentId;//上级企业ID，分公司时不为空
    private String hentId;//顶级父企业ID：集团公司时候=企业ID，分公司为集团总公司ID
    private String shxydm;//社会信用代码(纳税人识别号)
    private String nsrmc;//纳税人名称
    private String entPhone;//注册手机号
    private String sbcyfs;//设备持有方式
    private String bank;//开户银行
    private String bankNum;//开户银行账号
    private String linkMan;//联系人
    private String linkPhone;//联系电话
    private String enttypeDm;//企业类型代码：01代理公司 02普通公司 03集团公司
    private String status;//状态：00待审核 01正常 90注销
    private String addr;//地址
    private String remark;//备注
    private String areaId;//OSS注册区域ID
    private String creBy;//OSS创建者
    private Date creTime;//OSS创建时间
    private String updBy;//OSS更新者
    private Date updTime;//OSS更新时间
    private String statusMc;//状态名称
    private String creByCname;//创建用户姓名
    private String  updByCname;//修改用户姓名
    private String  enttype;//企业类型
    private String  userId;//管理员用户ID
    private String  username;//管理员登录账号
    private String areaName;//区域名称
    private String jxsjxzly;//进项数据下载来源：01云税慧商 02税务局 03云税慧商和税务局
    private List<Enterprise> childEnt;//分公司
    private List<Map<String,Object>> kplxList;//开票类型


}

