package com.windaka.suizhi.upcapture.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 统计－信息发布Model
 * @Author pxl
 * @create: 2019-05-29 10:28
 */
@Data
public class TJInfoPub implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String xq_code;		// 小区编码
    private Integer month;		// 月份(1-12)
    private Integer info_count;		// 该月的信息发布数量
    private Date oper_date;  //上传操作的时间（yyyy-MM-dd）
    private String cre_by;		// 创建者USER_ID
    private Date cre_time;		// 创建时间
    private String upd_by;		// 更新者USER_ID
    private Date upd_time;		// 更新时间
}
