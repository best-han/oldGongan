package com.windaka.suizhi.webapi.model;

import lombok.Data;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
@Data
public class Person implements Serializable {
    private String code;
    private String name;
    private String sex;
    private String birthday;
    private String paperType;

    private String paperNumber;
    private String nation;

    private String orgionPlace;
    private String marriageStatus;
    private String education;
    private String politicalStatus;

    private String company;
    private String phone;
    private String email;
    private String address;

    private String title;

    private String technicalDegree;

    private String personIdentityName;
    private String personIdentityId;
    private String personStatus;

    private String status;
    private String remarks;

    private String createDate;
    private String createBy;
    private String updateDate;
    private String updateBy;
    private String validFlag;
    private String occupation;
    private String tCode;
    private String extendS1;
    private String extendS2;
    private String extendS3;
    private String extendS4;
    private String extendS5;
    private String extendS6;
    private String extendS7;
    private String extendS8;
    private String extendI1;
    private String extendI2;
    private String extendI3;
    private String extendI4;
    private String extendF1;
    private String extendF2;
    private String extendF3;
    private String extendF4;
    private String extendD1;
    private String extendD2;
    private String extendD3;
    private String extendD4;


}
