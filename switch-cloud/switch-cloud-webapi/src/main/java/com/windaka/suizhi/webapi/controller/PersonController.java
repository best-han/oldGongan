package com.windaka.suizhi.webapi.controller;

import cn.hutool.core.util.ObjectUtil;
import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.common.utils.PropertiesUtil;
import com.windaka.suizhi.webapi.service.AppUserService;
import com.windaka.suizhi.webapi.service.PersonService;
import com.windaka.suizhi.webapi.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class PersonController extends BaseController {

    @Autowired
    private PersonService personService;
    @Autowired
    private AppUserService appUserService;

    /**
     * 添加小区人员
     *
     * @param persons
     * @return
     */
    @PostMapping("/person")
    public Map<String, Object> savePerson(@RequestBody Map<String, Object> persons) {
        try {
            personService.savePersons((String) persons.get("xqCode"), (List<Map<String, Object>>) persons.get("persons"));
            return render();
        } catch (Exception e) {
            log.info("[PersonController.savePerson,参数：{},异常信息{}]", "", e.getMessage());
            return failRender(e);
        }
    }

    @PutMapping("/person")
    public Map<String, Object> updatePerson(@RequestBody Map<String, Object> person) {
        try {
            personService.updatePerson(person);
            return render();
        } catch (Exception e) {
            log.info("[PersonController.updatePerson,参数：{},异常信息{}]", person, e.getMessage());
            return failRender(e);
        }
    }

    @DeleteMapping("/person/{personCode}")
    public Map<String, Object> deletePerson(@PathVariable("personCode") String personCode) {
        try {
            personService.deletePerson(personCode);
            return render();
        } catch (Exception e) {
            log.info("[PersonController.deleltePerson,参数：{},异常信息{}]", personCode, e.getMessage());
            return failRender(e);
        }
    }

    @GetMapping("/person/{personCode}")
    public Map<String, Object> queryPerson(@PathVariable("personCode") String personCode) {
        try {
            Map<String, Object> personMap = personService.queryPerson(personCode);
            return render(personMap);
        } catch (Exception e) {
            log.info("[PersonController.queryPerson,参数：{},异常信息{}]", personCode, e.getMessage());
            return failRender(e);
        }
    }


    /**
     * 根据图片查询小区单个人员Code
     *
     * @param params
     */
    @GetMapping("/guanliduan")
    public Map<String, Object> queryPersonCode(@RequestParam Map<String, Object> params) {
        try {
            Map map = new HashMap();
            map.put("personCode", personService.queryPersonCode(params));
            return map;
        } catch (Exception e) {
            log.info("[PersonController.queryPersonCode,参数：{},异常信息{}]", "", e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 街道 -人口基础信息-ygy
     *
     * @param params
     * @return
     */
    @GetMapping("/person/baseInfo")
    public Map<String, Object> personBaseInfo(@RequestParam Map<String, Object> params) {
        try {
            Map map = personService.personBaseInfo(params);
            return render(map);
        } catch (Exception e) {
            log.info("[PersonController.queryPersonBaseInfoList,参数：{},异常信息{}]", params, e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 趋势研判分析-人员性质统计 hjt
     *
     * @param params
     * @return
     */
    @GetMapping("/person/property")
    public Map<String, Object> queryPersonPropertyStatistics(@RequestParam Map<String, Object> params) {
        try {
            if (params.get("xqCode") == "" || ObjectUtil.isNull(params.get("xqCode"))) {
                //验证当前用户查询权限
                LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
                params.put("xqCode", appUserService.checkAuth(loginAppUser.getUserId()));
            }
            List<Map<String, Object>> list = personService.queryPersonPropertyStatistics(params);
           /* Iterator i=list.iterator();
            while (i.hasNext())
            {
                Map tMap=(Map)i.next();
                double percent=Double.parseDouble(tMap.get("percent").toString());
                int percentI=(int)(percent*100);
                tMap.put("percent",percentI);
            }*/
            return render(list);
//            Map resultMap=new HashMap();
//            List resultList=new LinkedList();
//            int []nums={3650,231,59};
//            NumberFormat numberFormat = NumberFormat.getInstance();
//            String []properties={"常住人口","流动人口-未申请居住证","流动人口-已申请居住证"};
//            int t=0;
//            for(int i=0;i<3;i++)
//                t+=nums[i];
//            for(int i=0;i<3;i++)
//            {
//                Map tMap=new HashMap();
//                tMap.put("personProperty",properties[i]);
//                tMap.put("num",nums[i]);
//                if(i==0)
//                {
//                    tMap.put("percent",92);
//                }
//                else if(i==1)
//                {
//                    tMap.put("percent",5);
//                }
//                else
//                {
//                    tMap.put("percent",3);
//                }
//                resultList.add(tMap);
//            }
//            return render(resultList);
        } catch (Exception e) {
            log.info("[PersonController.queryPersonPropertyStatistics,参数：{},异常信息{}]", params, e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 人员性质统计------ygy--实有人口页面的饼图，常住人口、流动人口、境外人员
     * @param params
     * @return
     */
    @GetMapping("/person/property2")
    public Map<String,Object> queryPersonPropertyStatistics2(@RequestParam Map<String,Object> params){
        try {
            List<Map<String,Object>> list = personService.queryPersonPropertyStatistics2(params);
            return render(list);
        } catch (Exception e) {
            log.info("[PersonController.queryPersonBaseInfoList,参数：{},异常信息{}]", params, e.getMessage());
            return failRender(e);
        }
    }


    /**
     * 街道-人员分布信息 ygy
     *
     * @param params
     * @return
     */
    @GetMapping("/person/num")
    public Map<String, Object> personDistribute(@RequestParam Map<String, Object> params) {
        try {
            List<Map<String, Object>> listMaps = personService.personDistribute(params);
            return render(listMaps);
        } catch (Exception e) {
            log.info("[PersonController.personDistribute,参数：{},异常信息{}]", params, e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 街道-人口出入信息统计-ygy
     *
     * @param params
     * @return
     */
    @GetMapping("/person/stream")
    public Map<String, Object> personStream(@RequestParam Map<String, Object> params) {
        try {
            params.putIfAbsent("areaId", 0);
            Map<String, Object> map = personService.personStream(params);
            return render(map);
        } catch (Exception e) {
            log.info("[PersonController.personStream,参数：{},异常信息{}]", params, e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 流动人口信息 hjt
     *
     * @param params
     * @return
     */
    @GetMapping("/person/floating")
    public Map<String, Object> queryFloatingPersonStatistics(@RequestParam Map<String, Object> params) {
        try {
            if (params.get("xqCode") == "" || ObjectUtil.isNull(params.get("xqCode"))) {
                //验证当前用户查询权限
                LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
                params.put("xqCode", appUserService.checkAuth(loginAppUser.getUserId()));
            }
            Map<String, Object> map = personService.queryFloatingPersonStatistics(params);
            return render(map);
        } catch (Exception e) {
            log.info("[PersonController.queryFloatingPersonStatistics,参数：{},异常信息{}]", params, e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 街道-特殊人员分布信息（控制器完成） dee
     *
     * @param params
     * @return
     */
    @GetMapping("/person/special/num")
    public Map<String, Object> queryPersonSpecialNum(@RequestParam Map<String, Object> params) {
        try {
//            List list = new LinkedList();
//            NumberFormat numberFormat = NumberFormat.getInstance();
//            if (params.get("areaId") == null || params.get("areaId").toString().trim().equals(""))
//                params.put("areaId", 0);
//            int[] personSpNums = {426, 376, 184, 252, 298};
//            int total = 0;
//            int n = personSpNums.length;
//            for (int i = 0; i < n; i++)
//                total += personSpNums[i];
//            double dt = (double) total;
//            if (params.get("xqCode") != null && !params.get("xqCode").toString().trim().equals("")) {
//                String xqCode = params.get("xqCode").toString();
//                Map perMap = new LinkedHashMap();
//                if (xqCode.equals("11")) {
//                    perMap.put("xqCode", xqCode);
//                    perMap.put("xqName", "香汐海");
//                    perMap.put("personSpNum", personSpNums[0]);
//                    perMap.put("percent", 100);
//                } else if (xqCode.equals("12")) {
//                    perMap.put("xqCode", xqCode);
//                    perMap.put("xqName", "海棠墅");
//                    perMap.put("personSpNum", personSpNums[1]);
//                    perMap.put("percent", 100);
//                } else if (xqCode.equals("13")) {
//                    perMap.put("xqCode", xqCode);
//                    perMap.put("xqName", "官厅园");
//                    perMap.put("personSpNum", personSpNums[2]);
//                    perMap.put("percent", 100);
//                } else if (xqCode.equals("14")) {
//                    perMap.put("xqCode", xqCode);
//                    perMap.put("xqName", "南岛小镇");
//                    perMap.put("personSpNum", personSpNums[3]);
//                    perMap.put("percent", 100);
//                } else if (xqCode.equals("02")) {
//                    perMap.put("xqCode", xqCode);
//                    perMap.put("xqName", "凤凰城");
//                    perMap.put("personSpNum", personSpNums[4]);
//                    perMap.put("percent", 100);
//                }
//                list.add(perMap);
//            } else if (params.get("subdistrictId") != null && !params.get("subdistrictId").toString().trim().equals("")) {
//                String subdistrictId = params.get("subdistrictId").toString();
//                if (subdistrictId.equals("2")) {
//                    for (int i = 0; i < n; i++) {
//                        Map perMap = new LinkedHashMap();
//                        if (i == 0) {
//                            perMap.put("xqCode", "11");
//                            perMap.put("xqName", "香汐海");
//                        } else if (i == 1) {
//                            perMap.put("xqCode", "12");
//                            perMap.put("xqName", "海棠墅");
//                        } else if (i == 2) {
//                            perMap.put("xqCode", "13");
//                            perMap.put("xqName", "官厅园");
//                        } else if (i == 3) {
//                            perMap.put("xqCode", "14");
//                            perMap.put("xqName", "南岛小镇");
//                        } else if (i == 4) {
//                            perMap.put("xqCode", "02");
//                            perMap.put("xqName", "凤凰城");
//                        }
//                        perMap.put("personSpNum", personSpNums[i]);
//                        numberFormat.setMaximumFractionDigits(2);
//                        double dpm = (double) personSpNums[i];
//                        double percent = Double.parseDouble(numberFormat.format(dpm / dt * 100));
//                        perMap.put("percent", percent);
//                        list.add(perMap);
//                    }
//                }
//            } else {
//                for (int i = 0; i < n; i++) {
//                    Map perMap = new LinkedHashMap();
//                    if (i == 0) {
//                        perMap.put("xqCode", "11");
//                        perMap.put("xqName", "香汐海");
//                    } else if (i == 1) {
//                        perMap.put("xqCode", "12");
//                        perMap.put("xqName", "海棠墅");
//                    } else if (i == 2) {
//                        perMap.put("xqCode", "13");
//                        perMap.put("xqName", "官厅园");
//                    } else if (i == 3) {
//                        perMap.put("xqCode", "14");
//                        perMap.put("xqName", "南岛小镇");
//                    } else if (i == 4) {
//                        perMap.put("xqCode", "02");
//                        perMap.put("xqName", "凤凰城");
//                    }
//                    perMap.put("personSpNum", personSpNums[i]);
//                    numberFormat.setMaximumFractionDigits(2);
//                    double dcm = (double) personSpNums[i];
//                    double percent = Double.parseDouble(numberFormat.format(dcm / dt * 100));
//                    perMap.put("percent", percent);
//                    list.add(perMap);
//                }
//            }
//            return render(list);
            if(params.get("areaId")==null||params.get("areaId").toString().trim().equals(""))
                params.put("areaId",0);
            List list=personService.querySpecialPersonDistribution(params);
            NumberFormat numberFormat = NumberFormat.getInstance();
            Iterator i=list.iterator();
            double t=0;
            List nList=new LinkedList();
            while (i.hasNext())
            {
                Map map=(Map)i.next();
                nList.add(map);
                t+=Double.parseDouble(map.get("personNum").toString());
            }
            i=nList.iterator();
            while (i.hasNext())
            {
                Map map=(Map)i.next();
                numberFormat.setMaximumFractionDigits(2);
                double percent = Double.parseDouble(numberFormat.format((Double.parseDouble(map.get("personNum").toString())/t)*100));
                map.put("percent",percent);
            }
            return render(nList);


        } catch (Exception e) {
            log.info(e.toString());
            return failRender(e);
        }
    }

    /**
     * 特殊人员管理列表 dee
     *
     * @param params
     * @return
     */
    @GetMapping("/person/special/list")
    public Map<String, Object> queryPersonSpecialList(@RequestParam Map<String, Object> params) {
        try {
            if (params.get("areaId") == null || params.get("areaId").toString().trim().equals(""))
                params.put("areaId", 0);
            int page = 1;
            int limit = 10;
            if (params.get("page") != null && !params.get("page").toString().trim().equals(""))
                page = Integer.parseInt(params.get("page").toString());
            if (params.get("limit") != null && !params.get("limit").toString().trim().equals(""))
                limit = Integer.parseInt(params.get("limit").toString());
            Map resultMap = new HashMap();

            List list = personService.queryPersonSpecialList(params);
            int totalRows = list.size();

            int currentPage = page;
            int totalPage = totalRows / limit;
            if (totalRows % limit != 0) totalPage += 1;
            if (page > totalPage) currentPage = totalPage;
            if (page < 1) currentPage = 1;

            List nList = new LinkedList();
            Iterator li = list.iterator();
            int i = 0;
            while (li.hasNext()) {
                Map tMap = (Map) li.next();
                tMap.putIfAbsent("image", "");
                tMap.putIfAbsent("faceTypeCode", "");
                tMap.putIfAbsent("faceTypeName", "");
                tMap.putIfAbsent("houseName", "");
                tMap.put("personId", "99_11061");
                if (i >= (currentPage - 1) * limit && i < currentPage * limit) {
                    nList.add(tMap);
                }
                i++;
            }

            resultMap.put("list", nList);
            resultMap.put("totalRows", totalRows);
            resultMap.put("currentPage", currentPage);
            return render(resultMap);
        } catch (Exception e) {
            log.info(e.toString());
            return failRender(e);
        }
    }

    /**
     * 活动规律统计-流动/常住 hjt
     *
     * @param params
     * @return
     */
    @GetMapping("/person/activity/statistics")
    public Map<String, Object> queryPersonActivityStatistics(@RequestParam Map<String, Object> params) {
        try {
            if (params.get("xqCode") == "" || ObjectUtil.isNull(params.get("xqCode"))) {
                //验证当前用户查询权限
                LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
                params.put("xqCode", appUserService.checkAuth(loginAppUser.getUserId()));
            }
            List<Map<String, Object>> list = personService.queryPersonActivityStatistics(params);
            return render(list);
//            String []dateListWeek=new String[7];
//
//            ArrayList<String> weekDays = DateUtil.getDays(7);
//            Iterator<String> wdi = weekDays.iterator();
//            int j = 0;
//            while (wdi.hasNext()) {
//                String wdt = wdi.next();
//                String wds = DateUtil.getWeek(wdt);
//                wds=wds.replaceFirst("星期","周");
//                dateListWeek[j] = wds;
//                j++;
//                //System.out.println(wds);
//            }
//            Random random=new Random();
//            Map resultMap=new HashMap();
//            resultMap.put("weekName",dateListWeek);
//            int [][]halfHourPermanentNum=new int[7][48];
//            for(int i=0;i<7;i++)
//            {
//                int indexLength=4+random.nextInt(3);
//                int []indexes=new int[indexLength];
//                for (int k=0;k<indexLength;k++)
//                {
//                    indexes[k]=14+random.nextInt(30);
//                }
//                for(int k=0;k<indexLength;k++)
//                {
//                    halfHourPermanentNum[i][indexes[k]]=1+random.nextInt(1);
//                }
//            }
//            resultMap.put("halfHourPermanentNum",halfHourPermanentNum);
//            int [][]halfHourFloatingNum=new int[7][48];
//            for(int i=0;i<7;i++)
//            {
//                int indexLength=4+random.nextInt(3);
//                int []indexes=new int[indexLength];
//                for (int k=0;k<indexLength;k++)
//                {
//                    indexes[k]=14+random.nextInt(30);
//                }
//                for(int k=0;k<indexLength;k++)
//                {
//                    halfHourFloatingNum[i][indexes[k]]=1+random.nextInt(1);
//                }
//            }
//            resultMap.put("halfHourFloatingNum",halfHourFloatingNum);
//            Date today=new Date();
//            SimpleDateFormat f=new SimpleDateFormat("HH");
//            String time=f.format(today);
//            int tt=Integer.parseInt(time);
//            for(int i=tt*2;i<48;i++)
//            {
//                halfHourPermanentNum[6][i]=0;
//                halfHourFloatingNum[6][i]=0;
//            }
//            return render(resultMap);
        } catch (Exception e) {
            log.info("[PersonController.queryPersonActivityStatistics,参数：{},异常信息{}]", params, e.getMessage());
            return failRender(e);
        }
    }


    /**
     * 街道-人员列表查询  ygy
     *
     * @param params
     * @return
     */
    @GetMapping("/person/list")
    public Map<String, Object> personList(@RequestParam Map<String, Object> params) {

        try {
            Page page = personService.queryPersonList(params);
            return render(page);
        } catch (Exception e) {
            log.info("[PersonController.personList,参数：{},异常信息{}]", params, e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 街道-人员档案-基础信息查询（单个查询） ygy
     *
     * @param personId
     * @return
     */
    @GetMapping("/person/{personId}/info")
    public Map<String, Object> queryBaseInfoByPersonId(@PathVariable("personId") String personId) {
        try {
            Map<String, Object> personMap = personService.queryBaseInfoByPersonId(personId);
            return render(personMap);
        } catch (Exception e) {
            log.info("[PersonController.queryBaseInfoByPersonId,参数：{},异常信息{}]", personId, e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 街道-人员档案-布控库标签 ygy
     *
     * @param personId
     * @return
     */
    @GetMapping("/person/{personId}/faceType")
    public Map<String, Object> faceType(@PathVariable("personId") String personId) {
        try {
            List<Map<String, Object>> listMap = personService.faceType(personId);
            return render(listMap);
        } catch (Exception e) {
            log.info("[PersonController.faceType,参数：{},异常信息{}]", personId, e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 人员档案-异常行为标签 ygy
     *
     * @param personId
     * @return
     */
    @GetMapping("/person/{personId}/abnormalType")
    public Map<String, Object> personAbnormalType(@PathVariable("personId") String personId) {
        try {
            List<Map<String, Object>> listMap = personService.personAbnormalType(personId);
            return render(listMap);
        } catch (Exception e) {
            log.info("[PersonController.personAbnormalType,参数：{},异常信息{}]", personId, e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 街道-人员档案-房产信息 ygy
     *
     * @param personId
     * @return
     */
    @GetMapping("/person/{personId}/house")
    public Map<String, Object> houseInfo(@PathVariable("personId") String personId) {
        try {
            Map<String, Object> map = personService.personHouseBypersonId(personId);
            return render(map);
        } catch (Exception e) {
            log.info("[PersonController.houseInfo,参数：{},异常信息{}]", personId, e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 街道-人员档案-车辆信息 ygy
     *
     * @param personId
     * @return
     */
    @GetMapping("/person/{personId}/car")
    public Map<String, Object> carInfo(@PathVariable("personId") String personId) {
        try {
            List<Map<String, Object>> listMap = personService.carInfo(personId);
            return render(listMap);
        } catch (Exception e) {
            log.info("[PersonController.carInfo,参数：{},异常信息{}]", personId, e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 街道-人员档案-抓拍记录 ygy
     *
     * @param personId
     * @return
     */
    @GetMapping("/person/{personId}/capture")
    public Map<String, Object> captureInfoByPersonId(@PathVariable("personId") String personId, @RequestParam Map<String, Object> params) {
        try {
            Map<String, Object> Map = personService.captureInfoByPersonId(personId, params);
            return render(Map);
        } catch (Exception e) {
            log.info("[PersonController.captureInfo,参数：{},异常信息{}]", personId, e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 街道-人员档案-抓拍记录详情 ygy
     *
     * @param personId
     * @return
     */
    @GetMapping("/person/{personId}/capture/day")
    public Map<String, Object> captureInfoDetailsByPersonId(@PathVariable("personId") String personId) {
        try {
            List<Map<String, Object>> listMap = personService.captureInfoDetailsByPersonId(personId);
            return render(listMap);
        } catch (Exception e) {
            log.info("[PersonController.captureInfoDetails,参数：{},异常信息{}]", personId, e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 街道-人员档案-关系图 ygy
     *
     * @param personId
     * @return
     */
    @GetMapping("/person/{personId}/relation")
    public Map<String, Object> personRelationByPersonId(@PathVariable("personId") String personId) {
        try {
            Map<String, Object> map = personService.personRelationByPersonId(personId);
            return render(map);
        } catch (Exception e) {
            log.info("[PersonController.personRelation,参数：{},异常信息{}]", personId, e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 街道-出入规律-人员出入信息统计 ygy
     *
     * @param params
     * @return
     */
//    @GetMapping("/person/stream/rule")
//    public Map<String, Object> personStreamRule(@RequestParam Map<String, Object> params) {
//        try {
//
////            Map map = new LinkedHashMap();
////            if (params.get("areaId") == null || params.get("areaId").toString().trim().equals(""))
////                params.put("areaId", 0);
////
////            int [][]outCountDay={
////                    {0,0,0,0,0,0,13,11,20,0,0,0,12,15,4,19,16,12,4,2,3,1,0,0},
////                    {0,0,0,0,0,1,9,8,17,0,0,0,10,13,0,22,4,14,22,4,5,0,0,0},
////                    {0,0,0,0,0,0,6,9,13,0,0,12,21,13,15,6,2,6,12,2,0,0,0,0},
////                    {0,0,0,0,0,0,11,10,22,0,0,0,1,12,2,2,16,23,17,3,1,1,0,0},
////                    {0,0,0,0,0,0,9,13,24,0,0,0,5,10,6,19,22,5,3,2,2,2,0,0}};
////            int [][]enterCountDay={
////                    {0,0,0,0,0,0,1,16,21,7,2,6,0,5,1,0,0,1,3,14,11,6,3,2},
////                    {0,0,0,0,0,0,1,7,14,4,0,14,11,1,5,0,1,2,4,12,9,2,1,0},
////                    {0,0,0,0,0,1,0,6,4,7,4,6,2,3,0,0,0,2,5,13,5,7,2,1},
////                    {0,0,0,0,0,0,13,4,14,8,5,7,2,6,0,0,0,2,8,9,11,6,0,0},
////                    {0,0,0,0,0,0,2,6,7,0,3,6,13,5,7,0,0,8,4,5,11,7,0,0}};
////            Date today=new Date();
////            SimpleDateFormat f=new SimpleDateFormat("HH");
////            String time=f.format(today);
////            int tt=Integer.parseInt(time);
////            for(int i=tt;i<24;i++)
////            {
////                outCountDay[0][i]=0;
////                enterCountDay[0][i]=0;
////                outCountDay[1][i]=0;
////                enterCountDay[1][i]=0;
////                outCountDay[2][i]=0;
////                enterCountDay[2][i]=0;
////                outCountDay[3][i]=0;
////                enterCountDay[3][i]=0;
////                outCountDay[4][i]=0;
////                enterCountDay[4][i]=0;
////            }
////            int []dateListDay={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,
////                    18,19,20,21,22,23};
////            int [][]outCountWeek={
////                    {167,245,228,238,197,124,184},
////                    {218,107,172,100,152,175,103},
////                    {122,222,219,199,141,214,156},
////                    {170,135,169,212,190,205,138},
////                    {78,81,67,198,122,112,244}};
////            int [][]enterCountWeek={
////                    {100,247,238,229,49,79,28},
////                    {56,84,69,107,192,44,38},
////                    {75,184,127,104,191,121,48},
////                    {94,193,169,50,71,84,35},
////                    {66,74,101,75,147,155,179}};
////            String []dateListWeek=new String[7];
////
////            ArrayList<String> weekDays = DateUtil.getDays(7);
////            Iterator<String> wdi = weekDays.iterator();
////            int j = 0;
////            while (wdi.hasNext()) {
////                String wdt = wdi.next();
////                String wds = DateUtil.getWeek(wdt);
////                dateListWeek[j] = wds;
////                j++;
//////                System.out.println(wds);
////            }
////
////            Map dayMap = new LinkedHashMap();
////            Map weekMap = new LinkedHashMap();
////            if (params.get("xqCode") != null && !params.get("xqCode").toString().trim().equals("")) {
////                String xqCode = params.get("xqCode").toString();
////                if (xqCode.equals("11")) {
////                    dayMap.put("outCount", outCountDay[0]);
////                    dayMap.put("dateList", dateListDay);
////                    dayMap.put("enterCount", enterCountDay[0]);
////                    weekMap.put("outCount", outCountWeek[0]);
////                    weekMap.put("dateList", dateListWeek);
////                    weekMap.put("enterCount", enterCountWeek[0]);
////                } else if (xqCode.equals("12")) {
////                    dayMap.put("outCount", outCountDay[1]);
////                    dayMap.put("dateList", dateListDay);
////                    dayMap.put("enterCount", enterCountDay[1]);
////                    weekMap.put("outCount", outCountWeek[1]);
////                    weekMap.put("dateList", dateListWeek);
////                    weekMap.put("enterCount", enterCountWeek[1]);
////                } else if (xqCode.equals("13")) {
////                    dayMap.put("outCount", outCountDay[2]);
////                    dayMap.put("dateList", dateListDay);
////                    dayMap.put("enterCount", enterCountDay[2]);
////                    weekMap.put("outCount", outCountWeek[2]);
////                    weekMap.put("dateList", dateListWeek);
////                    weekMap.put("enterCount", enterCountWeek[2]);
////                } else if (xqCode.equals("14")) {
////                    dayMap.put("outCount", outCountDay[3]);
////                    dayMap.put("dateList", dateListDay);
////                    dayMap.put("enterCount", enterCountDay[3]);
////                    weekMap.put("outCount", outCountWeek[3]);
////                    weekMap.put("dateList", dateListWeek);
////                    weekMap.put("enterCount", enterCountWeek[3]);
////                } else if (xqCode.equals("02")) {
////                    dayMap.put("outCount", outCountDay[4]);
////                    dayMap.put("dateList", dateListDay);
////                    dayMap.put("enterCount", enterCountDay[4]);
////                    weekMap.put("outCount", outCountWeek[4]);
////                    weekMap.put("dateList", dateListWeek);
////                    weekMap.put("enterCount", enterCountWeek[4]);
////                }
////            } else if (params.get("subdistrictId") != null && !params.get("subdistrictId").toString().trim().equals("")) {
////                String subdistrictId = params.get("subdistrictId").toString();
////                if (subdistrictId.equals("2")) {
////                    int[] sOutCountDay = new int[24];
//////                    int []sOutCountMonth=new int [30];
//////                    int []sOutCountYear=new int [12];
////                    int[] sOutCountWeek = new int[7];
////                    for (int i = 0; i < 24; i++) {
////                        sOutCountDay[i] = outCountDay[0][i] + outCountDay[1][i] + outCountDay[2][i] + outCountDay[3][i] + outCountDay[4][i];
////                    }
//////                    for (int i=0;i<30;i++)
//////                    {
//////                        sOutCountMonth[i]=outCountMonth[0][i]+outCountMonth[1][i]+outCountMonth[2][i]+outCountMonth[3][i];
//////                    }
//////                    for (int i=0;i<12;i++)
//////                    {
//////                        sOutCountYear[i]=outCountYear[0][i]+outCountYear[1][i]+outCountYear[2][i]+outCountYear[3][i];
//////                    }
////                    for (int i = 0; i < 7; i++) {
////                        sOutCountWeek[i] = outCountWeek[0][i] + outCountWeek[1][i] + outCountWeek[2][i] + outCountWeek[3][i] + outCountWeek[4][i];
////                    }
////                    int[] sEnterCountDay = new int[24];
//////                    int []sEnterCountMonth=new int [30];
//////                    int []sEnterCountYear=new int [12];
////                    int[] sEnterCountWeek = new int[7];
////                    for (int i = 0; i < 24; i++) {
////                        sEnterCountDay[i] = enterCountDay[0][i] + enterCountDay[1][i] + enterCountDay[2][i] + enterCountDay[3][i] + enterCountDay[4][i];
////                    }
//////                    for (int i=0;i<30;i++)
//////                    {
//////                        sEnterCountMonth[i]=enterCountMonth[0][i]+enterCountMonth[1][i]+enterCountMonth[2][i]+enterCountMonth[3][i];
//////                    }
//////                    for (int i=0;i<12;i++)
//////                    {
//////                        sEnterCountYear[i]=enterCountYear[0][i]+enterCountYear[1][i]+enterCountYear[2][i]+enterCountYear[3][i];
//////                    }
////                    for (int i = 0; i < 7; i++) {
////                        sEnterCountWeek[i] = enterCountWeek[0][i] + enterCountWeek[1][i] + enterCountWeek[2][i] + enterCountWeek[3][i] + enterCountWeek[4][i];
////                    }
////                    dayMap.put("outCount", sOutCountDay);
////                    dayMap.put("dateList", dateListDay);
////                    dayMap.put("enterCount", sEnterCountDay);
////                    weekMap.put("outCount", sOutCountWeek);
////                    weekMap.put("dateList", dateListWeek);
////                    weekMap.put("enterCount", sEnterCountWeek);
////
////                }
////            } else {
////                int[] sOutCountDay = new int[24];
//////                    int []sOutCountMonth=new int [30];
//////                    int []sOutCountYear=new int [12];
////                int[] sOutCountWeek = new int[7];
////                for (int i = 0; i < 24; i++) {
////                    sOutCountDay[i] = outCountDay[0][i] + outCountDay[1][i] + outCountDay[2][i] + outCountDay[3][i] + outCountDay[4][i];
////                }
//////                    for (int i=0;i<30;i++)
//////                    {
//////                        sOutCountMonth[i]=outCountMonth[0][i]+outCountMonth[1][i]+outCountMonth[2][i]+outCountMonth[3][i];
//////                    }
//////                    for (int i=0;i<12;i++)
//////                    {
//////                        sOutCountYear[i]=outCountYear[0][i]+outCountYear[1][i]+outCountYear[2][i]+outCountYear[3][i];
//////                    }
////                for (int i = 0; i < 7; i++) {
////                    sOutCountWeek[i] = outCountWeek[0][i] + outCountWeek[1][i] + outCountWeek[2][i] + outCountWeek[3][i] + outCountWeek[4][i];
////                }
////                int[] sEnterCountDay = new int[24];
//////                    int []sEnterCountMonth=new int [30];
//////                    int []sEnterCountYear=new int [12];
////                int[] sEnterCountWeek = new int[7];
////                for (int i = 0; i < 24; i++) {
////                    sEnterCountDay[i] = enterCountDay[0][i] + enterCountDay[1][i] + enterCountDay[2][i] + enterCountDay[3][i] + enterCountDay[4][i];
////                }
//////                    for (int i=0;i<30;i++)
//////                    {
//////                        sEnterCountMonth[i]=enterCountMonth[0][i]+enterCountMonth[1][i]+enterCountMonth[2][i]+enterCountMonth[3][i];
//////                    }
//////                    for (int i=0;i<12;i++)
//////                    {
//////                        sEnterCountYear[i]=enterCountYear[0][i]+enterCountYear[1][i]+enterCountYear[2][i]+enterCountYear[3][i];
//////                    }
////                for (int i = 0; i < 7; i++) {
////                    sEnterCountWeek[i] = enterCountWeek[0][i] + enterCountWeek[1][i] + enterCountWeek[2][i] + enterCountWeek[3][i] + enterCountWeek[4][i];
////                }
////                dayMap.put("outCount", sOutCountDay);
////                dayMap.put("dateList", dateListDay);
////                dayMap.put("enterCount", sEnterCountDay);
////                weekMap.put("outCount", sOutCountWeek);
////                weekMap.put("dateList", dateListWeek);
////                weekMap.put("enterCount", sEnterCountWeek);
////
////            }
////            map.put("day", dayMap);
////            map.put("week", weekMap);
////            return render(map);
//            Map map=new LinkedHashMap();
//            if(params.get("areaId")==null||params.get("areaId").toString().trim().equals(""))
//                params.put("areaId",0);
//            int []dateListDay={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,
//                    18,19,20,21,22,23};
//            String []dateListWeek=new String[7];
//
//            ArrayList<String> weekDays=DateUtil.getDays(7);
//            Iterator<String> wdi=weekDays.iterator();
//            int j=0;
//            while (wdi.hasNext())
//            {
//                String wdt=wdi.next();
//                //System.out.println(wdt);
//                String wds=DateUtil.getWeek(wdt);
//                dateListWeek[j]=wds;
//                j++;
//            }
//
//            int []enterCountDay=new int[24];
//            int []outCountDay=new int[24];
//
//            int []enterCountWeek=new int[7];
//            int []outCountWeek=new int[7];
//
//            String todayYMDH=DateUtil.getTodayStartTime();
//            String []ts=todayYMDH.split(" ");
//            String todayYMD=ts[0];
//            String beginTime;
//            String endTime;
//            Map tMap;
//            List inL;
//            List outL;
//            for (int i=0;i<24;i++)
//            {
//                beginTime=todayYMD+" "+dateListDay[i]+":00:00";
//                endTime=todayYMD+" "+dateListDay[i]+":59:59";
//                enterCountDay[i]=0;
//                outCountDay[i]=0;
//                params.put("beginTime",beginTime);
//                params.put("endTime",endTime);
//                params.put("access","1");
//                inL=personService.personStreamRules3(params);
//                params.put("access","0");
//                outL=personService.personStreamRules3(params);
//                if(inL!=null&&!inL.isEmpty())
//                {
//                    tMap=(Map)inL.get(0);
//                    enterCountDay[i]=Integer.parseInt(tMap.get("passNum").toString());
//                }
//                if(outL!=null&&!outL.isEmpty())
//                {
//                    tMap=(Map)outL.get(0);
//                    outCountDay[i]=Integer.parseInt(tMap.get("passNum").toString());
//                }
//            }
//
//            wdi=weekDays.iterator();
//            j=0;
//            while (wdi.hasNext())
//            {
//                String wdt=wdi.next();
//                beginTime=wdt+" 00:00:00";
//                endTime=wdt+" 23:59:59";
//                enterCountWeek[j]=0;
//                outCountWeek[j]=0;
//                params.put("beginTime",beginTime);
//                params.put("endTime",endTime);
//                params.put("access","1");
//                inL=personService.personStreamRules3(params);
//                params.put("access","0");
//                outL=personService.personStreamRules3(params);
//                if(inL!=null&&!inL.isEmpty())
//                {
//                    tMap=(Map)inL.get(0);
//                    enterCountWeek[j]=Integer.parseInt(tMap.get("passNum").toString());
//                }
//                if(outL!=null&&!outL.isEmpty())
//                {
//                    tMap=(Map)outL.get(0);
//                    outCountWeek[j]=Integer.parseInt(tMap.get("passNum").toString());
//                }
//                j++;
//            }
//            Map dayMap=new LinkedHashMap();
//            Map weekMap=new LinkedHashMap();
//            dayMap.put("outCount",outCountDay);
//            dayMap.put("dateList",dateListDay);
//            dayMap.put("enterCount",enterCountDay);
//            weekMap.put("outCount",outCountWeek);
//            weekMap.put("dateList",dateListWeek);
//            weekMap.put("enterCount",enterCountWeek);
//            map.put("day",dayMap);
//            map.put("week",weekMap);
//            return render(map);
//            //return render(map);
//        } catch (Exception e) {
//            log.info("[PersonController.personRelation,参数：{},异常信息{}]", params, e.getMessage());
//            return failRender(e);
//        }
//    }

//    @GetMapping("/person/stream/rule2")
//    public Map<String, Object> personStreamRule2(@RequestParam Map<String, Object> params) {
//        try {
//            String[] dateListWeek = new String[7];
//
//            ArrayList<String> weekDays = DateUtil.getDays(7);
//            Iterator<String> wdi = weekDays.iterator();
//            int j = 0;
//            while (wdi.hasNext()) {
//                String wdt = wdi.next();
//                String wds = DateUtil.getWeek(wdt);
//                dateListWeek[j] = wds;
//                j++;
////                System.out.println(wds);
//            }
//            Random random = new Random();
//            Map resultMap = new HashMap();
//            resultMap.put("time", dateListWeek);
//            int[][] enter = new int[7][48];
//            for (int i = 0; i < 7; i++) {
//                int indexLength = 4 + random.nextInt(3);
//                int[] indexes = new int[indexLength];
//                for (int k = 0; k < indexLength; k++) {
//                    indexes[k] = 14 + random.nextInt(30);
//                }
//                for (int k = 0; k < indexLength; k++) {
//                    enter[i][indexes[k]] = 1 + random.nextInt(1);
//                }
//            }
//            resultMap.put("enter", enter);
//            int[][] out = new int[7][48];
//            for (int i = 0; i < 7; i++) {
//                int indexLength = 4 + random.nextInt(3);
//                int[] indexes = new int[indexLength];
//                for (int k = 0; k < indexLength; k++) {
//                    indexes[k] = 14 + random.nextInt(30);
//                }
//                for (int k = 0; k < indexLength; k++) {
//                    out[i][indexes[k]] = 1 + random.nextInt(1);
//                }
//            }
//            resultMap.put("out", out);
//            Date today = new Date();
//            SimpleDateFormat f = new SimpleDateFormat("HH");
//            String time = f.format(today);
//            int tt = Integer.parseInt(time);
//            for (int i = tt * 2; i < 48; i++) {
//                enter[6][i] = 0;
//                out[6][i] = 0;
//            }
//            return render(resultMap);
//        } catch (Exception e) {
//            log.info("[PersonController.personRelation,参数：{},异常信息{}]", params, e.getMessage());
//            return failRender(e);
//        }
//    }

//    /**
//     * 公安-街道-人员感知（列表查） ygy
//     * @param params
//     * @return
//     */
//    @GetMapping("/stranger/sense")
//    public Map<String, Object> strangerSense(@RequestParam Map<String, Object> params) {
//        try {
//            Map<String, Object> map = personService.strangerSense(params);
//            return render(map);
//        } catch (Exception e) {
//            log.info("[PersonController.strangerSense,参数：{},异常信息{}]", params, e.getMessage());
//            return failRender(e);
//        }
//    }

    /**
     * 公安-街道-现有人员列表查询 ygy
     *
     * @param params
     * @return
     */
    @GetMapping("/person/owner/list")
    public Map<String, Object> personOwnerList(@RequestParam Map<String, Object> params) {
        try {
            Map<String, Object> map = personService.personOwnerList(params);
            return render(map);
        } catch (Exception e) {
            log.info("[PersonController.personOwnerList,参数：{},异常信息{}]", params, e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 街道-疑似迁出人员列表查询(按小区) ygy
     *
     * @param params
     * @return
     */
    @GetMapping("/person/quit/list")
    public Map<String, Object> personQuitList(@RequestParam Map<String, Object> params) {
        try {
//            Map<String, Object> map = personService.personQuitList(params);
            Map<String, Object> map = personService.personQuitList2(params);
            return render(map);
        } catch (Exception e) {
            log.info("[PersonController.personQuitList,参数：{},异常信息{}]", params, e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 疑似新增人员列表查询 ygy
     * @param params
     * @return
     */
    @GetMapping("/person/added/list")
    public Map<String,Object> personAddedList(@RequestParam Map<String,Object> params){
        try {
            Map<String,Object> map=personService.personAddedList(params);
            return render(map);
        }catch (Exception e){
            log.info("[PersonController.personAddedList,参数：{},异常信息{}]", params, e.getMessage());
            return failRender(e);
        }
    }
    /**
     * 公安-陌生人员列表查询 ygy
     *
     * @param params
     * @return
     */
    @GetMapping("/person/stranger/list")
    public Map<String, Object> strangerSense(@RequestParam Map<String, Object> params) {
        try {
            Map<String, Object> map = personService.strangerSense(params);
            return render(map);
        } catch (Exception e) {
            log.info("[PersonController.strangerSense,参数：{},异常信息{}]", params, e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 公安-外籍人员列表查询） ygy
     *
     * @param params
     * @return
     */
    @GetMapping("/foreigner/sense")
    public Map<String, Object> foreignerSense(@RequestParam Map<String, Object> params) {
        try {
            Map<String, Object> map = personService.foreignerSense(params);
            return render(map);
        } catch (Exception e) {
            log.info("[PersonController.foreignerSense,参数：{},异常信息{}]", params, e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 人员出入列表查询 ygy
     *
     * @param params
     * @return
     */
    @GetMapping("/person/access")
    public Map<String, Object> personAccess(@RequestParam Map<String, Object> params) {
        try {
            Map<String, Object> map = personService.personAccess(params);
            return render(map);
        } catch (Exception e) {
            log.info("[PersonController.personAccess,参数：{},异常信息{}]", params, e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 高频出入人员--{基本同“街道-人员感知（TOP10）”} ygy
     *
     * @param params
     * @return
     */
    @GetMapping("/person/sense/highAccess")
    public Map<String, Object> personSenseHighAccess(@RequestParam Map<String, Object> params) {
        try {
            Map<String, Object> map = personService.personSenseHighAccess(params);
            return render(map);
        } catch (Exception e) {
            log.info("[PersonController.personSenseHighAccess,参数：{},异常信息{}]", params, e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 个人出入记录 -ygy
     * @param params
     * @return
     */
    @GetMapping("/person/sense/highAccess/info")
    public Map<String,Object> personHighAccessByPersonId(@RequestParam Map<String,Object> params){
        try {
            Map<String, Object> map = personService.personHighAccessByPersonId(params);
            return render(map);
        } catch (Exception e) {
            log.info("[PersonController. personHighAccessByPersonId(,参数：{},异常信息{}]", params, e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 陌生人  个人抓拍记录-ygy
     * @param params
     * @return
     */
    @GetMapping("/person/stranger/list/info")
    public Map<String,Object> personStrangerByPersonId(@RequestParam Map<String,Object> params){
        try {
            Map<String, Object> map = personService.personStrangerByPersonId(params);
            return render(map);
        } catch (Exception e) {
            log.info("[PersonController.personStrangerByPersonId,参数：{},异常信息{}]", params, e.getMessage());
            return failRender(e);
        }
    }


    //    街道-人员基础信息 fake
    @GetMapping("/person/baseInfo2")
    public Map<String, Object> personBaseInfo2(@RequestParam Map<String, Object> params) {
        try {
            Map<String, Object> map1 = new HashMap<String, Object>();//fake
            map1.put("personTotalNum", 14474);
            map1.put("monthSenseNum", 202878);
            map1.put("todaySenseNum", 7149);
            map1.put("todaySerseStrangerNum", 3775);
            map1.put("permanentPersonNum", 13569);
            map1.put("floatPersonNum", 905);
            map1.put("monthAddFloatPersonNum", 21);
            return render(map1);
        } catch (Exception e) {
            log.info("[PersonController.personDistribute,参数：{},异常信息{}]", params, e.getMessage());
            return failRender(e);
        }
    }

    //    街道-人员分布信息 fake
    @GetMapping("/person/num2")
    public Map<String, Object> personDistribute2(@RequestParam Map<String, Object> params) {
        try {
            List<Map<String, Object>> listMaps = new ArrayList<>();
            Map<String, Object> map1 = new HashMap<String, Object>();
            Map<String, Object> map2 = new HashMap<String, Object>();
            Map<String, Object> map3 = new HashMap<String, Object>();
            Map<String, Object> map4 = new HashMap<String, Object>();
            Map<String, Object> map5 = new HashMap<String, Object>();

            map1.put("xqCode", "11");
            map1.put("xqName", "香汐海");
            map1.put("personNum", 1600);
            map1.put("percent", 11.05);
            map2.put("xqCode", "02");
            map2.put("xqName", "凤凰城");
            map2.put("personNum", 5436);
            map2.put("percent", 37.56);
            map3.put("xqCode", "12");
            map3.put("xqName", "海棠墅");
            map3.put("personNum", 210);
            map3.put("percent", 1.45);
            map4.put("xqCode", "13");
            map4.put("xqName", "官厅园");
            map4.put("personNum", 468);
            map4.put("percent", 3.23);
            map5.put("xqCode", "14");
            map5.put("xqName", "南岛小镇");
            map5.put("personNum", 6760);
            map5.put("percent", 46.70);

            listMaps.add(map1);
            listMaps.add(map2);
            listMaps.add(map3);
            listMaps.add(map4);
            listMaps.add(map5);

            return render(listMaps);
        } catch (Exception e) {
            log.info("[PersonController.personDistribute,参数：{},异常信息{}]", params, e.getMessage());
            return failRender(e);
        }
    }

    //    外籍人员感知 fake
    @GetMapping("/foreigner/sense2")
    public Map<String, Object> foreignerSense2(@RequestParam Map<String, Object> params) {
        try {
            String ip = PropertiesUtil.getLocalTomcatImageIp();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("totalRows", 3);
            map.put("currentPage", 1);
            List<Map<String, Object>> lists = new ArrayList<>();
            Map<String, Object> map1 = new HashMap<String, Object>();
            Map<String, Object> map2 = new HashMap<String, Object>();
            Map<String, Object> map3 = new HashMap<String, Object>();
            map1.put("name", "阿列克谢");
            map1.put("xqName", "凤凰城");
            map1.put("country", "俄罗斯");
            map1.put("phone", "13687956147");
            map1.put("passport", "EI125478I");
            map1.put("image", ip + "13687956147.jpg");
            map2.put("name", "阿普杜拉曼·阿卜迪");
            map2.put("xqName", "凤凰城");
            map2.put("country", "索马里");
            map2.put("phone", "15698745896");
            map2.put("passport", "EI2657894I");
            map2.put("image", ip + "15698745896.jpg");
            map3.put("name", "李桢茹");
            map3.put("xqName", "香汐海");
            map3.put("country", "韩国");
            map3.put("phone", "17865449875");
            map3.put("passport", "EI478458I");
            map3.put("image", ip + "17865449875.jpg");
            lists.add(map1);
            lists.add(map2);
            lists.add(map3);
            map.put("stranger", lists);

            return render(map);
        } catch (Exception e) {
            log.info("[PersonController.foreignerSense,参数：{},异常信息{}]", params, e.getMessage());
            return failRender(e);
        }
    }

    @GetMapping("/person/stream/rule3")
    public Map<String, Object> personStreamRule3(@RequestParam Map<String, Object> params) {
        try {
            Map map=new LinkedHashMap();
            if(params.get("areaId")==null||params.get("areaId").toString().trim().equals(""))
                params.put("areaId",0);
            int []dateListDay={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,
                    18,19,20,21,22,23};
            String []dateListWeek=new String[7];

            ArrayList<String> weekDays=DateUtil.getDays(7);
            Iterator<String> wdi=weekDays.iterator();
            int j=0;
            while (wdi.hasNext())
            {
                String wdt=wdi.next();
                //System.out.println(wdt);
                String wds=DateUtil.getWeek(wdt);
                dateListWeek[j]=wds;
                j++;
            }

            int []enterCountDay=new int[24];
            int []outCountDay=new int[24];

            int []enterCountWeek=new int[7];
            int []outCountWeek=new int[7];

            String todayYMDH=DateUtil.getTodayStartTime();
            String []ts=todayYMDH.split(" ");
            String todayYMD=ts[0];
            String beginTime;
            String endTime;
            Map tMap;
            List inL;
            List outL;
            for (int i=0;i<24;i++)
            {
                beginTime=todayYMD+" "+dateListDay[i]+":00:00";
                endTime=todayYMD+" "+dateListDay[i]+":59:59";
                enterCountDay[i]=0;
                outCountDay[i]=0;
                params.put("beginTime",beginTime);
                params.put("endTime",endTime);
                params.put("access","1");
                inL=personService.personStreamRules3(params);
                params.put("access","0");
                outL=personService.personStreamRules3(params);
                if(inL!=null&&!inL.isEmpty())
                {
                    tMap=(Map)inL.get(0);
                    enterCountDay[i]=Integer.parseInt(tMap.get("passNum").toString());
                }
                if(outL!=null&&!outL.isEmpty())
                {
                    tMap=(Map)outL.get(0);
                    outCountDay[i]=Integer.parseInt(tMap.get("passNum").toString());
                }
            }

            wdi=weekDays.iterator();
            j=0;
            while (wdi.hasNext())
            {
                String wdt=wdi.next();
                beginTime=wdt+" 00:00:00";
                endTime=wdt+" 23:59:59";
                enterCountWeek[j]=0;
                outCountWeek[j]=0;
                params.put("beginTime",beginTime);
                params.put("endTime",endTime);
                params.put("access","1");
                inL=personService.personStreamRules3(params);
                params.put("access","0");
                outL=personService.personStreamRules3(params);
                if(inL!=null&&!inL.isEmpty())
                {
                    tMap=(Map)inL.get(0);
                    enterCountWeek[j]=Integer.parseInt(tMap.get("passNum").toString());
                }
                if(outL!=null&&!outL.isEmpty())
                {
                    tMap=(Map)outL.get(0);
                    outCountWeek[j]=Integer.parseInt(tMap.get("passNum").toString());
                }
                j++;
            }
            Map dayMap=new LinkedHashMap();
            Map weekMap=new LinkedHashMap();
            dayMap.put("outCount",outCountDay);
            dayMap.put("dateList",dateListDay);
            dayMap.put("enterCount",enterCountDay);
            weekMap.put("outCount",outCountWeek);
            weekMap.put("dateList",dateListWeek);
            weekMap.put("enterCount",enterCountWeek);
            map.put("day",dayMap);
            map.put("week",weekMap);
            return render(map);
            //return render();
        } catch (Exception e) {
            log.info(e.toString());
            return failRender(e);
        }
    }

    @GetMapping("/person/stream/rule")
    public Map<String,Object> personAccessList(@RequestParam Map<String, Object> params) {
        try{
            Map<String,Object> map = personService.personStreamRules(params);
            return render(map);
        }catch (Exception e){
            log.info("[OpenRecordController.personAccessList,参数：{},异常信息{}]",params,e.getMessage());
            return failRender(e);
        }
    }

    /**
     * @description 人脸抓拍列表
     * @param params
     * @return map
     * @author zhoutao
     * @date 2019/12/28 11:43
     */
    @GetMapping("/person/capture/list")
    public Map<String, Object> personCaptureList(@RequestParam Map<String, Object> params){
        try{

            Map<String, Object> result = personService.personCaptureList(params);
            return render(result);
        }catch(Exception e){
            log.info("[OpenRecordController.personAccessList,参数：{},异常信息{}]",params,e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 个人一周 每天48段出入记录 ygy
     * @param personId
     * @return
     */
    @GetMapping("/person/stream/rule2/{personId}")
    public Map<String,Object> personStreamRule9(@PathVariable("personId") String personId){
        try{
            Map<String,Object> map = personService.personStreamRule9(personId);
            return render(map);
        }catch (Exception e){
            log.info("[OpenRecordController.personAccessList,参数：{},异常信息{}]",personId,e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 今日数据感知-ygy
     * @param params
     * @return
     */
    @GetMapping("/subdistrict/statistics/todaySense")
    public Map<String,Object> subdistrictStatisticsToday(@RequestParam Map<String,Object> params){
        try{
            Map<String,Object> map = personService.subdistrictStatisticsToday(params);
            return render(map);
        }catch (Exception e){
            log.info("[OpenRecordController.subdistrictStatisticsToday,参数：{},异常信息{}]",params,e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 国家列表 ygy
     * @param params
     * @return
     */
    @GetMapping("/country/list")
    public Map<String,Object> countryList(@RequestParam Map<String,Object> params){
        try{
            List<Map<String,Object>> lists = personService.countryList(params);
            return render(lists);
        }catch (Exception e){
            log.info("[OpenRecordController.countryList,参数：{},异常信息{}]",params,e.getMessage());
            return failRender(e);
        }
    }
}
