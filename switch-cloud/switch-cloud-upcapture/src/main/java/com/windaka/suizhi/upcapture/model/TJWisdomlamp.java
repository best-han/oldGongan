package com.windaka.suizhi.upcapture.model;

import lombok.Data;

import java.util.Date;

/**
 * 统计－智慧灯杆Model
 * @Author pxl
 * @create: 2019-05-28 16:37
 */
@Data
public class TJWisdomlamp {
    private static final long serialVersionUID = 1L;
    private String id;
    private String xq_code;		// 小区编码
    private Integer gb_device_online;		// 广播设备在线数
    private Integer gb_device_all;		// 广播设备总数
    private Integer light_device_online;		// 灯具设备在线数
    private Integer light_device_all;		// 灯具设备总数
    private Integer LED_device_online;		// LED设备在线数
    private Integer LED_device_all;		// LED设备总数
    private Integer hjjc_device_online;		// 环境监测设备在线数
    private Integer hjjc_device_all;		// 环境监测设备总数
    private String cre_by;		// 创建者USER_ID
    private Date cre_time;		// 创建时间
    private String upd_by;		// 更新者USER_ID
    private Date upd_time;		// 更新时间
}
