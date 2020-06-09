package com.windaka.suizhi.api.oss;


import lombok.Getter;

/**
 * 运营端超级管理员区域信息
 * @author: hjt
 * @Date: 2019/3/6 14:13
 * @Version 1.0
 */
@Getter
public enum OssAdmin {

    //区域ID为99999999999999999999999999999999代表为系统超级管理员，不是一个真实的区域
    AREA_ID("99999999999999999999999999999999","运营端超级管理员的区域");

    private String areaId;

    private String note;

    OssAdmin(String areaId, String note){
        this.areaId = areaId;
        this.note = note;
    }
}
