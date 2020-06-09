package com.windaka.suizhi.manageport.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @ClassName DeviceDao
 * @Description 设备dao层
 * @Author lixianhua
 * @Date 2019/12/9 8:45
 * @Version 1.0
 */
@Mapper
public interface DeviceDao {
    /**
     * 功能描述: 添加设备
     * @auther: lixianhua
     * @date: 2019/12/9 9:08
     * @param:
     * @return:
     */
    int insertSelectiveDevice(Map<String, Object> params);
    /**
     * 功能描述: 根据小区编码和管理端主键获取唯一记录
     * @auther: lixianhua
     * @date: 2019/12/9 9:45
     * @param:
     * @return:
     */
    Map<String, Object> getSingleRecord(Map<String, Object> params);
    /**
     * 功能描述: 根据主键更新设备
     * @auther: lixianhua
     * @date: 2019/12/9 9:54
     * @param:
     * @return:
     */
    int updateSelectiveDevice(Map<String ,Object> map);
    /**
     * 功能描述: 删除设备
     * @auther: lixianhua
     * @date: 2019/12/9 10:17
     * @param:
     * @return:
     */
    int deleteSelectiveDevice(Map<String, Object> params);
}
