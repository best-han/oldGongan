package com.windaka.suizhi.upcapture.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class FaceTypePerson implements Serializable {
	private String xqCode;
	private String personCode;
	private String faceTypeCode;
	private String create_date;
	private String create_by;
	private String update_date;
	private String update_by;
}
