package com.windaka.suizhi.manageport.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 统计－车辆信息Model
 * @Author wcl
 * @create: 2019-05-30
 */
@Data
public class TJCarInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String xq_code;		// 小区编码
    private Integer timing;		// 时间点（按小时计1-24）
    private Integer in;		// 进门人数
    private Integer out;		// 出门人数
    private String cre_by;		// 创建者USER_ID
    private Date cre_time;		// 创建时间
    private String upd_by;		// 更新者USER_ID
    private Date upd_time;		// 更新时间
    private Date operDate;      //操作时间
}
