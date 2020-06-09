package com.windaka.suizhi.mpi.model;

import lombok.Data;

import java.util.Date;

@Data
public class RecordAbnormal  {
	private int id;
	private Date captureTime;		// 抓拍时间
	private String captureTimeStr;
	private String time;
	private String captureDeviceId;		// 抓拍设备ID
	private String img;		// 抓拍图像
	private String captureImgSmall;		// 抓拍图像(缩略图)
	private String personId;		// 人员id
	private String personName;		// 人员姓名
	private String carNum;		// 车牌号
	private String event;		// 事件 （数据字典 recognition_event）
	private String eventName;
	private Date beginTime;		// 开始时间
	private Date endTime;		// 结束时间
	private String  alarmLevel; //告警级别（数据字典 recognition_level）
	private String handleUser;//处理人
	private Date handleTime;//处理时间
	private String handleConn;//处理内容
	private Integer handleStatus;//处理状态（0未处理  1已处理）
	private String handleImage1; //处理图片1
	private String handleImage2; //处理图片2
	private String boxXyxy; //截取照片点坐标
	private String location; //摄像头位置
	private String libraryImg;//人脸库照片
	private String similar; //相似度
	private String timeStamp;
	private String type; // 车辆库编码
	private String groupName;
	private String xqName;
	private String xqCode;
	private String devId;
	private String devName;
	private String captureImg;



}