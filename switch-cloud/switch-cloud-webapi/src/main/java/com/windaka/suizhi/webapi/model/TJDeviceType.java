package com.windaka.suizhi.webapi.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 统计－设备类型Model
 * @Author pxl
 * @create: 2019-05-06 10:22
 */
@Data
public class TJDeviceType implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String deviceCode;		// 设备类型编码
    private String deviceName;		// 设备类型名称
    private String creBy;		// 创建者USER_ID
    private Date creTime;		// 创建时间
    private String updBy;		// 更新者USER_ID
    private Date updTime;		// 更新时间
}
