package com.windaka.suizhi.mpi.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @InterfaceName FaceCaptureDeviceDao
 * @Description 抓拍设备dao
 * @Author lixianhua
 * @Date 2019/12/26 15:28
 * @Version 1.0
 */
@Mapper
public interface FaceCaptureDeviceDao {

    /**
     * 功能描述: 根据管理端主键获取抓拍设备
     * @auther: lixianhua
     * @date: 2019/12/26 15:31
     * @param:
     * @return:
     */
    Map<String, Object> getFaceDevice(String captureDeviceId);
}
