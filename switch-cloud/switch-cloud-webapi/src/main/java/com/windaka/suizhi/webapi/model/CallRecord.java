package com.windaka.suizhi.webapi.model;

import lombok.Data;

import java.util.Date;

/**
 * @Description 通话记录
 * @Author wcl
 * @Date 2019/8/21 0021 上午 9:53
 */
@Data
public class CallRecord {

	private Integer id;
	private String xqCode;
	private String deviceName;
	private String deviceCode;
	private String roomNum;
	private String status;
	private Date callTime;
	private Integer talkTime;
	private String endState;

}
