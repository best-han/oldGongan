package com.windaka.suizhi.manageport.model;

import lombok.Data;

import java.util.Date;

/**
 * @Description 车辆信息
 * @Author wcl
 * @Date 2019/8/20 0020 上午 11:15
 */
@Data
public class CarInfo {

	private Integer id;
	private String xqCode;
	private String manageId;
	private String carCode;
	private String carNum;
	private String carNumColor;
	private String carType;
	private String carBrand;
	private String carColor;
	private String carMark;
	private String carStatus;
	private String carPic;
	private String ownerId;
	private String memo;
	private Integer parkType;
	private String parkinglotCode;
	private Integer listType;
	private String status;
	private String creatBy;
	private Date creatDate;
	private String updateBy;
	private Date updateDate;
	private String remarks;
	private String parkingLotName;
	private String roomId;
	private String carImg;

}
