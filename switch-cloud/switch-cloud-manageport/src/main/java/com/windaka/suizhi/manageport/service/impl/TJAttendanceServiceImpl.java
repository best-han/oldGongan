package com.windaka.suizhi.manageport.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.manageport.dao.TJAttendanceDao;
import com.windaka.suizhi.manageport.model.TJAttendance;
import com.windaka.suizhi.manageport.service.TJAttendanceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 统计－考勤管理ServiceImpl
 * @author pxl
 * @create: 2019-05-06 10:22
 */
@Slf4j
@Service
public class TJAttendanceServiceImpl implements TJAttendanceService {


    @Autowired
    private TJAttendanceDao dao;

    /**
     * 新增统计－考勤管理
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
        if (StringUtils.isBlank(xqCode)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区Code不能为空");
        }

        //删除记录
        dao.delete(xqCode);

        //region Map转实体类对象
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();

        TJAttendance model = new TJAttendance();
        model.setXq_code((String) params.get("xqCode"));
        model.setPer_total(Integer.parseInt(params.get("perTotal").toString()));
        model.setPer_duty(Integer.parseInt(params.get("perDuty").toString()));
        model.setNormal(Integer.parseInt(params.get("normal").toString()));
        model.setLate(Integer.parseInt(params.get("late").toString()));
        model.setLeave(Integer.parseInt(params.get("leave").toString()));
        model.setCre_by(loginAppUser.getUserId());
        model.setCre_time(new Date());
        model.setUpd_by(loginAppUser.getUserId());
        model.setUpd_time(new Date());
        //endregion

        //保存数据
        dao.save(model);
    }

    /**
     * 根据小区Code删除记录
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String xqCode){
        int result = dao.delete(xqCode);
    }

    /**
     * 查询列表
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    @Override
    public Map<String, Object> query(Map<String, Object> params) throws OssRenderException {
        String areaId = (String) params.get("areaId");
        String wyCode = (String) params.get("wyCode");
        String xqCode = (String) params.get("xqCode");

        if (StringUtils.isBlank(areaId)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"区域不能为空");
        }
//        if (StringUtils.isBlank(xqCode)) {
//            throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区编码不能为空");
//        }
//        if (StringUtils.isBlank(xqCode)) {
//            throw new OssRenderException(ReturnConstants.CODE_FAILED,"物业编码不能为空");
//        }

//        //验证当前用户查询权限
//        TJUtil.getInstance(htUserXQDao).checkAuth(params);

        Map map = dao.query(params);
        if(map==null){
            //throw new OssRenderException(ReturnConstants.CODE_FAILED,"查询的数据不存在");
            map = new HashMap<String,Object>();
            map.put("perTotal",0);
            map.put("perDuty",0);
            map.put("normal",0);
            map.put("late",0);
            map.put("leave",0);
        }
        return map;
    }
}
