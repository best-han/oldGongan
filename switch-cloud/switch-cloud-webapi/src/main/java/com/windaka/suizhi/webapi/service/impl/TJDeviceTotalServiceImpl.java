package com.windaka.suizhi.webapi.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.webapi.dao.TJDeviceTotalDao;
import com.windaka.suizhi.webapi.model.TJDeviceTotal;
import com.windaka.suizhi.webapi.model.TJDeviceType;
import com.windaka.suizhi.webapi.service.TJDeviceTotalService;
import com.windaka.suizhi.webapi.service.TJDeviceTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计－考勤管理ServiceImpl
 * @author pxl
 * @create: 2019-05-06 10:22
 */
@Slf4j
@Service
public class TJDeviceTotalServiceImpl implements TJDeviceTotalService {


    @Autowired
    private TJDeviceTotalDao dao;
    @Autowired
    private TJDeviceTypeService tjDeviceTypeService;

    /**
     * 新增统计－缴费信息
     * @param params
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveMap(Map<String, Object> params) throws OssRenderException {
        String xqCode = (String) params.get("xqCode");
        String deviceType = (String) params.get("deviceCode");
        if (StringUtils.isBlank(xqCode)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区Code不能为空");
        }

        if (StringUtils.isBlank(deviceType)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"设备类型不能为空");
        }else{
            TJDeviceType model = tjDeviceTypeService.query(deviceType);
            if(model==null){
                throw new OssRenderException(ReturnConstants.CODE_FAILED,"设备类型编码不存在");
            }
        }

        //删除记录
        dao.delete(xqCode,deviceType);

        //region Map转实体类对象
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();

        TJDeviceTotal model = new TJDeviceTotal();
        model.setXq_code((String) params.get("xqCode"));
        model.setDevice_code((String) params.get("deviceCode").toString());
        model.setTotal_device(Integer.parseInt(params.get("totalDevice").toString()));
        model.setOnline(Integer.parseInt(params.get("online").toString()));
        model.setCre_by(loginAppUser.getUserId());
        model.setCre_time(new Date());
        model.setUpd_by(loginAppUser.getUserId());
        model.setUpd_time(new Date());
        //endregion

        //保存数据
        dao.save(model);
    }

    /**
     * 根据小区Code、设备类型，删除记录
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String xqCode,String deviceType){
        int result = dao.delete(xqCode,deviceType);
    }

    /**
     * 查询列表
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    @Override
    public Map<String,Object> query(Map<String, Object> params) throws OssRenderException {
        String areaId = (String) params.get("areaId");
//        String wyCode = (String) params.get("wyCode");
//        String xqCode = (String) params.get("xqCode");

        if (StringUtils.isBlank(areaId)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"区域不能为空");
        }
//        if (StringUtils.isBlank(xqCode)) {
//            throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区编码不能为空");
//        }
//        if (StringUtils.isBlank(xqCode)) {
//            throw new OssRenderException(ReturnConstants.CODE_FAILED,"物业编码不能为空");
//        }

        //验证当前用户查询权限
//        TJUtil.checkAuth(params);

        Map<String,Object> map = dao.queryTotal(params);
        if(map==null){
            //throw new OssRenderException(ReturnConstants.CODE_FAILED,"查询的数据不存在");
            map = new HashMap<String,Object>();
            map.put("totalNum",0);
            map.put("onlineNum",0);
            String[] array = {};
            map.put("devices",array);
        }else {
            List<Map<String, Object>> list = dao.queryDetail(params);
            map.put("devices", list);
        }
        return map;
    }
}
