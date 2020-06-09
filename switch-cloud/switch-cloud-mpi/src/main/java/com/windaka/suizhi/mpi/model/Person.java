package com.windaka.suizhi.mpi.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Person implements Serializable {

    private int id;
    private String personCode;
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
    private String imgPath;

    private String manageId;


}
