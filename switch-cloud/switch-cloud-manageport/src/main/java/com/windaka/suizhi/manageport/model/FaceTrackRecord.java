package com.windaka.suizhi.manageport.model;

import lombok.Data;

import java.util.Date;

/**
 * @Description TODO
 * @Author wcl
 * @Date 2019/5/9 0009 上午 9:41
 */
@Data
public class FaceTrackRecord {
	private int id ;
	private String xqCode;
	private String faceImgUrl;
	private Date capImgUrl;
	private String capVideoUrl;
	private String deviceName;
	private String personCode;
	private Date capTime;
	private String deviceAddr;
	private String deviceLocation;
	private String captureId;
	private int delFlag;
	private String creatBy;
	private Date creatTime;
	private String updateBy;
	private Date updateTime;
	private String contrastValue;
	private String capVUrl;
	private String capDevChannel;
	private String capDevCode;


}
