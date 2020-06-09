package com.windaka.suizhi.webapi.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 统计－缴费信息Model
 * @Author pxl
 * @create: 2019-05-06 10:22
 */
@Data
public class TJPay implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String xq_code;		// 小区编码
    private Integer month;		// 月份
    private Double pay_amount;		// 缴费额
    private Double finishing_rate;		// 完成率
    private Double today_pay;  //当日缴费额
    private Double current_month_pay;  //当月累计完成缴费额
    private String cre_by;		// 创建者USER_ID
    private Date cre_time;		// 创建时间
    private String upd_by;		// 更新者USER_ID
    private Date upd_time;		// 更新时间
}
