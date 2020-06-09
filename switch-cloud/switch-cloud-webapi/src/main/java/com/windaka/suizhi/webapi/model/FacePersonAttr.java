package com.windaka.suizhi.webapi.model;

import lombok.Data;

import java.util.Date;

@Data
public class FacePersonAttr {
    /** 自增主键ID **/
    private Long id;

    /** 抓拍时间 **/
    private Date captureTime;
    
    private String captureTimeStr;

    /** 抓拍设备ID **/
    private String captureDeviceId;

    /** 抓拍图像 **/
    private String captureImg;

    /** 人脸ID **/
    private String personId;
    
    private String xqName;

    /** 姓名 **/
    private String name;
    
    private int times;

    /** 性别 （数据字典 sys_user_sex 1男 2女） **/
    private Short sex;

    /** 人员类型（1正常人 2陌生人 3没有人脸图像） **/
    private Short type;

    /** 年龄 **/
    private String age;

    /** 国别 （数据字典 ） **/
    private Short country;

    /** 口罩 （数据字典 sys_have_no） **/
    private Integer bmask;

    /** 眼镜 （数据字典 sys_have_no） **/
    private Integer eyeglass;

    /** 帽子 （数据字典 sys_have_no） **/
    private Integer hat;

    /** 背包 （数据字典 sys_have_no） **/
    private Integer knapsack;

    /** 手提袋 （数据字典 sys_have_no） **/
    private Integer handbag;

    /** 上衣种类 （数据字典 ） **/
    private Integer coatType;

    /** 上衣颜色 （数据字典 ） **/
    private Integer coatColor;
    
    private String identityName;

    /** 裤子种类 （数据字典 ） **/
    private Integer trousersType;

    /** 裤子颜色 （数据字典 ） **/
    private Integer trousersColor;

    /** 人脸相似度 **/
    private Double similar;

    /** 小区编码 **/
    private String xqCode;
    
    /** 管理端主键（"小区编码_+管理端主键"） **/
    private String manageId;

    /** 图片地址(ftp) **/
    private String base64Img;
    
    private String img;
    
    private String typeName;

}