package com.windaka.suizhi.manageport.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.SqlFileUtil;
import com.windaka.suizhi.manageport.dao.DeviceDao;
import com.windaka.suizhi.manageport.dao.FaceAlarmRecordDao;
import com.windaka.suizhi.manageport.service.DeviceService;
import com.windaka.suizhi.manageport.util.SqlCreateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * @ClassName DeviceServiceImpl
 * @Description 设备实现类
 * @Author lixianhua
 * @Date 2019/12/9 8:45
 * @Version 1.0
 */
@Slf4j
@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceDao deviceDao;
    /**
     * 功能描述: 添加设备
     *
     * @auther: lixianhua
     * @date: 2019/12/9 9:01
     * @param:
     * @return:
     */
    @Override
    public void addDeviceRecord(Map<String, Object> params) throws OssRenderException, IOException {
        if (StringUtils.isBlank((String) params.get("xqCode"))) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "小区编码不能为空");
        }
        if (StringUtils.isBlank((String) params.get("manageId"))) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "管理端主键不能为空");
        }
        int insertNum = this.deviceDao.insertSelectiveDevice(params);
        if (insertNum<1){
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "添加失败");
        }
        String sqlStr= SqlCreateUtil.getSqlByMybatis(DeviceDao.class.getName()+".insertSelectiveDevice",params);
        SqlFileUtil.InsertSqlToFile(sqlStr);
    }
    /**
     * 功能描述: 修改设备
     * @auther: lixianhua
     * @date: 2019/12/9 9:39
     * @param:
     * @return:
     */
    @Override
    public void updateDeviceRecord(Map<String, Object> params) throws OssRenderException, IOException {
        if (StringUtils.isBlank((String) params.get("xqCode"))) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "小区编码不能为空");
        }
        if (StringUtils.isBlank((String) params.get("manageId"))) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "管理端主键不能为空");
        }
        // 获取唯一记录
        Map<String ,Object> map =  this.deviceDao.getSingleRecord(params);
        if (null == map){
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "记录不存在");
        }
        params.put("id",map.get("id"));
        int updateNum = this.deviceDao.updateSelectiveDevice(params);
        if (updateNum<1){
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "修改失败");
        }
        String sqlStr= SqlCreateUtil.getSqlByMybatis(DeviceDao.class.getName()+".updateSelectiveDevice",params);
        SqlFileUtil.InsertSqlToFile(sqlStr);
    }
    /**
     * 功能描述: 删除设备
     * @auther: lixianhua
     * @date: 2019/12/9 10:15
     * @param:
     * @return:
     */
    @Override
    public void deleteDeviceRecord(Map<String, Object> params) throws OssRenderException, IOException {
        if (StringUtils.isBlank((String) params.get("xqCode"))) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "小区编码不能为空");
        }
        if (StringUtils.isBlank((String) params.get("manageId"))) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "管理端主键不能为空");
        }
        int deleteNum = this.deviceDao.deleteSelectiveDevice(params);
        if (deleteNum<1){
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "删除失败");
        }
        String sqlStr= SqlCreateUtil.getSqlByMybatis(DeviceDao.class.getName()+".deleteSelectiveDevice",params);
        SqlFileUtil.InsertSqlToFile(sqlStr);
    }
}
