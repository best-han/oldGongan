package com.windaka.suizhi.mpi.model;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName CarAccessDto
 * @Description 车辆传输对象
 * @Author lixianhua
 * @Date 2019/12/26 20:13
 * @Version 1.0
 */
@Data
public class CarAccessDto {
    // UUID
    private String id;
    // 车牌号（没有车牌的填“无车牌”）
    private String plateNo;
    // 车牌颜色（1：黑色2：白色5：蓝色6：黄色9：绿色）
    private String plateColor;
    // 车牌类型（未登记为空串，01：大型汽车号牌02：小型汽车号牌03：使馆汽车号牌04：领馆汽车号牌）
    private String plateClass;
    // 有无车牌（有车牌：“true”无车牌：“false”）
    private String hasPlate;
    // 设备编号
    private String deviceID;
    // 抓拍时间
    private String shotTime;
    // 抓拍图片
    private String image;
    // 车主身份证号(若未登记则为空串)
    private String idCard;
}
