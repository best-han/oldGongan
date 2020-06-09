package com.windaka.suizhi.mpi.model;

import lombok.Data;

import java.util.Date;

@Data
public class CarInOut {
	private int id;
	// 车牌编号
	private String carNum;
	private String xqName;
	private String xqCode;
	private String time;
	private String img;
	// 抓拍图片
	private String captureImg;
	private String timeStamp;
	// 抓拍时间
	private Date captureTime;
	// 抓拍时间字符串
	private String captureTimeStr;
	// 行政区划编号
	private String regionId;
	// 厂商原始设备编号
	private String devId;
	// 设备名称
	private String devName;
	// 库名称
	private String groupName;
	// 报警等级
	private String event;
	// 出现地点
	private String position;
	// 车牌颜色
	private String plateColor;
	// 证件号码
	private String idCard;

	

}
