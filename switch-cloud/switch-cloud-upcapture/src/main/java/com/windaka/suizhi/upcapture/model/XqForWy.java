package com.windaka.suizhi.upcapture.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class XqForWy implements Serializable {
	private static final long serialVersionUID = 1L;
	private String xqCode;		// 小区编码
	private String xqName;		// 小区名称

}

