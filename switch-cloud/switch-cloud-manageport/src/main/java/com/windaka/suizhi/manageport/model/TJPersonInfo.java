package com.windaka.suizhi.manageport.model;

import lombok.Data;
import org.codehaus.jackson.map.deser.std.StdDeserializer;

import java.io.Serializable;
import java.util.Date;

/**
 * 统计－人员信息Model
 * @Author pxl
 * @create: 2019-05-06 10:22
 */
@Data
public class TJPersonInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String xq_code;		// 小区编码
    private Integer per_total_num;		// 小区人员总数
    private Integer owner_num;		// 业主数量
    private Integer family_member_num;		// 家庭成员数量
    private Integer tenement_num;		// 租户数量
    private Integer newper_num;		// 当日新增人员
    private String cre_by;		// 创建者USER_ID
    private Date cre_time;		// 创建时间
    private String upd_by;		// 更新者USER_ID
    private Date upd_time;		// 更新时间
}
