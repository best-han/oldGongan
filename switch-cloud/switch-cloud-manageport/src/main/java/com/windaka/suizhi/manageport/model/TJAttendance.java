package com.windaka.suizhi.manageport.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 统计－考勤管理Model
 * @Author pxl
 * @create: 2019-05-06 10:22
 */
@Data
public class TJAttendance implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String xq_code;		// 小区编码
    private Integer per_total;		// 小区全部员工数
    private Integer per_duty;		// 到岗员工数
    private Integer normal;		// 正常考勤数
    private Integer late;  //迟到人员数量
    private Integer leave;  //未到岗人员
    private String cre_by;		// 创建者USER_ID
    private Date cre_time;		// 创建时间
    private String upd_by;		// 更新者USER_ID
    private Date upd_time;		// 更新时间
}
