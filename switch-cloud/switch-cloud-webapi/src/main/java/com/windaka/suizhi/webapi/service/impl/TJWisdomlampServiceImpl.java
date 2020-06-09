package com.windaka.suizhi.webapi.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.webapi.dao.TJWisdomlampDao;
import com.windaka.suizhi.webapi.model.TJWisdomlamp;
import com.windaka.suizhi.webapi.service.TJWisdomlampService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 统计－智慧灯杆ServiceImpl
 * @author pxl
 * @create: 2019-05-28 17:03
 */
@Slf4j
@Service
public class TJWisdomlampServiceImpl implements TJWisdomlampService {

    @Autowired
    private TJWisdomlampDao dao;

    /**
     * 新增统计－智慧灯杆
     * @param params
     * @throws OssRenderException
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveMap(Map<String, Object> params) throws OssRenderException {
        String xqCode = (String) params.get("xqCode");
        if (StringUtils.isBlank(xqCode)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区Code不能为空");
        }

        //删除记录
        dao.delete(xqCode);

        //region Map转实体类对象
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();

        TJWisdomlamp model = new TJWisdomlamp();
        model.setXq_code((String) params.get("xqCode"));
        model.setLight_device_online(Integer.parseInt(params.get("lightDeviceOnline").toString()));
        model.setLight_device_all(Integer.parseInt(params.get("lightDeviceAll").toString()));
        model.setGb_device_online(Integer.parseInt(params.get("gbDeviceOnline").toString()));
        model.setGb_device_all(Integer.parseInt(params.get("gbDeviceAll").toString()));
        model.setLED_device_online(Integer.parseInt(params.get("LEDDeviceOnline").toString()));
        model.setLED_device_all(Integer.parseInt(params.get("LEDDeviceAll").toString()));
        model.setHjjc_device_online(Integer.parseInt(params.get("hjjcDeviceOnline").toString()));
        model.setHjjc_device_all(Integer.parseInt(params.get("hjjcDeviceAll").toString()));
        model.setCre_by(loginAppUser.getUserId());
        model.setCre_time(new Date());
        model.setUpd_by(loginAppUser.getUserId());
        model.setUpd_time(new Date());
        //endregion

        //保存数据
        dao.save(model);
    }

    /**
     * 删除所有统计－智慧灯杆
     * @return
     *
     * @author pxl
     * @create 2019-05-28 17:03
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String xqCode) {
        int result = dao.delete(xqCode);
    }

    /**
     * 查询列表
     * @return
     *
     * @author pxl
     * @create 2019-05-28 17:03
     */
    @Override
    public Map<String, Object> query(Map<String, Object> params) throws OssRenderException {
        String areaId = (String) params.get("areaId");
        String wyCode = (String) params.get("wyCode");
        String xqCode = (String) params.get("xqCode");
        if (StringUtils.isBlank(areaId)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"区域不能为空");
        }
//        if (StringUtils.isBlank(wyCode)) {
//            throw new OssRenderException(ReturnConstants.CODE_FAILED,"物业编码不能为空");
//        }
//        if (StringUtils.isBlank(xqCode)) {
//            throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区编码不能为空");
//        }

//        //验证当前用户查询权限
//        TJUtil.getInstance(htUserXQDao).checkAuth(params);

        Map map = dao.query(params);
        if(map==null){
            //throw new OssRenderException(ReturnConstants.CODE_FAILED,"查询的数据不存在");
            map = new HashMap<String,Object>();
            map.put("lightDeviceOnline",0);
            map.put("lightDeviceAll",0);
            map.put("gbDeviceAll",0);
            map.put("gbDeviceOnline",0);
            map.put("LEDDeviceAll",0);
            map.put("LEDDeviceOnline",0);
            map.put("hjjcDeviceOnline",0);
            map.put("hjjcDeviceAll",0);
        }
        return map;
    }
}
