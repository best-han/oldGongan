package com.windaka.suizhi.webapi.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.webapi.dao.HTUserXQDao;
import com.windaka.suizhi.webapi.dao.TJPayDao;
import com.windaka.suizhi.webapi.service.TJPayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 统计－缴费信息ServiceImpl
 * @author pxl
 * @create: 2019-05-06 10:22
 */
@Slf4j
@Service
public class TJPayServiceImpl implements TJPayService {


    @Autowired
    private TJPayDao dao;

    @Autowired
    private HTUserXQDao htUserXQDao;

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
        if (StringUtils.isBlank(xqCode)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区Code不能为空");
        }
        //List<Map<String, Object>> dataList=(List<Map<String, Object>>)params.get("dataList");
        //删除记录
        dao.deletePayment(params);
        //region Map转实体类对象
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();

      /*  TJPay model = new TJPay();
        model.setXq_code((String) params.get("xqCode"));
        model.setMonth(Integer.parseInt(params.get("month").toString()));
        model.setPay_amount(Double.parseDouble(params.get("payAmount").toString()));
        model.setFinishing_rate(Double.parseDouble(params.get("finishingRate").toString()));
        model.setToday_pay(Double.parseDouble(params.get("todayPay").toString()));
        model.setCurrent_month_pay(Double.parseDouble(params.get("currentMonthPay").toString()));
        model.setCre_by(loginAppUser.getUserId());
        model.setCre_time(new Date());
        model.setUpd_by(loginAppUser.getUserId());
        model.setUpd_time(new Date());
        //endregion
        //保存数据
        dao.save(model);*/
        params.put("creBy",loginAppUser.getUserId());
        params.put("creTime",new Date());
        params.put("updBy",loginAppUser.getUserId());
        params.put("updTime",new Date());
        dao.savePayment(params);


    }

    /**
     * 根据小区Code删除记录
     * @return
     *
     * @author pxl hjt
     * @create 2019-05-06 10:26
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String xqCode){
        int result = dao.deletePaymentByXqCode(xqCode);
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
        if (StringUtils.isBlank(areaId)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"区域不能为空");
        }

//        //验证当前用户查询权限
//        TJUtil.getInstance(htUserXQDao).checkAuth(params);

        /*Map map = dao.query(params);
        if(map==null){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"查询的数据不存在");
        }*/

        Map<String, Object> map =new HashMap<>();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        String operDate=sdf.format(date);
        params.put("operDate",operDate);
        /*map=dao.queryTjPaySumMonth(params);
        if(MapUtils.isEmpty(map)){
            map.put("sumMonth",0);//月度累计完成
        }*/
        SimpleDateFormat sdf1=new SimpleDateFormat("MM");
        int currentMonth=Integer.parseInt(sdf1.format(date));
        if(currentMonth>=7){//判断上半年还是下半年：up上半年 down下半年
            map.put("monthOfYear","down");
        }else{
            map.put("monthOfYear","up");
        }
        List<Map<String, Object>> list=dao.queryTjPayList(params);
        double[] payMonth={0,0,0,0,0,0,0,0,0,0,0,0};//该月的缴费数
        String[] percent=new String[12];//该月缴费完成率
        int[] monthList={1,2,3,4,5,6,7,8,9,10,11,12};//月份
        DecimalFormat df = new DecimalFormat("0.00");
        if(list!=null && list.size()>0){
            for(int i=0;i<list.size();i++){
                if(Objects.equals(list.get(i).get("month"),currentMonth)){
                    map.put("sumMonth",list.get(i).get("payMonth").toString());//当月缴费
                }
                if(Objects.equals(list.get(i).get("month"),monthList[i])){
                    payMonth[i]=Double.parseDouble(list.get(i).get("payMonth").toString());
                    if(Double.parseDouble(list.get(i).get("payMonth").toString())==0 ||
                            Double.parseDouble(list.get(i).get("payableMonth").toString())==0){
                        percent[i]="0";
                    }else{
                        //该月缴费数除以该月应缴费得出percent
                        percent[i]=df.format(Double.parseDouble(list.get(i).get("payMonth").toString())
                                /Double.parseDouble(list.get(i).get("payableMonth").toString()));
                    }
                }
            }
        }
        double payDay=dao.queryTjPaySumPayDay(params);
        map.put("payDay",payDay);
        map.put("payMonth",payMonth);
        map.put("percent",percent);
        map.put("monthList",monthList);
        return map;
    }
}
