package com.windaka.suizhi.webapi.service.impl;


import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.constants.FastdfsConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.FileUploadUtil;
import com.windaka.suizhi.common.utils.PageUtil;
import com.windaka.suizhi.common.utils.PropertiesUtil;
import com.windaka.suizhi.common.utils.TimesUtil;
import com.windaka.suizhi.webapi.dao.*;
import com.windaka.suizhi.webapi.service.PersonService;
import com.windaka.suizhi.webapi.service.RoomService;
import com.windaka.suizhi.webapi.util.DateUtil;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import javax.rmi.CORBA.Util;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.math.BigDecimal.ROUND_HALF_UP;

@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao personDao;
    @Autowired
    private XqPersonRelationDao xqPersonRelationDao;
    @Autowired
    private OwnerRoomDao ownerRoomDao;
    @Autowired
    private RoomDao roomDao;
    @Autowired
    private SingleTableDao singleTableDao;
    @Autowired
    private DictDao dictDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void savePersons(String xqCode, List<Map<String, Object>> persons) throws OssRenderException, IOException {


        //添加角色
        if (xqCode == null || StringUtils.isBlank(xqCode)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "缺少xqCode参数");
        }
        for (int i = 0; i < persons.size(); i++) {
            Map<String, Object> person = persons.get(i);
            String code = (String) person.get("code");
            if (code == null || StringUtils.isBlank(code)) {
                throw new OssRenderException(ReturnConstants.CODE_FAILED, "缺少code参数");
            }
            String createTime = (String) person.get("createDate");
            if (createTime == null && StringUtils.isBlank(createTime)) {
                throw new OssRenderException(ReturnConstants.CODE_FAILED, "缺少createDate参数");
            }
            // 清洗数据

            String personIdentityId = (String) person.get("personIdentityId");
            if (personIdentityId != null) {
                if (StringUtils.isEmpty(personIdentityId)) {
                    person.remove("personIdentityId");
                } else {
                    Integer personIdentityIdTemp = Integer.parseInt(personIdentityId);
                    person.put("personIdentityId", personIdentityIdTemp);
                }
            }

            String updateDate = (String) person.get("updateDate");
            if (updateDate != null) {
                if (StringUtils.isEmpty(updateDate)) {
                    person.remove("updateDate");
                } else {
                    Date updateDateTemp = TimesUtil.stringToDate(updateDate);
                    if (updateDateTemp != null && !updateDateTemp.equals("")) {
                        person.put("updateDate", updateDateTemp);
                    }
                }
            }
            String extendD1 = (String) person.get("extendD1");
            if (extendD1 != null) {
                if (StringUtils.isEmpty(extendD1)) {
                    person.remove("extendD1");
                } else {
                    Date extendD1Temp = TimesUtil.stringToDate(extendD1);
                    if (extendD1Temp != null && !extendD1Temp.equals("")) {
                        person.put("extendD1", extendD1Temp);
                    }
                }
            }
            String extendD2 = (String) person.get("extendD2");
            if (extendD2 != null) {
                if (StringUtils.isEmpty(extendD2)) {
                    person.remove("extendD2");
                } else {
                    Date extendD2Temp = TimesUtil.stringToDate(extendD2);
                    if (extendD2Temp != null && !extendD2Temp.equals("")) {
                        person.put("extendD2", extendD2Temp);
                    }
                }
            }

            String extendD3 = (String) person.get("extendD3");
            if (extendD3 != null) {
                if (StringUtils.isEmpty(extendD3)) {
                    person.remove("extendD3");
                } else {
                    Date extendD3Temp = TimesUtil.stringToDate(extendD3);
                    if (extendD3Temp != null && !extendD3Temp.equals("")) {
                        person.put("extendD3", extendD3Temp);
                    }
                }
            }
            String extendD4 = (String) person.get("extendD4");
            if (extendD4 != null) {
                if (StringUtils.isEmpty(extendD4)) {
                    person.remove("extendD4");
                } else {
                    Date extendD4Temp = TimesUtil.stringToDate(extendD4);
                    if (extendD4Temp != null && !extendD4Temp.equals("")) {
                        person.put("extendD4", extendD4Temp);
                    }
                }
            }

            String extendI1 = (String) person.get("extendI1");
            if (extendI1 != null) {
                if (StringUtils.isEmpty(extendI1)) {
                    person.remove("extendI1");
                } else {
                    BigDecimal extendI1Temp = BigDecimal.valueOf(Double.parseDouble(extendI1));
                    person.put("extendI1", extendI1Temp);
                }
            }
            String extendI2 = (String) person.get("extendI2");
            if (extendI2 != null) {
                if (StringUtils.isEmpty(extendI2)) {
                    person.remove("extendI2");
                } else {
                    BigDecimal extendI2Temp = BigDecimal.valueOf(Double.parseDouble(extendI2));
                    person.put("extendI2", extendI2Temp);
                }
            }
            String extendI3 = (String) person.get("extendI3");
            if (extendI3 != null) {
                if (StringUtils.isEmpty(extendI3)) {
                    person.remove("extendI3");
                } else {
                    BigDecimal extendI3Temp = BigDecimal.valueOf(Double.parseDouble(extendI3));
                    person.put("extendI3", extendI3Temp);
                }
            }
            String extendI4 = (String) person.get("extendI4");
            if (extendI4 != null) {
                if (StringUtils.isEmpty(extendI4)) {
                    person.remove("extendI4");
                } else {
                    BigDecimal extendI4Temp = BigDecimal.valueOf(Double.parseDouble(extendI4));
                    person.put("extendI4", extendI4Temp);
                }
            }
            String extendF1 = (String) person.get("extendF1");
            if (extendF1 != null) {
                if (StringUtils.isEmpty(extendF1)) {
                    person.remove("extendF1");
                } else {
                    BigDecimal extendF1Temp = BigDecimal.valueOf(Double.parseDouble(extendF1));
                    person.put("extendF1", extendF1Temp);
                }
            }
            String extendF2 = (String) person.get("extendF2");
            if (extendF2 != null) {
                if (StringUtils.isEmpty(extendF2)) {
                    person.remove("extendF2");
                } else {
                    BigDecimal extendF2Temp = BigDecimal.valueOf(Double.parseDouble(extendF2));
                    person.put("extendF2", extendF2Temp);
                }
            }
            String extendF3 = (String) person.get("extendF3");
            if (extendF3 != null) {
                if (StringUtils.isEmpty(extendF3)) {
                    person.remove("extendF3");
                } else {
                    BigDecimal extendF3Temp = BigDecimal.valueOf(Double.parseDouble(extendF3));
                    person.put("extendF3", extendF3Temp);
                }
            }
            String extendF4 = (String) person.get("extendF4");
            if (extendF4 != null) {
                if (StringUtils.isEmpty(extendF4)) {
                    person.remove("extendF4");
                } else {
                    BigDecimal extendF4Temp = BigDecimal.valueOf(Double.parseDouble(extendF4));
                    person.put("extendF4", extendF4Temp);
                }
            }

            //保存图片
            if (person.get("extendS4") != null && !((String) person.get("extendS4")).equals("")) {

                person.put("xqCode", xqCode);
                person.put("personCode", person.get("code"));
                person.put("uploadFile", person.get("extendS4"));
                try {
                    String uploadPath = FileUploadUtil.uploadFile(person);
                    if (uploadPath == null && uploadPath.equals("")) {
                        throw new OssRenderException(ReturnConstants.CODE_FAILED, "批量保存人员失败");
                    } else {
                        person.put("extendS4", uploadPath);
                    }
                } catch (FileUploadException e) {
                    e.printStackTrace();
                    throw new OssRenderException(ReturnConstants.CODE_FAILED, "批量保存人员失败");
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new OssRenderException(ReturnConstants.CODE_FAILED, "批量保存人员失败");
                }
            }
            int s = 0;
            try {
                s = personDao.savePerson(person);
            } catch (Exception e) {
                throw new OssRenderException(ReturnConstants.CODE_FAILED, "存在已存在的人员，请先删除该人员");
            }
            if (s == 0) {
                throw new OssRenderException(ReturnConstants.CODE_FAILED, "批量保存人员失败");
            } else {
                if (xqPersonRelationDao.saveXqPersonRelation(xqCode, (String) person.get("code")) == 0) {
                    throw new OssRenderException(ReturnConstants.CODE_FAILED, "批量保存人员失败");
                }

            }


        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePerson(Map<String, Object> person) throws OssRenderException {
        //修改小区人员
        String code = (String) person.get("code");
        if (code == null || code.equals("")) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "缺少code参数");
        }

        String personIdentityId = (String) person.get("personIdentityId");
        if (personIdentityId != null) {
            if (StringUtils.isEmpty(personIdentityId)) {
                person.remove("personIdentityId");
            } else {
                Integer personIdentityIdTemp = Integer.parseInt(personIdentityId);
                person.put("personIdentityId", personIdentityIdTemp);
            }
        }

        String updateDate = (String) person.get("updateDate");
        if (updateDate != null) {
            if (StringUtils.isEmpty(updateDate)) {
                person.remove("updateDate");
            } else {
                Date updateDateTemp = TimesUtil.stringToDate(updateDate);
                if (updateDateTemp != null && !updateDateTemp.equals("")) {
                    person.put("updateDate", updateDateTemp);
                }
            }
        }
        String extendD1 = (String) person.get("extendD1");
        if (extendD1 != null) {
            if (StringUtils.isEmpty(extendD1)) {
                person.remove("extendD1");
            } else {
                Date extendD1Temp = TimesUtil.stringToDate(extendD1);
                if (extendD1Temp != null && !extendD1Temp.equals("")) {
                    person.put("extendD1", extendD1Temp);
                }
            }
        }
        String extendD2 = (String) person.get("extendD2");
        if (extendD2 != null) {
            if (StringUtils.isEmpty(extendD2)) {
                person.remove("extendD2");
            } else {
                Date extendD2Temp = TimesUtil.stringToDate(extendD2);
                if (extendD2Temp != null && !extendD2Temp.equals("")) {
                    person.put("extendD2", extendD2Temp);
                }
            }
        }

        String extendD3 = (String) person.get("extendD3");
        if (extendD3 != null) {
            if (StringUtils.isEmpty(extendD3)) {
                person.remove("extendD3");
            } else {
                Date extendD3Temp = TimesUtil.stringToDate(extendD3);
                if (extendD3Temp != null && !extendD3Temp.equals("")) {
                    person.put("extendD3", extendD3Temp);
                }
            }
        }
        String extendD4 = (String) person.get("extendD4");
        if (extendD4 != null) {
            if (StringUtils.isEmpty(extendD4)) {
                person.remove("extendD4");
            } else {
                Date extendD4Temp = TimesUtil.stringToDate(extendD4);
                if (extendD4Temp != null && !extendD4Temp.equals("")) {
                    person.put("extendD4", extendD4Temp);
                }
            }
        }

        String extendI1 = (String) person.get("extendI1");
        if (extendI1 != null) {
            if (StringUtils.isEmpty(extendI1)) {
                person.remove("extendI1");
            } else {
                BigDecimal extendI1Temp = BigDecimal.valueOf(Double.parseDouble(extendI1));
                person.put("extendI1", extendI1Temp);
            }
        }
        String extendI2 = (String) person.get("extendI2");
        if (extendI2 != null) {
            if (StringUtils.isEmpty(extendI2)) {
                person.remove("extendI2");
            } else {
                BigDecimal extendI2Temp = BigDecimal.valueOf(Double.parseDouble(extendI2));
                person.put("extendI2", extendI2Temp);
            }
        }
        String extendI3 = (String) person.get("extendI3");
        if (extendI3 != null) {
            if (StringUtils.isEmpty(extendI3)) {
                person.remove("extendI3");
            } else {
                BigDecimal extendI3Temp = BigDecimal.valueOf(Double.parseDouble(extendI3));
                person.put("extendI3", extendI3Temp);
            }
        }
        String extendI4 = (String) person.get("extendI4");
        if (extendI4 != null) {
            if (StringUtils.isEmpty(extendI4)) {
                person.remove("extendI4");
            } else {
                BigDecimal extendI4Temp = BigDecimal.valueOf(Double.parseDouble(extendI4));
                person.put("extendI4", extendI4Temp);
            }
        }
        String extendF1 = (String) person.get("extendF1");
        if (extendF1 != null) {
            if (StringUtils.isEmpty(extendF1)) {
                person.remove("extendF1");
            } else {
                BigDecimal extendF1Temp = BigDecimal.valueOf(Double.parseDouble(extendF1));
                person.put("extendF1", extendF1Temp);
            }
        }
        String extendF2 = (String) person.get("extendF2");
        if (extendF2 != null) {
            if (StringUtils.isEmpty(extendF2)) {
                person.remove("extendF2");
            } else {
                BigDecimal extendF2Temp = BigDecimal.valueOf(Double.parseDouble(extendF2));
                person.put("extendF2", extendF2Temp);
            }
        }
        String extendF3 = (String) person.get("extendF3");
        if (extendF3 != null) {
            if (StringUtils.isEmpty(extendF3)) {
                person.remove("extendF3");
            } else {
                BigDecimal extendF3Temp = BigDecimal.valueOf(Double.parseDouble(extendF3));
                person.put("extendF3", extendF3Temp);
            }
        }
        String extendF4 = (String) person.get("extendF4");
        if (extendF4 != null) {
            if (StringUtils.isEmpty(extendF4)) {
                person.remove("extendF4");
            } else {
                BigDecimal extendF4Temp = BigDecimal.valueOf(Double.parseDouble(extendF4));
                person.put("extendF4", extendF4Temp);
            }
        }

        if (person.get("extendS4") != null && !((String) person.get("extendS4")).equals("")) {//保存图片

            String xqCode = xqPersonRelationDao.queryXqCodeByPersonCode(code);
            person.put("xqCode", xqCode);
            person.put("personCode", person.get("code"));
            person.put("uploadFile", person.get("extendS4"));
            try {
                String uploadPath = FileUploadUtil.uploadFile(person);
                if (uploadPath == null && uploadPath.equals("")) {
                    throw new OssRenderException(ReturnConstants.CODE_FAILED, "批量保存人员失败");
                } else {
                    person.put("extendS4", uploadPath);
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
                throw new OssRenderException(ReturnConstants.CODE_FAILED, "批量保存人员失败");
            } catch (IOException e) {
                e.printStackTrace();
                throw new OssRenderException(ReturnConstants.CODE_FAILED, "批量保存人员失败");
            }
        }
        int i = 0;
        try {
            i = personDao.updatePerson(person);

        } catch (Exception e) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "没有找到要修改的人员");
        }
        if (i == 0) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "修改小区人员失败");
        }


    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletePerson(String personCode) throws OssRenderException, IOException {
        int i = 0;
        try {
            i = personDao.deletePerson(personCode);

        } catch (Exception e) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "没有要删除的人");
        }
        if (i == 0) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "删除小区人员失败");
        } else {
            if (xqPersonRelationDao.deleteXqPersonRelation(personCode) == 0) {
                throw new OssRenderException(ReturnConstants.CODE_FAILED, "删除小区人员失败");
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> queryPerson(String personCode) throws OssRenderException {
        //查看小区人员
        Map<String, Object> persons = personDao.queryPerson(personCode);
        if (persons == null) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "查无此人");
        } else {
            return persons;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Page<Map<String, Object>> queryPersonList(Map<String, Object> params) throws OssRenderException {

        if (params.get("areaId") == null || params.get("areaId").toString().trim().equals(""))
            params.put("areaId", 0);
        if (params.get("page") == null || params.get("page").toString().trim().equals(""))
            params.put("page", 1);
        if (params.get("limit") == null || params.get("limit").toString().trim().equals(""))
            params.put("limit", 10);

        int totalRows = personDao.totalRows(params);
        List<Map<String, Object>> lists = Collections.emptyList();
        if (totalRows > 0) {
            PageUtil.pageParamConver(params, true);

            lists = personDao.queryPersonList(params);
            Iterator i = lists.iterator();
            while (i.hasNext()) {
                Map tMap = (Map) i.next();
                if (tMap.get("sex").equals("2")) {
                    tMap.put("sex", "女");
                }
                if (tMap.get("sex").equals("1")) {
                    tMap.put("sex", "男");
                }
            }
        }
        return new Page<>(totalRows, MapUtils.getInteger(params, PageUtil.PAGE), lists);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String queryPersonCode(Map<String, Object> params) throws OssRenderException {
        String image = (String) params.get("txImg");
        if (StringUtils.isBlank(image)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "Base64图片不能为空");
        }

        String contrastValue = (String) params.get("contrastValue");
        if (contrastValue == null) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "图片相似度不能为空");
        }

        String personCode = personDao.queryPersonCode(image);
        return personCode;
    }

    @Override
    public Map<String, Object> personBaseInfo(Map<String, Object> params) throws OssRenderException {

        if (params.get("areaId") == null || params.get("areaId").toString().equals(""))
            params.put("areaId", 0);

        Map map = new HashMap<String, Object>();
        map.put("personTotalNum", personDao.personTotalNum(params));//人口总数
        map.put("monthSenseNum", personDao.monthSenseNum(params));//本月感知人数
        map.put("todaySenseNum", personDao.todaySenseNum(params));//今日感知人数
        map.put("todaySerseStrangerNum", personDao.todaySenseStrangerNum(params));//今日陌生人感知
        map.put("permanentPersonNum", personDao.permanentPersonNum(params));//常住人口数量
        map.put("floatPersonNum", personDao.floatPersonNum(params));//流动人口数量
        map.put("monthAddFloatPersonNum", personDao.monthAddFloatPersonNum(params));//本月新增流动人口数量
        map.put("foreignerPersonNum", personDao.foreignerPersonNum(params));//涉外人口数量

        return map;
    }

    @Override
    public List<Map<String, Object>> queryPersonPropertyStatistics(Map<String, Object> params) throws OssRenderException {
        List<Map<String, Object>> list = new ArrayList<>();
        params.put("liveType", "1,2");//常住人员
        int permanentNum = personDao.permanentPersonNum(params);

        params.put("liveType", "3");//租户
        //params.put("residence", "0");//未申请居住证 为空也为未申请
        //int noResidenceNum = ownerRoomDao.queryOwnerLiveTypeNum(params);
        int sunRenter = ownerRoomDao.queryOwnerLiveTypeNum(params);

        params.put("residence", "1");//已申请居住证
        int residenceNum = ownerRoomDao.queryOwnerLiveTypeNum(params);
        int noResidenceNum = sunRenter - residenceNum;
        int sum = permanentNum + noResidenceNum + residenceNum;
        if (sum == 0) sum = 1;
        BigDecimal b2 = new BigDecimal(sum);
        list.add(new HashMap<String, Object>() {
            {
                put("personProperty", "常住人口");
                put("num", permanentNum);
                put("percent", new BigDecimal(permanentNum).divide(b2, 2, ROUND_HALF_UP).doubleValue() * 100);
            }
        });
        list.add(new HashMap<String, Object>() {
            {
                put("personProperty", "流动人口-未申请居住证");
                put("num", noResidenceNum);
                put("percent", new BigDecimal(noResidenceNum).divide(b2, 2, ROUND_HALF_UP).doubleValue() * 100);
            }
        });
        list.add(new HashMap<String, Object>() {
            {
                put("personProperty", "流动人口-已申请居住证");
                put("num", residenceNum);
                put("percent", new BigDecimal(residenceNum).divide(b2, 2, ROUND_HALF_UP).doubleValue() * 100);
            }
        });
        return list;
    }


    @Override
    public List<Map<String, Object>> queryPersonPropertyStatistics2(Map<String, Object> params) throws OssRenderException {
        if (params.get("areaId") == null || params.get("areaId").toString().equals(""))
            params.put("areaId", 0);
        List<Map<String, Object>> list = new ArrayList<>();
        int permanentPersonNum=personDao.permanentPersonNum(params);//常住人口数量
        int floatPersonNum=personDao.floatPersonNum(params);//流动人口数量
        int foreignerPersonNum=personDao.foreignerPersonNum(params);//境外人口数量
        int totalPersonNum=permanentPersonNum+floatPersonNum+foreignerPersonNum;
        BigDecimal sum=new BigDecimal(totalPersonNum);
        list.add(new HashMap<String, Object>() {
            {
                put("personProperty", "常住人口");
                put("num", permanentPersonNum);
                put("percent", new BigDecimal(permanentPersonNum).divide(sum, 3, ROUND_HALF_UP).doubleValue() * 100);
            }
        });
        list.add(new HashMap<String, Object>() {
            {
                put("personProperty", "流动人口");
                put("num", floatPersonNum);
                put("percent", new BigDecimal(floatPersonNum).divide(sum, 3, ROUND_HALF_UP).doubleValue() * 100);
            }
        });
        list.add(new HashMap<String, Object>() {
            {
                put("personProperty", "涉外人员");
                put("num", foreignerPersonNum);
                put("percent", new BigDecimal(foreignerPersonNum).divide(sum, 3, ROUND_HALF_UP).doubleValue() * 100);
            }
        });
        return list;
    }

    @Override
    public List<Map<String, Object>> personDistribute(Map<String, Object> params) throws OssRenderException {

        if (params.get("areaId") == null || params.get("areaId").toString().equals(""))
            params.put("areaId", 0);
        if (params.get("subdistrictId") == null || params.get("subdistrictId").toString().equals(""))
            params.put("subdistrictId", 0);

        int personTotalNum = personDao.personTotalNum(params);//街道总人数

        List<Map<String, Object>> lists = personDao.infoXq(params);

        for (Map<String, Object> list : lists) {
            String code = list.get("xqCode").toString();
            int personNum = personDao.personTotalXq(code);
            if (personTotalNum == 0) {
                personTotalNum = 1;
            }
            double percent = 100 * (personNum * 1.0 / personTotalNum);
            list.put("personNum", personNum);
            list.put("percent", percent);
        }
        return lists;
    }

    @Override
    public Map<String, Object> queryFloatingPersonStatistics(Map<String, Object> params) throws OssRenderException {
        Map<String, Object> map = new HashMap<>();
        params.put("startTime", DateUtil.getTodayStartTime());
        params.put("endTime", DateUtil.getTodayEndTime());
        int rentAddDay = ownerRoomDao.queryRentOwnerNum(params);
        map.put("rentAddDay", rentAddDay);
        params.put("startTime", DateUtil.getMonthStartTime());
        params.put("endTime", DateUtil.getMonthEndTime());
        int rentAddMonth = ownerRoomDao.queryRentOwnerNum(params);
        map.put("rentAddMonth", rentAddMonth);
        params.put("startTime", DateUtil.getYearStartTime());
        params.put("endTime", DateUtil.getYearEndTime());
        int rentAddYear = ownerRoomDao.queryRentOwnerNum(params);
        map.put("rentAddYear", rentAddYear);
        return map;
    }


    @Override
    public Map<String, Object> personStream(Map<String, Object> params) throws OssRenderException {
        String areaId = (String) params.get("areaId");
        String xqCode = (String) params.get("xqCode");
        String subdistrictId = (String) params.get("subdistrictId");
        params.put("todayStartTime", DateUtil.getTodayStartTime());
        params.put("todayEndTime", DateUtil.getTodayEndTime());
        params.put("monthStartTime", DateUtil.getMonthStartTime());
        params.put("monthEndTime", DateUtil.getMonthEndTime());

        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> mapDay = new HashMap<String, Object>();
        Map<String, Object> mapMonth = new HashMap<String, Object>();
        Map<String, Object> mapWeek = new HashMap<String, Object>();

        //初始化一天24小时人员出入统计
        String[] enterCountDay = {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
        String[] outCountDay = {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
        String[] dateListDay = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};

        //初始化一个月31天人员出入统计
        String[] enterCountMonth = {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
        String[] outCountMonth = {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
        String[] dateListMonth = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};


        String[] enterCountWeek = {"45", "63", "54", "56", "76", "32", "33"};
        String[] outCountWeek = {"67", "23", "34", "23", "21", "56", "54"};
        String[] dateListWeek = {"1", "2", "3", "4", "5", "6", "7"};

        List<Map<String, Object>> mapListWeeks = personDao.personStreamWeek(params);

        int i = 0;
        for (Map<String, Object> mapListWeek : mapListWeeks) {
            String time = mapListWeek.get("openTime").toString();
            dateListWeek[i] = time;
            i++;
            String accessType = mapListWeek.get("accessType").toString();
            String count = mapListWeek.get("count").toString();


            if (accessType.equals("1")) {
                outCountWeek[i] = count;//判断进出状态：1-出
            } else if (accessType.equals("0")) {
                enterCountWeek[i] = count;//0-进
            }
        }

//        获取一天24小时人员出入状态
        List<Map<String, Object>> mapListDays = personDao.personStreamDay(params);
        for (Map<String, Object> mapListDay : mapListDays) {
            int hour = (Integer) mapListDay.get("openTime");
            int accessType = (Integer) mapListDay.get("accessType");
            int count = (Integer) mapListDay.get("count");
            if (accessType == 1) //判断进出状态：1-出
            {
                outCountDay[hour] = count + "";
            } else if (accessType == 0)//0-进
            {
                enterCountDay[hour] = count + "";
            }
        }


//        获取本月31天人员出入状态
        List<Map<String, Object>> mapListMonths = personDao.personStreamMonth(params);
        for (Map<String, Object> mapListMonth : mapListMonths) {
            int day = (Integer) mapListMonth.get("openTime");
            int accessType = (Integer) mapListMonth.get("accessType");
            int count = (Integer) mapListMonth.get("count");
            if (accessType == 1) //判断进出状态：1-出
            {
                outCountDay[day - 1] = count + "";
            } else if (accessType == 0)//0-进
            {
                enterCountDay[day - 1] = count + "";
            }
        }

        mapDay.put("outCount", outCountDay);
        mapDay.put("dateList", dateListDay);
        mapDay.put("enterCount", enterCountDay);

        mapMonth.put("outCount", outCountMonth);
        mapMonth.put("dateList", dateListMonth);
        mapMonth.put("enterCount", enterCountMonth);

        mapWeek.put("outCount", outCountWeek);
        mapWeek.put("dateList", dateListWeek);
        mapWeek.put("enterCount", enterCountWeek);

        map.put("day", mapDay);
        map.put("month", mapMonth);
        map.put("Week", mapWeek);

        return map;
    }

    @Override
    public Map<String, Object> personSense(Map<String, Object> params) throws OssRenderException {
        String timeTpye = (String) params.get("timeType");
        if (StringUtils.isBlank(timeTpye)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "timeTpye不能为空");
        }

        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> mapListPermanent = personDao.personSensePermanent(params);//常住

        List<Map<String, Object>> mapListFlow = personDao.personSenseFlow(params);//流动
        List<Map<String, Object>> mapListStranger = personDao.personSenseStrange(params);//陌生

        map.put("Permanent", mapListPermanent);
        map.put("Flow", mapListFlow);
        map.put("Stranger", mapListStranger);

        return map;
    }

    @Override
    public Map<String, Object> queryBaseInfoByPersonId(String personId) throws OssRenderException {

        List<Map<String, Object>> lists = personDao.queryBaseInfoByPersonId(personId);
        if (lists.size() > 0) {
            Map<String, Object> list = lists.get(0);
            if (list.get("image") != null && !list.get("image").toString().trim().equals("")) {
                String img = list.get("image").toString();
                String ip = PropertiesUtil.getLocalTomcatImageIp();
                img = ip + img;
                list.put("image", img);
            }
            if (list.get("nationalityName") == null || list.get("nationalityName").toString().trim().equals("")) {
                list.put("nationalityName", "中华人民共和国");
            }
            if (list.get("personId") != null && !list.get("personId").toString().trim().equals("")) {
                String id = list.get("personId").toString();
                List<Map<String, Object>> owner = personDao.getOwnerRoomByPersonId(id);//roomId liveType residence
                //此人是否有房产
                if (owner.size() > 0) {
                    for (Map<String, Object> map : owner) {
                        //判断居住类型 1业主 2 家庭成员  3 租户
                        if (map.get("liveType") != null && !map.get("liveType").toString().trim().equals("")) {
                            String ltp = map.get("liveType").toString();
                            int lt = Integer.parseInt(ltp);
                            //1 or 2  为 /
                            if (lt == 1 || lt == 2) list.put("residence", "/");
                            //租户 状态下 判断是否申请居住证
                            if (lt == 3) {
                                if (map.get("residence") != null && !map.get("residence").toString().trim().equals("")) {
                                    int re = Integer.parseInt(map.get("residence").toString());
                                    if (re == 0) list.put("residence", "未申请");
                                    if (re == 1) list.put("residence", "已申请");
                                } else list.put("residence", "未知");
                            }
                        }
                    }
                } else {//没有房产 显示未知
                    list.put("residence", "未知");
                }
            }
            return list;
        } else {
            return new HashMap<>();
        }
    }

    @Override
    public List<Map<String, Object>> queryPersonActivityStatistics(Map<String, Object> params) throws OssRenderException {
        List<Map<String, Object>> resultList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat weekStr = new SimpleDateFormat("EEEE", Locale.CHINA);
        Calendar t = Calendar.getInstance();
        int i = 0;
        t.add(Calendar.DAY_OF_MONTH, -6);
        while (i < 7) {
            Map<String, Object> map = new HashMap<>();
            List<Integer> halfHourPermanentNum = new ArrayList<>();
            List<Integer> halfHourFloatingNum = new ArrayList<>();

            t.set(Calendar.HOUR_OF_DAY, 0);
            t.set(Calendar.MINUTE, 0);
            t.set(Calendar.SECOND, 0);
            t.set(Calendar.MILLISECOND, 0);
            map.put("weekName", weekStr.format(t.getTime()));
            int j = 0;
            int rentAdd = 0;
            int permanentAdd = 0;
            while (j < 48) {//48个半小时
                params.put("startTime", sdf.format(t.getTime()));
                t.add(Calendar.MINUTE, 30);//30分钟统计一次
                params.put("endTime", sdf.format(t.getTime()));
                rentAdd = ownerRoomDao.queryRentOwnerNum(params);
                permanentAdd = ownerRoomDao.queryPermanentOwnerNum(params);
                halfHourFloatingNum.add(rentAdd);
                halfHourPermanentNum.add(permanentAdd);
                j++;
            }
            map.put("halfHourFloatingNum", halfHourFloatingNum);
            map.put("halfHourPermanentNum", halfHourPermanentNum);
            //t.add(Calendar.DAY_OF_MONTH,1);
            resultList.add(map);
            i++;
        }
        return resultList;
    }

    public static void main(String[] args) {
        Date date = new Date();
        SimpleDateFormat dateFm = new SimpleDateFormat("MM月dd日_EEEE", Locale.CHINA);
        System.out.println(dateFm.format(date));
    }

    @Override
    public List<Map<String, Object>> carInfo(String personId) throws OssRenderException {
        List<Map<String, Object>> lists = personDao.carInfo(personId);
        return lists;
    }

    @Override
    public List<Map<String, Object>> houseInfo(String personId) throws OssRenderException {
        return personDao.houseInfo(personId);
    }

    @Override
    public List<Map<String, Object>> queryPersonSpecialList(Map<String, Object> params) throws OssRenderException {
        if (params.get("xqCode") != null && !params.get("xqCode").toString().trim().equals("")) {
            return personDao.queryPersonSpecialListByXq(params);
        }
        if (params.get("subdistrictId") != null && !params.get("subdistrictId").toString().trim().equals("")) {
            return personDao.queryPersonSpecialListBySub(params);
        }
        if (params.get("areaId") != null && !params.get("areaId").toString().trim().equals("")) {
            return personDao.queryPersonSpecialListByArea(params);
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> faceType(String personId) throws OssRenderException {
        List<Map<String, Object>> lists = new ArrayList<>();
        lists = personDao.faceType(personId);
        if (lists == null || lists.size() == 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("faceTypeName", "[无记录]");
            lists.add(map);
        }
        return lists;
    }

    @Override
    public Map<String, Object> captureInfoByPersonId(String personId, Map<String, Object> params) throws OssRenderException {

        String todayYMDH = DateUtil.getTodayStartTime();
        String[] ts = todayYMDH.split("-");
        String year = ts[0];
        String month = ts[1];
        if (params.get("month") == null || params.get("month").toString().trim().equals(""))
            params.put("month", month);
        if (params.get("year") == null || params.get("year").toString().trim().equals(""))
            params.put("year", year);
        params.put("personId", personId);

        int y = Integer.parseInt(year);
        int m = Integer.parseInt(month);
        int days = DateUtil.getDaysByYearMonth(y, m);
        System.out.println(days);

        int[] capMonth = new int[days];//存放本月的抓拍记录
        String[] monthListParam = new String[days];//存放本月日期
        String[] monthWeek = new String[days];//存放本月日期对应的星期

        String timeStart = DateUtil.getMonthStartTime2();
        System.out.println(timeStart);
        for (int i = 0; i < days; i++) {
            String si = (i + 1) + "";
            if (i + 1 < 10) {
                si = "0" + si;
            }
            monthListParam[i] = timeStart + "-" + si;
        }

        Map<String, Object> mapMonth = new HashMap<>();//查询本月出门记录
        for (int i = 0; i < days; i++) {
//            System.out.println(monthListParam[i]);
            mapMonth.put("monthDay", monthListParam[i]); // ...14 15 16 17 18
            mapMonth.put("personId", personId);
            capMonth[i] = personDao.captureInfoByPersonId(mapMonth);//本月天每天出门量
        }
        for (int i = 0; i < days; i++) {//得到一个月的 星期日历
            monthWeek[i] = DateUtil.getWeek(monthListParam[i]);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");

        String[] monthList = new String[days];//日期格式化为 几月几日
        for (int i = 0; i < days; i++) {
            String[] date = monthListParam[i].split("-");
            monthList[i] = date[1] + "-" + date[2];
        }
        String[] weekList = new String[days]; // 星期日   -----》日
        for (int i = 0; i < days; i++) {
            String[] date = monthWeek[i].split("");
            weekList[i] = date[2];
        }


        Map<String, Object> map = new HashMap<>();
        Map<String, Object> record = new HashMap<>();
        record.put("monthDay", monthList);
        record.put("monthWeek", weekList);
        record.put("num", capMonth);


        map.put("year", params.get("year"));
        map.put("month", params.get("month"));
        map.put("record", record);
        return map;
    }

    @Override
    public List<Map<String, Object>> captureInfoDetailsByPersonId(String personId) {
        List<Map<String, Object>> listMaps = personDao.captureInfoDetailsByPersonId(personId);
        for (Map<String, Object> listMap : listMaps) {
            listMap.putIfAbsent("capImage", "无");
            listMap.putIfAbsent("faceImage", "无");
            listMap.putIfAbsent("similarity", "无");
        }
        return listMaps;
    }

    @Override
    public Map<String, Object> personRelationByPersonId(String personId) throws OssRenderException {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("img", FastdfsConstants.HTTP_PRODOCOL + "://" + FastdfsConstants.RES_HOST);
        map1.put("personId", personId);

        String roomId = personDao.personRoomIdByPersonId(personId);
        List<Map<String, Object>> lists = new ArrayList<>();
        if (roomId != null && !roomId.equals("")) {
            lists = personDao.getPersonByRoomId(roomId);//ownerId  liveType relation
        }
        Map<String, Object> owner = new HashMap<>();//存放业主 只1人
        List<Map<String, Object>> friends = new ArrayList<>();//存放业主朋友
        if (lists.size() > 0) {
            for (Map<String, Object> list : lists) {//遍历 lists  （同一个房屋下的人）
                if (list.get("liveType") != null && !list.get("liveType").toString().trim().equals("")) {
                    int lt = Integer.parseInt(list.get("liveType").toString());
                    if (lt == 1) {
                        owner = list;//业主
                    } else {
                        friends.add(list);//业主朋友
                    }
                }
            }
            //根据业主ownerId 查询 personName faceImage
            if (owner.get("ownerId") != null && !owner.get("ownerId").toString().trim().equals("")) {
                String ownerId = owner.get("ownerId").toString();
                List<Map<String, Object>> owners = personDao.personInfoByPersonId(ownerId);//得到owner 业主的个人信息
                for (Map<String, Object> person : owners) {
                    if (person.get("extend_s4") != null && !person.get("extend_s4").toString().trim().equals("")) {
                        String img = person.get("extend_s4").toString();
                        String ip = PropertiesUtil.getLocalTomcatImageIp();
                        img = ip + img;
                        owner.put("faceImage", img);//存放图片
                    }
                    if (person.get("name") != null && !person.get("name").toString().trim().equals("")) {
                        String name = person.get("name").toString();
                        owner.put("personName", name);//存放名字
                    }
                }
            }
            if (friends.size() > 0) {//业主是否有朋友
                for (Map<String, Object> friend : friends) {
                    if (friend.get("ownerId") != null && !friend.get("ownerId").toString().trim().equals("")) {
                        String ownerId = friend.get("ownerId").toString();
                        List<Map<String, Object>> fs = personDao.personInfoByPersonId(ownerId);//得到owner 每一个朋友的个人信息
                        for (Map<String, Object> f : fs) {//遍历每一个朋友
                            if (f.get("name") != null && !f.get("name").toString().trim().equals("")) {
                                String name = f.get("name").toString();
                                friend.put("personName", name);//存放名字
                            } else friend.put("personName", null);
                            if (f.get("extend_s4") != null && !f.get("extend_s4").toString().trim().equals("")) {
                                String img = f.get("extend_s4").toString();
                                String ip = PropertiesUtil.getLocalTomcatImageIp();
                                img = ip + img;
                                friend.put("faceImage", img);//存放图片
                            } else friend.put("faceImage", null);
                            if (f.get("manage_id") != null && !f.get("manage_id").toString().trim().equals("")) {
                                String personId1 = f.get("manage_id").toString();
                                friend.put("personId", personId1);//存放personId
                            }
                            List<Map<String, Object>> faces = personDao.faceType(ownerId);//根据ownerId 查询人脸库
                            if (faces.size() > 0) {
                                for (Map<String, Object> face : faces) {
                                    if (face.get("faceTypeName") != null && !face.get("faceTypeName").toString().trim().equals("")) {
                                        String name = face.get("faceTypeName").toString();
                                        friend.put("faceTypeName", name);//存放personId 的人脸库
                                    }
                                }
                            } else {
                                friend.put("faceTypeName", "[无记录]");
                            }
                        }
                    }
                }
            }
        }
        map.put("personName", owner.get("personName"));
        map.put("faceImage", owner.get("faceImage"));
        map.put("friend", friends);

        return map;
    }

    @Override
    public Map<String, Object> personStreamRule(Map<String, Object> params) throws OssRenderException {

        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> sevenDay = new HashMap<String, Object>();
        Map<String, Object> thirtyDay = new HashMap<String, Object>();

        String[] enterCountWeek = {"45", "63", "54", "56", "76", "32", "33"};
        String[] outCountWeek = {"67", "23", "34", "23", "21", "56", "54"};
        String[] dateListWeek = {"1", "2", "3", "4", "5", "6", "7"};

        String[] enterCountMonth = {"50", "45", "63", "54", "56", "76", "32", "67", "23", "34", "23", "21", "56", "54", "54", "56", "76", "32", "67", "23", "34", "34", "23", "21", "56", "54", "54", "56", "30", "60"};
        String[] outCountMonth = {"30", "51", "14", "30", "51", "14", "30", "51", "14", "30", "51", "14", "30", "15", "51", "14", "30", "51", "14", "51", "14", "30", "15", "14", "30", "15", "51", "14", "30", "15"};
        String[] dateListMonth = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};

        List<Map<String, Object>> mapListWeeks = personDao.personStreamWeek(params);
        int i = 0;
        for (Map<String, Object> mapListWeek : mapListWeeks) {
            String time = mapListWeek.get("openTime").toString();
            dateListWeek[i] = time;
            i++;
            String accessType = mapListWeek.get("accessType").toString();
            String count = mapListWeek.get("count").toString();


            if (accessType.equals("1")) {
                outCountWeek[i] = count;//判断进出状态：1-出
            } else if (accessType.equals("0")) {
                enterCountWeek[i] = count;//0-进
            }
        }

        List<Map<String, Object>> mapListMonths = personDao.personStreamMonth(params);
        int j = 0;
        for (Map<String, Object> mapListMonth : mapListMonths) {
            String time = mapListMonth.get("openTime").toString();
            dateListMonth[j] = time;
            j++;
            String accessType = mapListMonth.get("accessType").toString();
            String count = mapListMonth.get("count").toString();


            if (accessType.equals("1")) {
                outCountMonth[j] = count;//判断进出状态：1-出
            } else if (accessType.equals("0")) {
                enterCountMonth[j] = count;//0-进
            }
        }

        sevenDay.put("outCount", outCountWeek);
        sevenDay.put("enterCount", enterCountWeek);
        sevenDay.put("dateList", dateListWeek);

        thirtyDay.put("outCount", outCountMonth);
        thirtyDay.put("enterCount", enterCountMonth);
        thirtyDay.put("dateList", dateListMonth);

        map.put("sevenDay", sevenDay);
        map.put("thirtyDay", thirtyDay);

        return map;
    }

    @Override
    public Map<String, Object> strangerSense(Map<String, Object> params) throws OssRenderException {

        if (params.get("areaId") == null || params.get("areaId").toString().trim().equals("")) {
            params.put("areaId", 0);
        }

        if (params.get("page") == null || params.get("page").toString().trim().equals("")) {
            params.put("page", 1);
        }

        if (params.get("limit") == null || params.get("limit").toString().trim().equals("")) {
            params.put("limit", 10);
        }


        int start = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        params.put("start", (start - 1) * limit);
        params.put("end", limit);
        params.put("img", FastdfsConstants.HTTP_PRODOCOL + "://" + FastdfsConstants.RES_HOST);

        //int totalRows = personDao.totalStrangerSense(params);

        List<Map<String, Object>> lists = personDao.strangerSense(params);

        int totalRows = lists.size();
        int page = Integer.parseInt(params.get("page").toString().trim());
        int currentPage = page;
        int totalPage = totalRows / limit;
        if (totalRows % limit != 0) totalPage += 1;
        if (page > totalPage) currentPage = totalPage;
        if (page < 1) currentPage = 1;
        int i = 0;

        List nList = new LinkedList();

        for (Map<String, Object> list : lists) {
            if (i >= (currentPage - 1) * limit && i < currentPage * limit) {
                String id = list.get("id").toString();
                Map<String, Object> m = new HashMap<String, Object>();

                List<Map<String, Object>> maps = personDao.getPersonAttr(id);
                for (Map<String, Object> map : maps) {
                    list.put("sex", map.get("sex"));
                    list.put("age", map.get("age"));
                    list.put("image", map.get("image"));
                    list.put("xqCode", map.get("xqCode"));
                    list.put("capTime", map.get("capTime"));
                }
                nList.add(list);
            }
            i++;
        }

        for (Map<String, Object> list : lists) {
            if (list.get("sex") != null && !list.get("sex").toString().trim().equals("")) {
                int sex = Integer.parseInt(list.get("sex").toString());
                if (sex == 1) {
                    list.put("sex", "男");
                }
                if (sex == 2) {
                    list.put("sex", "女");
                }
            }
            if (list.get("image") != null && !list.get("image").toString().trim().equals("")) {
                String img = list.get("image").toString();
                String ip = PropertiesUtil.getLocalTomcatImageIp();
                img = ip + img;
                list.put("image", img);
            }
            if (list.get("xqCode") != null && !list.get("xqCode").toString().trim().equals("")) {
                String xqCode = list.get("xqCode").toString();
                String xqName = personDao.xqNameByXqCode(xqCode);
                list.put("xqName", xqName);
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("stranger", nList);
        map.put("currentPage", currentPage);
        map.put("totalRows", totalRows);
        return map;
    }

    @Override
    public Map<String, Object> personOwnerList(Map<String, Object> params) throws OssRenderException {

        if (params.get("areaId") == null || params.get("areaId").toString().trim().equals(""))
            params.put("areaId", 0);
        if (params.get("page") == null || params.get("page").toString().trim().equals(""))
            params.put("page", 1);
        if (params.get("limit") == null || params.get("limit").toString().trim().equals(""))
            params.put("limit", 10);

        if (params.get("nationalityName") != null && !params.get("nationalityName").toString().trim().equals("")) {
            String nationalityName = params.get("nationalityName").toString();
            if (!nationalityName.trim().equals("0") && !nationalityName.trim().equals("1")) {
                //nationalityName 0: 外国人   1：中国人   （flag:2）  根据nationalityName筛选具体国家名称
                params.put("flag", 2);
            }
        }
        if (params.get("color")!=null &&!params.get("color").toString().trim().equals("")){//根据颜色、事件类型  得到字典标签
            String color=params.get("color").toString();
            Map<String,Object> innerParams=new HashMap<>();
            if (color.equals("r")){
                innerParams.put("dictCode","crime_color");//事件类型
                innerParams.put("dictLabel",1);//颜色
            }
            if (color.equals("y")){
                innerParams.put("dictCode","crime_color");
                innerParams.put("dictLabel",2);
            }
            List<Map<String,Object>> lists=dictDao.getDictLabel(innerParams);//得到字典标签
            String codeLists="";//存放字典标签
            int k=0;
            if (lists.size()>0){
                Iterator li=lists.iterator();
                while (li.hasNext())
                {
                    Map lMap=(Map)li.next();
                    if(lMap.get("label")!=null&&!lMap.get("label").toString().trim().equals(""))
                    {
                        String perCodeList=lMap.get("label").toString();
                        if(k==0)
                        {
                            codeLists+="'"+perCodeList+"'";
                        }
                        else
                        {
                            codeLists+=",'"+perCodeList+"'";
                        }
                        k++;
                    }
                }
//                System.out.println(codeLists);
                params.put("codeLists",codeLists);
            }else {
                codeLists="''";
                params.put("codeLists",codeLists);
            }
        }


        int start = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        params.put("start", (start - 1) * limit);
        params.put("end", limit);
        params.put("img", FastdfsConstants.HTTP_PRODOCOL + "://" + FastdfsConstants.RES_HOST);

        int totalRows = personDao.totalPersonOwnerList(params);
        List<Map<String, Object>> lists = personDao.personOwnerList(params);
        for (Map<String, Object> list : lists) {
            if (list.get("image") != null && !list.get("image").toString().trim().equals("")) {//拼接图片地址
                String img = list.get("image").toString();
                String ip = PropertiesUtil.getLocalTomcatImageIp();
                img = ip + img;
                list.put("image", img);
            }
            if (list.get("roomId") != null && !list.get("roomId").toString().trim().equals("")) {//添加房屋字段
                String roomId = list.get("roomId").toString();
                String houseName = roomDao.queryRoomNameByManageId(roomId);
                list.put("houseName", houseName);
            } else {
                list.put("houseName", "/");
            }

            list.put("status", null);//初始化人员列表状态 0 白名单：正常人； 1 灰名单 ：租户没申请居住证； 2 黑名单①外国人②犯罪库

            if (list.get("liveType") != null && !list.get("liveType").toString().trim().equals("")) {
                int type = Integer.parseInt(list.get("liveType").toString());
                if (type == 1 || type == 2) {
                    list.put("residence", "/");
                    list.put("status", 0);//状态：白  正常人
                }
                if (type == 3) {
                    if (list.get("residence") != null && !list.get("residence").toString().trim().equals("")) {
                        int re = Integer.parseInt(list.get("residence").toString());
                        if (re == 0) {
                            list.put("residence", 0);//0 未申请
                            list.put("status", 1);//状态：灰  未申请居住证
                        } else {
                            list.put("residence", 1);//1 已申请
                            list.put("status", 0);//状态：白 已申请居住证
                        }
                    } else {
                        list.put("residence", 0);//residenct 为空 默认未申请
                        list.put("status", 1);//状态：灰  未申请居住证
                    }
                }
            }

            if (list.get("countryCode") == null || list.get("countryCode").toString().trim().equals("")) {//国别代号为空 默认是中国 1
                list.put("countryCode", 1);
            }

            int countryCode = Integer.parseInt(list.get("countryCode").toString());
            if (countryCode != 1) {
                list.put("status", 2);//状态：黑 外国人
            }

            if (list.get("nationalityName") == null || list.get("nationalityName").toString().trim().equals("")) {
                list.put("nationalityName", "中华人民共和国");
            }

            //找到实有人口中的重点关注对象，对其打上相应标签（红或黄）
            list.put("color", "g");//默认是绿色 好人
            if (list.get("faceTypeCode") != null && !list.get("faceTypeCode").toString().trim().equals("")) {
                String dictLabel = list.get("faceTypeCode").toString();//获取字典标签
                String dictCode="crime_type";//字典编码
                Map<String,Object> innerParams=new HashMap<>();
                innerParams.put("dictLabel",dictLabel);
                innerParams.put("dictCode",dictCode);
                String color=dictDao.getDictColor(innerParams);//根据字典编码、字典标签  得到对应颜色表示 ygy
                if (color.equals("1")){//1红  2黄
                    list.put("color","r");
                }else if (color.equals("2")){
                    list.put("color","y");
                }else if (color.equals("3")){
                    list.put("color","g");
                }
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", lists);
        map.put("currentPage", start);
        map.put("totalRows", totalRows);
        return map;
    }

    @Override
    public Map<String, Object> personQuitList(Map<String, Object> params) throws OssRenderException {

        Map<String, Object> map = new HashMap<String, Object>();

        List personList = personDao.quitPersonList(params);//获得疑似迁出人列表
        if (personList.size() > 0) { //翻页
            int totalRows = personList.size();
            int page = 1;
            int limit = 10;
            if (params.get("page") != null && !params.get("page").toString().trim().equals(""))
                page = Integer.parseInt(params.get("page").toString());
            if (params.get("limit") != null && !params.get("limit").toString().trim().equals(""))
                limit = Integer.parseInt(params.get("limit").toString());
            int currentPage = page;
            int totalPage = totalRows / limit;
            if (totalRows % limit != 0) totalPage += 1;
            if (page > totalPage) currentPage = totalPage;
            if (page < 1) currentPage = 1;
            List nList = new LinkedList(); //存放当前页 page 与限制数limit  列表
            int i = 0;

            Iterator pi = personList.iterator();
            while (pi.hasNext()) {//循环 迁出人列表
                Map tMap = (Map) pi.next();
                if (i >= (currentPage - 1) * limit && i < currentPage * limit) {//查询当前页 page 与限制数limit
                    Map innerParam;//存放再次查询信息参数

                    Map nMap = new LinkedHashMap(); //存放每条信息
                    nMap.put("xqCode", tMap.get("xqCode"));
                    nMap.put("ownerId", tMap.get("personId"));
                    nMap.put("name", tMap.get("personName"));
                    nMap.put("personId", tMap.get("personId"));

                    innerParam = new LinkedHashMap();

                    innerParam.put("xqCode", nMap.get("xqCode"));
                    List xqL = singleTableDao.selectHtXqInfo(innerParam);//根据xqCode 得到xq信息
                    String xqName = "";
                    if (xqL != null && !xqL.isEmpty()) {
                        Map xqInfoMap = (Map) xqL.get(0);
                        if (xqInfoMap.get("name") != null && !xqInfoMap.get("name").toString().trim().equals("")) {
                            xqName = xqInfoMap.get("name").toString();
                        }
                    }
                    nMap.put("xqName", xqName);

                    innerParam = new HashMap();
                    innerParam.put("personId", tMap.get("personId"));
                    List zsPersonInfoL = singleTableDao.selectZsPersonInfo(innerParam);//根据personId 得到person信息
                    String phone = "";
                    String image = "";
                    if (zsPersonInfoL != null && !zsPersonInfoL.isEmpty()) {
                        Map zsPersonInfoMap = (Map) zsPersonInfoL.get(0);
                        if (zsPersonInfoMap.get("phone") != null && !zsPersonInfoMap.get("phone").toString().trim().equals("")) {
                            phone = zsPersonInfoMap.get("phone").toString();
                        }
                        if (zsPersonInfoMap.get("extend_s4") != null && !zsPersonInfoMap.get("extend_s4").toString().trim().equals("")) {
                            image = PropertiesUtil.getLocalTomcatImageIp() + zsPersonInfoMap.get("extend_s4").toString();
                        }
                    }
                    nMap.put("phone", phone);
                    nMap.put("image", image);

                    innerParam = new HashMap();
                    innerParam.put("ownerId", tMap.get("personId"));
                    List houseRoomOwnerL = singleTableDao.selectHouseOwnerRoom(innerParam);//根据personId得到 房屋信息
                    String checkinDate = "";
                    String liveTypyName = "";
                    String roomId = "";
                    String houseName = "";
                    if (houseRoomOwnerL != null && !houseRoomOwnerL.isEmpty()) {
                        Map houseRoomOwnerMap = (Map) houseRoomOwnerL.get(0);
                        if (houseRoomOwnerMap.get("checkin_time") != null && !houseRoomOwnerMap.get("checkin_time").toString().trim().equals("")) {
                            checkinDate = houseRoomOwnerMap.get("checkin_time").toString();
                        }
                        if (houseRoomOwnerMap.get("live_type_name") != null && !houseRoomOwnerMap.get("live_type_name").toString().trim().equals("")) {
                            liveTypyName = houseRoomOwnerMap.get("live_type_name").toString();
                        }
                        if (houseRoomOwnerMap.get("room_id") != null && !houseRoomOwnerMap.get("room_id").toString().trim().equals("")) {
                            roomId = houseRoomOwnerMap.get("room_id").toString();
                            houseName = roomDao.queryRoomNameByManageId(roomId);
                        }
                    }

                    nMap.put("checkinDate", checkinDate);
                    nMap.put("houseName", houseName);
                    nMap.put("liveTypyName", liveTypyName);
                    nMap.put("roomId", roomId);
                    //疑似迁出 1、人未感知时常>15  && 2、车未感知时长>15
                    innerParam = new HashMap();

                    innerParam.put("ownerId", tMap.get("personId"));
                    List carL = singleTableDao.selectCarInfo(innerParam);//根据personId /ownerId 得到汽车信息 车牌号
                    String carNum = "";
                    if (carL != null && !carL.isEmpty()) {
                        Map carMap = (Map) carL.get(0);
                        if (carMap.get("car_num") != null && !carMap.get("car_num").toString().trim().equals("")) {
                            carNum = carMap.get("car_num").toString();
                        }
                    }
                    //nMap.put("carNum",carNum);

                    innerParam = new HashMap();
                    innerParam.put("carNum", carNum);
                    if (params.get("daySmall") != null && !params.get("daySmall").toString().trim().equals(""))
                        innerParam.put("daySmall", params.get("daySmall"));
                    List carSenceL = personDao.quitCarList(innerParam);//根据车牌号 得到 此人所属车辆的未感知时长
                    String lastCapTimeCar = "";
                    int noSenceTimeCar = -1;
                    if (carSenceL != null && !carSenceL.isEmpty()) {
                        Map carSenseMap = (Map) carSenceL.get(0);
                        if (carSenseMap.get("lastCapTimeCar") != null && !carSenseMap.get("lastCapTimeCar").toString().trim().equals("")) {
                            lastCapTimeCar = carSenseMap.get("lastCapTimeCar").toString();//末次抓拍时间
                        }
                        if (carSenseMap.get("noSenceTimeCar") != null && !carSenseMap.get("noSenceTimeCar").toString().trim().equals("")) {
                            noSenceTimeCar = Integer.parseInt(carSenseMap.get("noSenceTimeCar").toString());//未感知时长
                        }
                    }

                    if (noSenceTimeCar != -1 && !lastCapTimeCar.equals("")) {
                        int noSenceTime = Integer.parseInt(tMap.get("noSenceTime").toString());
                        if (noSenceTime < noSenceTimeCar) {//选择人 与车 未感知时长的最小值
                            nMap.put("noSenceTime", tMap.get("noSenceTime"));
                            nMap.put("lastCapTime", tMap.get("lastCapTime"));
                        } else {
                            nMap.put("noSenceTime", noSenceTimeCar);
                            nMap.put("lastCapTime", lastCapTimeCar);
                        }
                    } else {//无车情况
                        nMap.put("noSenceTime", tMap.get("noSenceTime"));
                        nMap.put("lastCapTime", tMap.get("lastCapTime"));
                    }

                    nList.add(nMap);
                }
                i++;
            }


            map.put("list", nList);
            map.put("currentPage", currentPage);
            map.put("totalRows", totalRows);
        } else {
            map.put("list", new LinkedList<>());
            map.put("currentPage", 1);
            map.put("totalRows", 0);
        }

        return map;
    }

    @Override
    public Map<String, Object> personQuitList2(Map<String, Object> params) throws OssRenderException {
        Map<String, Object> map = new HashMap<String, Object>();

        List personList = personDao.quitPersonAndCarList(params);//获得疑似迁出人列表
        if (personList.size() > 0) { //翻页
            int totalRows = personList.size();
            int page = 1;
            int limit = 10;
            if (params.get("page") != null && !params.get("page").toString().trim().equals(""))
                page = Integer.parseInt(params.get("page").toString());
            if (params.get("limit") != null && !params.get("limit").toString().trim().equals(""))
                limit = Integer.parseInt(params.get("limit").toString());
            int currentPage = page;
            int totalPage = totalRows / limit;
            if (totalRows % limit != 0) totalPage += 1;
            if (page > totalPage) currentPage = totalPage;
            if (page < 1) currentPage = 1;
            List nList = new LinkedList(); //存放当前页 page 与限制数limit  列表
            int i = 0;

            Iterator pi = personList.iterator();
            while (pi.hasNext()) {//循环 迁出人列表
                Map tMap = (Map) pi.next();
                if (i >= (currentPage - 1) * limit && i < currentPage * limit) {//查询当前页 page 与限制数limit
                    Map innerParam;//存放再次查询信息参数

                    Map nMap = new LinkedHashMap(); //存放每条信息
                    nMap.put("xqCode", tMap.get("xqCode"));
                    nMap.put("ownerId", tMap.get("personId"));
                    nMap.put("name", tMap.get("personName"));
                    nMap.put("personId", tMap.get("personId"));

                    innerParam = new LinkedHashMap();

                    innerParam.put("xqCode", nMap.get("xqCode"));
                    List xqL = singleTableDao.selectHtXqInfo(innerParam);//根据xqCode 得到xq信息
                    String xqName = "";
                    if (xqL != null && !xqL.isEmpty()) {
                        Map xqInfoMap = (Map) xqL.get(0);
                        if (xqInfoMap.get("name") != null && !xqInfoMap.get("name").toString().trim().equals("")) {
                            xqName = xqInfoMap.get("name").toString();
                        }
                    }
                    nMap.put("xqName", xqName);

                    innerParam = new HashMap();
                    innerParam.put("personId", tMap.get("personId"));
                    List zsPersonInfoL = singleTableDao.selectZsPersonInfo(innerParam);//根据personId 得到person信息
                    String phone = "";
                    String image = "";
                    if (zsPersonInfoL != null && !zsPersonInfoL.isEmpty()) {
                        Map zsPersonInfoMap = (Map) zsPersonInfoL.get(0);
                        if (zsPersonInfoMap.get("phone") != null && !zsPersonInfoMap.get("phone").toString().trim().equals("")) {
                            phone = zsPersonInfoMap.get("phone").toString();
                        }
                        if (zsPersonInfoMap.get("extend_s4") != null && !zsPersonInfoMap.get("extend_s4").toString().trim().equals("")) {
                            image = PropertiesUtil.getLocalTomcatImageIp() + zsPersonInfoMap.get("extend_s4").toString();
                        }
                    }
                    nMap.put("phone", phone);
                    nMap.put("image", image);

                    innerParam = new HashMap();
                    innerParam.put("ownerId", tMap.get("personId"));
                    List houseRoomOwnerL = singleTableDao.selectHouseOwnerRoom(innerParam);//根据personId得到 房屋信息
                    String checkinDate = "";
                    String liveTypyName = "";
                    String roomId = "";
                    String houseName = "";
                    if (houseRoomOwnerL != null && !houseRoomOwnerL.isEmpty()) {
                        Map houseRoomOwnerMap = (Map) houseRoomOwnerL.get(0);
                        if (houseRoomOwnerMap.get("checkin_time") != null && !houseRoomOwnerMap.get("checkin_time").toString().trim().equals("")) {
                            checkinDate = houseRoomOwnerMap.get("checkin_time").toString();
                        }
                        if (houseRoomOwnerMap.get("live_type_name") != null && !houseRoomOwnerMap.get("live_type_name").toString().trim().equals("")) {
                            liveTypyName = houseRoomOwnerMap.get("live_type_name").toString();
                        }
                        if (houseRoomOwnerMap.get("room_id") != null && !houseRoomOwnerMap.get("room_id").toString().trim().equals("")) {
                            roomId = houseRoomOwnerMap.get("room_id").toString();
                            houseName = roomDao.queryRoomNameByManageId(roomId);
                        }
                    }
                    nMap.put("checkinDate", checkinDate);
                    nMap.put("houseName", houseName);
                    nMap.put("liveTypyName", liveTypyName);
                    nMap.put("roomId", roomId);
                    nMap.put("noSenceTime",tMap.get("noSenceTimeMin"));
                    nMap.put("lastCapTime",tMap.get("lastCapTimeLater"));

                    nList.add(nMap);
                }
                i++;
            }
            map.put("list", nList);
            map.put("currentPage", currentPage);
            map.put("totalRows", totalRows);
        } else {
            map.put("list", new LinkedList<>());
            map.put("currentPage", 1);
            map.put("totalRows", 0);
        }
        return map;
    }

    @Override
    public Map<String, Object> personAddedList(Map<String, Object> params) throws OssRenderException {
        if (params.get("areaId") == null || params.get("areaId").toString().trim().equals(""))
            params.put("areaId", 0);
        if (params.get("page") == null || params.get("page").toString().trim().equals(""))
            params.put("page", 1);
        if (params.get("limit") == null || params.get("limit").toString().trim().equals(""))
            params.put("limit", 10);

        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String,Object>> lists=personDao.personAddedList(params);

        if (lists.size()>0){
            int totalRows = lists.size();
            int page = 1;
            int limit = 10;
            if (params.get("page") != null && !params.get("page").toString().trim().equals(""))
                page = Integer.parseInt(params.get("page").toString());
            if (params.get("limit") != null && !params.get("limit").toString().trim().equals(""))
                limit = Integer.parseInt(params.get("limit").toString());
            int currentPage = page;
            int totalPage = totalRows / limit;
            if (totalRows % limit != 0) totalPage += 1;
            if (page > totalPage) currentPage = totalPage;
            if (page < 1) currentPage = 1;
            List nList = new LinkedList(); //存放当前页 page 与限制数limit  列表
            int i=0;
            for (Map<String,Object> list:lists){
                if (i >= (currentPage - 1) * limit && i < currentPage * limit){
                    Map innerParam;//存放再次查询信息参数
                    Map nMap = new LinkedHashMap(); //存放每条信息

                    nMap.put("capDays",list.get("capDays"));//存放抓拍天数

                    String personId="";//根据personId 获得抓怕信息
                    if (list.get("personId")!=null && !list.get("personId").toString().trim().equals("")){
                        personId=list.get("personId").toString();
                    }
                    innerParam=new HashMap();//相同personId  会有不同age情况 限制personId 的age 获得抓怕信息
                    if (params.get("age")!=null &&!params.get("age").toString().trim().equals("")){
                        innerParam.put("age",params.get("age"));
                    }
                    innerParam.put("personId",personId);
                    List<Map<String,Object>> personAttrs=personDao.getPersonAttrByPersonId(innerParam);
                    Map<String,Object> personAttr=personAttrs.get(0);
                    String sex="";
                    String age="";
                    String xqCode="";
                    String image="";
                    String xqName="";
                    if (personAttr.get("sex")!=null && !personAttr.get("sex").toString().trim().equals("")){
                        sex=personAttr.get("sex").toString();
                        if (sex.equals("1")){
                            sex="男";
                        }
                        if (sex.equals("2")){
                            sex="女";
                        }
                    }
                    if (personAttr.get("age")!=null && !personAttr.get("age").toString().trim().equals("")){
                        age=personAttr.get("age").toString();
                    }
                    if (personAttr.get("xqCode")!=null && !personAttr.get("xqCode").toString().trim().equals("")){
                        xqCode=personAttr.get("xqCode").toString();
                        xqName=personDao.xqNameByXqCode(xqCode);
                    }
                    if (personAttr.get("image")!=null && !personAttr.get("image").toString().trim().equals("")){
                        image=personAttr.get("image").toString();
                        String ip=PropertiesUtil.getLocalTomcatImageIp();
                        image=ip+image;
                    }

                    nMap.put("sex",sex);
                    nMap.put("personId",personId);
                    nMap.put("age",age);
                    nMap.put("xqCode",xqCode);
                    nMap.put("xqName",xqName);
                    nMap.put("image",image);
                    nList.add(nMap);
                }
                i++;
            }
            map.put("list", nList);
            map.put("currentPage", currentPage);
            map.put("totalRows", totalRows);
        }else {
            map.put("list", new LinkedList<>());
            map.put("currentPage", 1);
            map.put("totalRows", 0);
        }
        return map;
    }

    @Override
    public List<Map<String, Object>> querySpecialPersonDistribution(Map<String, Object> params) throws OssRenderException {
        if (params.get("xqCode") != null && !params.get("xqCode").toString().trim().equals("")) {
            return personDao.querySpecialPersonDistributionByXq(params);
        }
        if (params.get("subdistrictId") != null && !params.get("subdistrictId").toString().trim().equals("")) {
            return personDao.querySpecialPersonDistributionBySub(params);
        }
        if (params.get("areaId") != null && !params.get("areaId").toString().trim().equals("")) {
            return personDao.querySpecialPersonDistributionByArea(params);
        }
        return null;
    }


    @Override
    public Map<String, Object> foreignerSense(Map<String, Object> params) throws OssRenderException {
        if (params.get("areaId") == null || params.get("areaId").toString().trim().equals(""))
            params.put("areaId", 0);
        if (params.get("page") == null || params.get("page").toString().trim().equals(""))
            params.put("page", 1);
        if (params.get("limit") == null || params.get("limit").toString().trim().equals(""))
            params.put("limit", 10);
        if (params.get("liveType") == null || params.get("liveType").toString().trim().equals(""))
            params.put("liveType", 1);

        int start = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        params.put("start", (start - 1) * limit);
        params.put("end", limit);

        Map<String, Object> map = new HashMap<String, Object>();
        int totalRows = personDao.totalForeignerSense(params);
        List<Map<String, Object>> lists = personDao.foreignerSense(params);
        if (totalRows == 0) {
            map.put("stranger", "无外籍人员");//stranger ：与前端对接字段名  本应为list
            map.put("currentPage", start);
            map.put("totalRows", totalRows);
        } else {
            for (Map<String, Object> list : lists) {
                if (list.get("image") != null && !list.get("image").toString().trim().equals("")) {
                    String img = list.get("image").toString();
                    String ip = PropertiesUtil.getLocalTomcatImageIp();
                    img = ip + img;
                    list.put("image", img);
                }
                if (list.get("roomId") != null && !list.get("roomId").toString().trim().equals("")) {
                    String roomId = list.get("roomId").toString();
                    String houseName = roomDao.queryRoomNameByManageId(roomId);
                    list.put("houseName", houseName);
                } else {
                    list.put("houseName", "");
                }
            }
            map.put("stranger", lists);
            map.put("currentPage", start);
            map.put("totalRows", totalRows);
        }
        return map;
    }

    @Override
    public List<Map<String, Object>> personAbnormalType(String personId) throws OssRenderException {
        List<Map<String, Object>> lists = new ArrayList<>();
        lists = personDao.personAbnormalType(personId);
        if (lists == null || lists.size() == 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("abnormalTypeName", "[无记录]");
            lists.add(map);
        }
        return lists;
    }

    @Override
    public Map<String, Object> personHouseBypersonId(String personId) throws OssRenderException {

        if (StringUtils.isBlank(personId)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "personId不能为空");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> mapPerson = personDao.personHouseByPersonId(personId);
        if (mapPerson != null) {
            String name = mapPerson.get("houseOwnerName").toString();
            String num = mapPerson.get("houseOwnerPaperNum").toString();
            map.put("houseOwnerName", name);
            map.put("houseOwnerPaperNum", num);
        }
        List<Map<String, Object>> lists = new ArrayList<>();
        lists = personDao.houseListByPersonId(personId);

        if (lists.size() > 0) {
            for (Map<String, Object> list : lists) {
                if (list.get("roomId") != null && !list.get("roomId").toString().trim().equals("")) {
                    String roomId = list.get("roomId").toString();
                    String house = roomDao.queryRoomNameByManageId(roomId);
                    list.put("houseName", house);
                }
                if (list.get("personType") != null && !list.get("roomId").toString().trim().equals("")) {
                    int type = Integer.parseInt(list.get("personType").toString());
                    if (type == 1) {
                        list.put("personType", "业主");
                    }
                    if (type == 2) {
                        list.put("personType", "业主的家庭成员");
                    }
                    if (type == 3) {
                        list.put("personType", "租户");
                    }
                }
            }
            map.put("houses", lists);
        } else {
            map.put("houses", "此人无房屋");
        }

        return map;
    }

    @Override
    public Map<String, Object> personAccess(Map<String, Object> params) throws OssRenderException {

        if (params.get("areaId") == null || params.get("areaId").toString().trim().equals(""))
            params.put("areaId", 0);
        if (params.get("page") == null || params.get("page").toString().trim().equals(""))
            params.put("page", 1);
        if (params.get("limit") == null || params.get("limit").toString().trim().equals(""))
            params.put("limit", 10);
        int start = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        params.put("start", (start - 1) * limit);
        params.put("end", limit);
        params.put("img", FastdfsConstants.HTTP_PRODOCOL + "://" + FastdfsConstants.RES_HOST);

//       int totalRows = personDao.personAccessTotal(params);数量太多 限制100条
        List<Map<String, Object>> lists = personDao.personAccess(params);
        for (Map<String, Object> list : lists) {
            if (list.get("access") != null && !list.get("access").toString().trim().equals("")) {
                int type = Integer.parseInt(list.get("access").toString());
                if (type == 1) {
                    list.put("access", "进门");
                }
                if (type == 0) {
                    list.put("access", "出门");
                }
            }
            if (list.get("roomId") != null && !list.get("roomId").toString().trim().equals("")) {

                String roomId = list.get("roomId").toString();
                String roomName = roomDao.queryRoomNameByManageId(roomId);
                list.put("roomName", roomName);
            }

            if (list.get("image") != null && !list.get("image").toString().trim().equals("")) {
                String img = list.get("image").toString();
                String ip = PropertiesUtil.getLocalTomcatImageIp();
                img = ip + img;
                list.put("image", img);
            }
            if (list.get("xqCode") != null && !list.get("xqCode").toString().trim().equals("")) {
                String xqCode = list.get("xqCode").toString();
                String xqName = personDao.xqNameByXqCode(xqCode);
                list.put("xqName", xqName);
            }
        }
//        //添加今日人员出入数量
//        int todayTotalRows=0;
//        todayTotalRows = personDao.personAccessTodayTotal(params);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", lists);
        map.put("currentPage", start);
        map.put("totalRows", 100);
//        map.put("todayTotalRows",todayTotalRows);

        return map;
    }

    @Override
    public Map<String, Object> personSenseHighAccess(Map<String, Object> params) throws OssRenderException {

        if (params.get("areaId") == null || params.get("areaId").toString().trim().equals(""))
            params.put("areaId", 0);
        if (params.get("page") == null || params.get("page").toString().trim().equals(""))
            params.put("page", 1);
        if (params.get("limit") == null || params.get("limit").toString().trim().equals(""))
            params.put("limit", 10);
        if (params.get("timeType") == null || params.get("timeType").toString().trim().equals(""))
            params.put("timeType", "month");

        int start = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        params.put("start", (start - 1) * limit);
        params.put("end", limit);
        params.put("img", FastdfsConstants.HTTP_PRODOCOL + "://" + FastdfsConstants.RES_HOST);

        int totalRows = personDao.personSenseHighAccessTotal(params);
        List<Map<String, Object>> lists = personDao.personSenseHighAccess(params);

        for (Map<String, Object> list : lists) {
            if (list.get("personType") != null && !list.get("personType").toString().trim().equals("")) {
                int personType = Integer.parseInt(list.get("personType").toString());
                if (personType == 1) {
                    list.put("personType", "房主及家庭成员");

                }
                if (personType == 2) {
                    list.put("personType", "房主及家庭成员");
                }
                if (personType == 3) {
                    list.put("personType", "租户");
                }
            } else {
                list.put("personType", "陌生人");
            }
            if (list.get("image") != null && !list.get("image").toString().trim().equals("")) {
                String img = list.get("image").toString();
                String ip = PropertiesUtil.getLocalTomcatImageIp();
                img = ip + img;
                list.put("image", img);
            }

            if (list.get("roomId") != null && !list.get("roomId").toString().trim().equals("")) {
                String roomId = list.get("roomId").toString();
                String houseName = roomDao.queryRoomNameByManageId(roomId);
                list.put("houseName", houseName);
            } else {
                list.put("houseName", "");
            }

            if (list.get("xqCode") != null && !list.get("xqCode").toString().trim().equals("")) {
                String xqCode = list.get("xqCode").toString();

                String xqName = personDao.xqNameByXqCode(xqCode);
                list.put("xqName", xqName);
            } else {
                list.put("xqName", "");
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", lists);
        map.put("currentPage", start);
        map.put("totalRows", totalRows);
        return map;
    }

    @Override
    public Map<String, Object> personHighAccessByPersonId(Map<String, Object> params) throws OssRenderException {
        if (params.get("personId") == null || StringUtils.isBlank(params.get("personId").toString())) {//处理初始值
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "缺少personId参数");
        }
        String personId = params.get("personId").toString();
        if (params.get("page") == null || params.get("page").toString().trim().equals("")) {
            params.put("page", 1);
        }
        if (params.get("limit") == null || params.get("limit").toString().trim().equals("")) {
            params.put("limit", 10);
        }

        Map<String, Object> innerParams = new HashMap<>();//查询
        innerParams.put("personId", personId);
        List<Map<String, Object>> lists = personDao.personHighAccessByPersonId(innerParams);

        Map<String, Object> map = new HashMap<String, Object>();

        if (lists.size() > 0) {//分页处理
            int totalRows = lists.size();
            int page = Integer.parseInt(params.get("page").toString().trim());
            int limit = Integer.parseInt(params.get("limit").toString().trim());
            int currentPage = page;
            int totalPage = totalRows / limit;
            if (totalRows % limit != 0) totalPage += 1;
            if (page > totalPage) currentPage = totalPage;
            if (page < 1) currentPage = 1;

            int i = 0;
            List nList = new LinkedList();
            for (Map<String, Object> list : lists) {
                if (i >= (currentPage - 1) * limit && i < currentPage * limit) {//查询当前页 page 与限制数limit

                    Map nMap = new LinkedHashMap(); //存放每条信息
                    nMap.put("capTime", list.get("capTime"));
                    nMap.put("xqName", list.get("xqName"));
                    nMap.put("capAddress", list.get("capAddress"));

                    nList.add(nMap);

                }
                i++;
                map.put("list", nList);
                map.put("currentPage", currentPage);
                map.put("totalRows", totalRows);
            }
        } else {
            map.put("list", new LinkedList<>());
            map.put("currentPage", 1);
            map.put("totalRows", 0);
        }
        return map;
    }

    @Override
    public Map<String, Object> personStrangerByPersonId(Map<String, Object> params) throws OssRenderException {
        if (params.get("personId") == null || StringUtils.isBlank(params.get("personId").toString())) {//处理初始值
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "缺少personId参数");
        }
        String personId = params.get("personId").toString();
        if (params.get("page") == null || params.get("page").toString().trim().equals("")) {
            params.put("page", 1);
        }
        if (params.get("limit") == null || params.get("limit").toString().trim().equals("")) {
            params.put("limit", 10);
        }

        Map<String, Object> innerParams = new HashMap<>();//查询
        innerParams.put("personId", personId);
        List<Map<String, Object>> lists = personDao.personStrangerByPersonId(innerParams);

        Map<String, Object> map = new HashMap<String, Object>();

        if (lists.size() > 0) {//分页处理
            int totalRows = lists.size();
            int page = Integer.parseInt(params.get("page").toString().trim());
            int limit = Integer.parseInt(params.get("limit").toString().trim());
            int currentPage = page;
            int totalPage = totalRows / limit;
            if (totalRows % limit != 0) totalPage += 1;
            if (page > totalPage) currentPage = totalPage;
            if (page < 1) currentPage = 1;

            int i = 0;
            List nList = new LinkedList();
            for (Map<String, Object> list : lists) {
                if (i >= (currentPage - 1) * limit && i < currentPage * limit) {//查询当前页 page 与限制数limit

                    Map nMap = new LinkedHashMap(); //存放每条信息
                    nMap.put("capTime", list.get("capTime"));
                    nMap.put("xqName", list.get("xqName"));
                    nMap.put("capAddress", list.get("capAddress"));

                    nList.add(nMap);

                }
                i++;
                map.put("list", nList);
                map.put("currentPage", currentPage);
                map.put("totalRows", totalRows);
            }
        } else {
            map.put("list", new LinkedList<>());
            map.put("currentPage", 1);
            map.put("totalRows", 0);
        }
        return map;
    }

    @Override
    public List<Map<String, Object>> personStreamRules3(Map<String, Object> params) {
        return personDao.personStreamRules3(params);
    }

    @Override
    public Map<String, Object> personStreamRules(Map<String, Object> params) throws OssRenderException, ParseException {

        String personId = null;
        if (params.get("areaId") == null || params.get("areaId").toString().trim().equals(""))
            params.put("areaId", 0);
        if (params.get("personId") != null && !params.get("personId").toString().trim().equals(""))
            personId = params.get("personId").toString();
//////////////////////////////////////////////////////////////////////////////////以天为单位统计近7天的进门出门记录
        int[] outWeek = new int[7];//存放最近7天的出门记录
        int[] inWeek = new int[7];//存放最近7天的进门记录
        String[] weekListParam = new String[7];//存放七天日期

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//获取最近七天日期
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < 7; i++) {
            Date date = cal.getTime();
            weekListParam[i] = sdf.format(date);  //17 16 15 14 13 12 11
            cal.add(Calendar.DATE, -1);
        }

        String[] weekList = new String[7];//七天 星期格式
        for (int i = 0; i < 7; i++) {
            weekList[i] = DateUtil.getWeek(weekListParam[6 - i]);
        }

        Map<String, Object> mapWeek = new HashMap<>();//查询七天出门记录
        for (int i = 0; i < 7; i++) {
            mapWeek.put("weekTime", weekListParam[6 - i]);
            mapWeek.put("personId", personId);
            outWeek[i] = personDao.personStreamRulesWeekOut(mapWeek);//七天每天出门量
            inWeek[i] = personDao.personStreamRulesWeekIn(mapWeek);//七天每天进门量
        }
/////////////////////////////////////////////////////////////////////////////////////以小时为单位统计本天的进门出门记录

        int[] outDay = new int[24];//本日24小时出门量
        int[] inDay = new int[24];//本日24小时进门量
        String[] dayListParam = new String[24];//作为查询传参

        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = df.format(day);
        Date date = df.parse(s);
        for (int i = 0; i < 24; i++) {
            cal.setTime(date);
            dayListParam[i] = format.format(date);
            cal.add(Calendar.HOUR, 1);
            date = cal.getTime();
        }
        Map<String, Object> mapDay = new HashMap<>();//查询七天出门记录
        for (int i = 0; i < 24; i++) {
            mapDay.put("dayTime", dayListParam[i]);
            outDay[i] = personDao.personStreamRulesDaysOut(mapDay);//七天每天出门量
            inDay[i] = personDao.personStreamRulesDaysIn(mapDay);//七天每天进门量
        }
        int[] dayList = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
                18, 19, 20, 21, 22, 23};
/////////////////////////////////////////////////////////////////////////////////////////////以天为单位统计近30天的进门出门记录

        int[] outThirty = new int[30];//存放最近30天的出门记录
        int[] inThirty = new int[30];//存放最近30天的进门记录
        String[] thirtyListParam = new String[30];//存放30天日期

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");//获取最近30天日期
        Calendar cal2 = Calendar.getInstance();
        for (int i = 0; i < 30; i++) {
            Date dates = cal2.getTime();
            thirtyListParam[i] = sdf2.format(dates);
            cal2.add(Calendar.DATE, -1); //18 17 16 15 14...
        }

        Map<String, Object> mapThirty = new HashMap<>();//查询30天出门记录
        for (int i = 0; i < 30; i++) {
            mapThirty.put("thirtyDay", thirtyListParam[29 - i]); // ...14 15 16 17 18
            mapThirty.put("personId", personId);
            outThirty[i] = personDao.personStreamRulesThirtyDaysOut(mapThirty);//30天每天出门量
            inThirty[i] = personDao.personStreamRulesThirthyDaysIn(mapThirty);//30天每天进门量
        }

        String temp = null;
        int length = thirtyListParam.length;
        for (int i = 0; i < length / 2; i++)        //日期逆序输出
        {
            temp = thirtyListParam[i];
            thirtyListParam[i] = thirtyListParam[length - i - 1];
            thirtyListParam[length - i - 1] = temp;
        }

        String[] thirtyList = new String[30];//日期格式化为 几月几日
        for (int i = 0; i < 30; i++) {
            String[] sp = thirtyListParam[i].split("-");
            thirtyList[i] = sp[1] + "-" + sp[2];
        }
////////////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////////////
        Map<String, Object> thirtyMap = new HashMap<>();//30天
        thirtyMap.put("outCount", outThirty);
        thirtyMap.put("enterCount", inThirty);
        thirtyMap.put("dateList", thirtyList);


        Map<String, Object> weekMap = new HashMap<>();//周
        weekMap.put("outCount", outWeek);
        weekMap.put("enterCount", inWeek);
        weekMap.put("dateList", weekList);

        Map<String, Object> dayMap = new HashMap<>();//24h
        dayMap.put("outCount", outDay);
        dayMap.put("enterCount", inDay);
        dayMap.put("dateList", dayList);
/////////////////////////////////////////////////////////////////////////////
        Map<String, Object> map = new HashMap<>();
        map.put("week", weekMap);//近7天
        map.put("day", dayMap);//一天24小时
        map.put("thirty", thirtyMap);//近30天

        return map;
    }

    @Override
    public Map<String, Object> personStreamRule9(String personId) throws OssRenderException {
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (personId != null && !StringUtils.isBlank(personId)) {
            params.put("personId", personId);
        }

        String[] dateListWeek = new String[7];//存放近7天日期  星期格式

        ArrayList<String> weekDays = DateUtil.getDays(7);//得到近7天日期
        Iterator<String> wdi = weekDays.iterator();
        int j = 0;
        while (wdi.hasNext()) {
            String wdt = wdi.next();
            String wds = DateUtil.getWeek(wdt);
            dateListWeek[j] = wds;//得到星期格式
            j++;
        }
        resultMap.put("time", dateListWeek);
        int[][] enter = new int[7][48];//近7天 每天48个段的 入 记录
        int[][] out = new int[7][48];//近7天 每天48个段的 出 记录
        int i = 0;
        int access = 49;//zs_open_record 表 字段open_type 49为出门 其余为进门
        int[] hour48 = {0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 13, 13, 14, 14, 15, 15, 16, 16, 17, 17, 18, 18, 19, 19, 20, 20,
                21, 21, 22, 22, 23, 23};
        String[] half1 = {"00", "30"};
        String[] half2 = {"29", "59"};
        String beginTime;
        String endTime;
        int weekI = 0;
        for (String day : weekDays) {  //yy-mm-dd
            for (int k = 0; k < 48; k++) {//统计一天48段 出门记录
                beginTime = day + " " + hour48[k] + ":" + half1[k % 2] + ":00";
                endTime = day + " " + hour48[k] + ":" + half2[k % 2] + ":59";
                params.put("passType", "in");
                params.put("beginTime", beginTime);
                params.put("endTime", endTime);
                enter[weekI][k] = personDao.personStreamRules9(params);
                params.put("passType", "out");
                out[weekI][k] = personDao.personStreamRules9(params);
                //System.out.println(beginTime+" ~ "+endTime+" in:"+inNum+" out:"+outNum);
            }
            weekI++;

        }
        resultMap.put("enter", enter);
        resultMap.put("out", out);


        return resultMap;
    }

    @Override
    public Map<String, Object> subdistrictStatisticsToday(Map<String, Object> params) throws OssRenderException {
        if (params.get("areaId") == null || params.get("areaId").toString().trim().equals("")) {
            params.put("areaId", 0);
        }

        Map<String, Object> subStaToday = new HashMap<String, Object>();
        subStaToday.put("todayPersonSenseNum", personDao.todayPersonSenseNum(params));//今日人员出入
        subStaToday.put("todayCarSenseNum", personDao.todayCarSenseNum(params));//今日车辆出入
        subStaToday.put("abnormalTotalNum", personDao.abnormalTotalNum(params));//今日异常数量
        subStaToday.put("faceCaptureNum", personDao.faceCaptureNum(params));//人脸抓拍（新增）
        subStaToday.put("carCaptureNum", personDao.carCaptureNum(params));//车辆抓拍（新增）

        return subStaToday;
    }

    /**
     * @param params
     * @return map
     * @description 获取人脸抓拍列表
     * @author zhoutao
     * @date 2019/12/28 10:05
     */
    @Override
    public Map<String, Object> personCaptureList(Map<String, Object> params) {
        if (params.get("page") == null || "".equals(params.get("page").toString().trim())) {
            params.put("page", 1);
        }
        if (params.get("limit") == null || "".equals(params.get("limit").toString().trim())) {
            params.put("limit", 10);
        }
        if (params.get("areaId") == null || "".equals(params.get("areaId").toString().trim())) {
            params.put("areaId", 0);
        }
        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        int start = (page - 1) * limit;
        params.put("start", start);
        params.put("limit", limit);
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> findResult = personDao.personCaptureList(params);
        for (Map<String, Object> tmp : findResult) {

            if (tmp.get("bmask") != null && !tmp.get("bmask").toString().trim().equals("")) {
                int bmask = Integer.parseInt(tmp.get("bmask").toString());
                if (bmask == 1) {
                    tmp.put("bmask", "有");
                } else {
                    tmp.put("bmask", "无");
                }
            } else {
                tmp.put("bmask", "无");
            }
            if (tmp.get("eyeglass") != null && !tmp.get("eyeglass").toString().trim().equals("")) {
                int eyeglass = Integer.parseInt(tmp.get("eyeglass").toString());
                if (eyeglass == 1) {
                    tmp.put("eyeglass", "有");
                } else {
                    tmp.put("eyeglass", "无");
                }
            } else {
                tmp.put("eyeglass", "无");
            }

            if (tmp.get("sex") != null && !tmp.get("sex").toString().trim().equals("")) {
                tmp.put("sex", "1".equals(tmp.get("sex").toString()) ? "男" : "女");
            } else {
                tmp.put("sex", "");
            }

            if (tmp.get("country") == null || tmp.get("country").toString().trim().equals("")) {
                tmp.put("country", "中华人民共和国");
            }
            if (tmp.get("image") == null || "".equals(tmp.get("image"))) {
                tmp.put("image", "");
            } else {
                String img = tmp.get("image").toString();
                String ip = PropertiesUtil.getLocalTomcatImageIp();
                img = ip + img;
                tmp.put("image", img);
            }
        }
        result.put("list", findResult);
//         总条数太多，影响查询性能
        int totalRows = personDao.personCaptureNum(params);
        result.put("totalRows", totalRows);
        result.put("currentPage", params.get("page"));

        if (params.get("name") != null && !params.get("name").toString().trim().equals("")) {//根据人名获取 个人 -人脸抓拍的列表
            List<Map<String, Object>> list2 = personDao.personCaptureList2(params);
            result.put("list2", list2);
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> countryList(Map<String, Object> params) {
        List lists = personDao.countryList(params);
        if (lists.size() > 0) {
            return personDao.countryList(params);
        } else {
            return new LinkedList<>();
        }
    }
}
