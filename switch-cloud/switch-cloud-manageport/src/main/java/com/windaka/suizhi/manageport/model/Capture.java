package com.windaka.suizhi.manageport.model;

import lombok.Data;
import java.util.Date;

/**
 * @Description TODO
 * @Author wcl
 * @Date 2019/7/16 0016 上午 11:54
 */
@Data
public class Capture {
	private Integer id ;
	private String CaptureId;
	private String xqCode;
	private String deviceAddr;
	private String deviceName;
	private String deviceLocation;
	private String capDevCode;
	private String capDevChannel;
	private String status;  //设备状态（0：不在线；1：在线）
	private String creatBy;
	private Date creatTime;
	private String updateBy;
	private Date updateTime;
	private String dchnlRtsp;
}
