package com.sample;


public class AnimalAttribute {

  private long id;
  private java.sql.Timestamp captureTime;
  private String captureDeviceId;
  private String captureImg;
  private long color;
  private long breed;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public java.sql.Timestamp getCaptureTime() {
    return captureTime;
  }

  public void setCaptureTime(java.sql.Timestamp captureTime) {
    this.captureTime = captureTime;
  }


  public String getCaptureDeviceId() {
    return captureDeviceId;
  }

  public void setCaptureDeviceId(String captureDeviceId) {
    this.captureDeviceId = captureDeviceId;
  }


  public String getCaptureImg() {
    return captureImg;
  }

  public void setCaptureImg(String captureImg) {
    this.captureImg = captureImg;
  }


  public long getColor() {
    return color;
  }

  public void setColor(long color) {
    this.color = color;
  }


  public long getBreed() {
    return breed;
  }

  public void setBreed(long breed) {
    this.breed = breed;
  }

}
