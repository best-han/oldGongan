package com.windaka.suizhi.manageport.model;

import lombok.Data;

import java.util.Date;

/**
 * @Description TODO
 * @Author wcl
 * @Date 2019/5/9 0009 上午 9:40
 */
@Data
public class FaceAlarmRecord {
	private Integer id ;
	private String alarmId;
	private String personCode;
	private Date alarmTime;
	private String deviceAddr;
	private String deviceCode;
	private String captureId;
	private int clStatus;
	private String status;
	private String creatBy;
	private Date creatTime;
	private String updateBy;
	private Date updateTime;
	private String remarks;
	private String clUser;
	private Date clTime;
	private String clContent;
	private String faceTypeCode;
	private int alarmLevel;
	private String contrastValue;
	private String faceImgUrl;
	private String capImgUrl;
	private String capVideoUrl;
	private String xqCode;
	private String capVUrl;
	private String capDevChannel;
	private String base64Img;
}
