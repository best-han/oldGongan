package com.windaka.suizhi.webapi.model;

import lombok.Data;

import java.util.Date;

/**
 * @Description 开门记录
 * @Author wcl
 * @Date 2019/8/20 0020 下午 5:35
 */
@Data
public class OpenRecord {

	private Integer id;
	private String xqCode;
	private String deviceName;
	private String deviceCode;
	private String openType;
	private String roomNum;
	private String swipeId;
	private String swipeName;
	private String cardNumber;
	private String cardType;
	private String openResult;
	private Date swipeTime;
	private Date operateTime;

}
