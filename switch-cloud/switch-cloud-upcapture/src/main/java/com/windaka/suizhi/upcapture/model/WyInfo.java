package com.windaka.suizhi.upcapture.model;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class WyInfo {
	private static final long serialVersionUID = 1L;
	private String wyId;
	private String wyCode;
	private String wyName;
	private String parentCode;
	private String delFlag;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;
	private List<Xq> xqs;
}
