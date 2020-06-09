package com.windaka.suizhi.webapi.model;

import lombok.Data;
import java.util.Date;

@Data
public class WyCompany {
	private String companyCode;
	private String companyName;
	private String parentCode;
	private String parentCodes;
	private float treeSort;
	private String treeSorts;
	private String treeLeaf;
	private float treeLevel;
	private String treeNames;
	private String viewCode;
	private String fullName;
	private String areaCode;
	private String status;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;
	private String remarks;
	private String corpCode;
	private String corpName;

}
