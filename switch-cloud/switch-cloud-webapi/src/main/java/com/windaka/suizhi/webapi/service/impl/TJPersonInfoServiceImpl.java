package com.windaka.suizhi.webapi.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.webapi.dao.FaceTypePersonDao;
import com.windaka.suizhi.webapi.dao.TJPersonInfoDao;
import com.windaka.suizhi.webapi.model.TJPersonInfo;
import com.windaka.suizhi.webapi.service.TJPersonInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 * 统计－人员信息ServiceImpl
 * @author pxl
 * @create: 2019-05-06 10:22
 */
@Slf4j
@Service
public class TJPersonInfoServiceImpl implements TJPersonInfoService {


    @Autowired
    private TJPersonInfoDao dao;
    @Autowired
    private FaceTypePersonDao faceTypePersonDao;

    /**
     * 新增统计－人员信息
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

        TJPersonInfo model = new TJPersonInfo();
        model.setXq_code((String) params.get("xqCode"));
        model.setPer_total_num(Integer.parseInt(params.get("perTotalNum").toString()));
        model.setOwner_num(Integer.parseInt(params.get("ownerNum").toString()));
        model.setFamily_member_num(Integer.parseInt(params.get("familyMemberNum").toString()));
        model.setTenement_num(Integer.parseInt(params.get("tenementNum").toString()));
        model.setNewper_num(Integer.parseInt(params.get("newperNum").toString()));
        model.setCre_by(loginAppUser.getUserId());
        model.setCre_time(new Date());
        model.setUpd_by(loginAppUser.getUserId());
        model.setUpd_time(new Date());
        //endregion

        //保存数据
        dao.save(model);
    }

    /**
     * 删除所有统计－人员信息
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
            map.put("perTotalNum",0);
            map.put("ownerNum",0);
            map.put("familyMemberNum",0);
            map.put("tenementNum",0);
            map.put("newperNum",0);
        }
        return map;
    }

    @Override
    public List<Map<String, Object>> queryFaceTypesOfPerson(Map<String, Object> params) throws OssRenderException {
        String areaId = (String) params.get("areaId");
        if (StringUtils.isBlank(areaId)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"区域不能为空");
        }
        List<Map<String, Object>> list=faceTypePersonDao.queryFaceTypes();
        if(list==null || list.size()==0 ){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"人脸类型表为空");
        }
        int num=0;
        int sum=dao.querySumFaceType(params);
        BigDecimal b2=new BigDecimal(sum);
        for (Map map:list) {
            if(sum==0){
                map.put("num",0);
                map.put("percent",0);
            }else{
                params.put("faceTypeCode",map.get("faceTypeCode"));
                num=dao.querySumByFaceType(params);
                map.put("num",num);
                map.put("percent",new BigDecimal(num).divide(b2,2, ROUND_HALF_UP).doubleValue());
            }
        }
        return list;

    }

    @Override
    public Map<String,Object> queryPersonInfoForOverview(Map<String, Object> params) throws OssRenderException {
        String wyCode = (String) params.get("wyCode");
        String xqCode = (String) params.get("xqCode");
        String areaId = (String) params.get("areaId");
        if (StringUtils.isBlank(areaId)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"区域不能为空");
        }

        BigDecimal totalNum = new BigDecimal(dao.querySumPserson(params));
        BigDecimal manNum = new BigDecimal(dao.queryManNum(params));
        BigDecimal womanNum = new BigDecimal(dao.queryWomanNum(params));
        BigDecimal marryNum = new BigDecimal(dao.queryMarryNum(params));
        BigDecimal singleNum = new BigDecimal(dao.querySingleNum(params));
        BigDecimal faceRegister = new BigDecimal(dao.queryFaceRegisterNum(params));

        Map map = new HashMap();
        map.put("totalNum",totalNum);
        map.put("manNum",manNum);
        map.put("womanNum",womanNum);
        map.put("manPercent",manNum.divide(totalNum,3,ROUND_HALF_UP));
        map.put("womanPercent",womanNum.divide(totalNum,3,ROUND_HALF_UP));
        map.put("marryNum",marryNum);
        map.put("singleNum",singleNum);
        map.put("marryPercent",marryNum.divide(totalNum,3,ROUND_HALF_UP));
        map.put("singlePercent",singleNum.divide(totalNum,3,ROUND_HALF_UP));
        map.put("facePercent",faceRegister.divide(totalNum,3,ROUND_HALF_UP));

        return map;
    }

}
