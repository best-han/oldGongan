package com.windaka.suizhi.webapi.model;

import lombok.Data;

import java.util.Date;

/**
 * @Description 车辆出入场记录
 * @Author wcl
 * @Date 2019/8/22 0022 下午 4:16
 */
@Data
public class CarAccessRecord {

	private Integer id;
	private String xqCode;
	private String devChnName;
	private String devChnId;
	private String devChnNum;
	private String carNum;
	private String realCapturePicPath;
	private Date capTime;
	private String carDirect;
	private String originalPicPath;

}
