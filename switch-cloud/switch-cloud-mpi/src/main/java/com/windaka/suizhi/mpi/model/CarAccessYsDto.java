package com.windaka.suizhi.mpi.model;

/**
 * @ClassName CarAccessYsDto
 * @Description 给以萨传输数据格式
 * @Author lixianhua
 * @Date 2020/1/4 9:22
 * @Version 1.0
 */
public class CarAccessYsDto {

    // 设备编号
    private String devNo;

    // 抓拍时间
    private String captureTime;

    // 车牌号码
    private String carNun;

    //图片名称
    private String fileName;

    public String getDevNo() {
        return devNo;
    }

    public void setDevNo(String devNo) {
        this.devNo = devNo;
    }

    public String getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(String captureTime) {
        this.captureTime = captureTime;
    }

    public String getCarNun() {
        return carNun;
    }

    public void setCarNun(String carNun) {
        this.carNun = carNun;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
