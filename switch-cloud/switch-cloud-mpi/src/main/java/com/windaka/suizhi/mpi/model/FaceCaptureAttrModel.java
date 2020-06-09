package com.windaka.suizhi.mpi.model;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName FaceCaptureAttrModel
 * @Description 人脸抓拍实体类
 * @Author lixianhua
 * @Date 2019/12/26 17:35
 * @Version 1.0
 */
@Data
public class FaceCaptureAttrModel {

    // 抓拍主键
    private Integer id;

    // 图片路径
    private String base64Img;

    // 抓拍设备主键
    private Integer deviceId;

    // 抓拍时间
    private Date captureTime;
}
