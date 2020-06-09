package com.windaka.suizhi.webapi.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 统计－设备管理Model
 * @Author pxl
 * @create: 2019-05-06 10:22
 */
@Data
public class TJDeviceTotal implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String xq_code;		// 小区编码
    private String device_code;		// 设备编码
    private Integer total_device;		// 设备总数
    private Integer online;		// 在线设备数
    private String cre_by;		// 创建者USER_ID
    private Date cre_time;		// 创建时间
    private String upd_by;		// 更新者USER_ID
    private Date upd_time;		// 更新时间
}
