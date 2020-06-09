package com.windaka.suizhi.manageport.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class FaceTypePerson implements Serializable {
	private String xqCode;
	private String personId;
	private String faceTypeCode;
	private String createDate;
	private String createBy;
	private String updateDate;
	private String updateBy;

}
