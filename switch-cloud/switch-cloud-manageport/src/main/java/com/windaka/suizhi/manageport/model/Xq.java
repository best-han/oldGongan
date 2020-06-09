package com.windaka.suizhi.manageport.model;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class Xq implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String code;		// 小区编码
	private String name;		// 小区名称
	private String addr;		// 地址
	private String memo;		// 备注
	private Double zdmj;		// 占地面积
	private Double jzmj;		// 建筑面积
	private Double ldmj;		// 绿地面积
	private Double dlmj;		// 道路面积
	private Integer lysl;		// 楼宇数量
	private String fzr;		// 负责人
	private String gsmc;		// 公司名称
	private String frdb;		// 法人代表
	private String lxr;		// 联系人
	private String lxdh;		// 联系电话
	private String lxdz;		// 联系地址
	private String ssgs;		// 所属公司
	private Double znj;		// 滞纳金比率
	private Long cqts;		// 超期天数
	private String lptype;		// 房产类型
	private Long sfatsf;		// 是否按天收费
	private String xqlxdh;		// 小区联系电话
	private String ssoRegionalcode;		// 所在区域
	private String ssoCode;			// SSO小区编码
	private String status;
	private String create_by;
	private Date create_date;
	private String update_by;
	private Date update_date;
	private String remarks;
	private String dys;       //小区总单元数
	private String hs;        //小区总户数
	private String parking;   //车位数
	private String extend_s4;
	private String extend_s5;
	private String extend_s6;
	private String extend_s7;
	private String extend_s8;
	private String location;
	private String locationArea;
	private String addrCode;


}

