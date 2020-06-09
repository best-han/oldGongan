package com.windaka.suizhi.webapi.service;

import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.api.user.Role;
import com.windaka.suizhi.common.exception.OssRenderException;
import org.omg.CORBA.OBJ_ADAPTER;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface PersonService {
    /**
     * 批量保存小区人员
     *
     * @param xq_code
     * @param person
     * @throws OssRenderException
     */
    void savePersons(String xq_code, List<Map<String, Object>> person) throws OssRenderException, IOException;

    /**
     * 小区人员修改
     *
     * @param person
     * @throws OssRenderException
     */
    void updatePerson(Map<String, Object> person) throws OssRenderException;

    /**
     * 删除小区人员
     *
     * @param personCode
     * @throws OssRenderException
     */
    void deletePerson(String personCode) throws OssRenderException, IOException;

    /**
     * 查询小区人员
     *
     * @param personCode
     * @throws OssRenderException
     */
    Map<String, Object> queryPerson(String personCode) throws OssRenderException;

    /**
     * 分页查询街道人员列表 ygy
     *
     * @param params
     * @return
     * @throws OssRenderException
     */
    Page<Map<String, Object>> queryPersonList(Map<String, Object> params) throws OssRenderException;

    /**
     * 根据图片查询小区单个人员Code
     *
     * @param params
     * @return
     */
    String queryPersonCode(Map<String, Object> params) throws OssRenderException;

    /**
     * 街道-人口基础信息-yin
     * @param params
     * @return
     * @throws OssRenderException
     */
    Map<String, Object> personBaseInfo(Map<String, Object> params) throws OssRenderException;

    /**
     * 人员性质统计 hjt
     * @param params
     * @return
     * @throws OssRenderException
     */
    List<Map<String, Object>> queryPersonPropertyStatistics(Map<String, Object> params) throws OssRenderException;

    /**
     * 员性质统计------ygy--实有人口页面的饼图，常住人口、流动人口、境外人员
     * @param params
     * @return
     * @throws OssRenderException
     */
    List<Map<String, Object>> queryPersonPropertyStatistics2(Map<String, Object> params) throws OssRenderException;


    /**
     * 街道-人员分布信息-yin
     * @param params
     * @return
     * @throws OssRenderException
     */
    List<Map<String,Object>> personDistribute(Map<String,Object> params) throws OssRenderException;


    /**
     * 街道-人口出入信息统计-ygy
     * @param params
     * @return
     */


    Map<String,Object> personStream(Map<String,Object> params) throws OssRenderException;

    /**
     * 流动人口统计 hjt
     * @param params
     * @return
     * @throws OssRenderException
     */
    Map<String, Object> queryFloatingPersonStatistics(Map<String, Object> params) throws OssRenderException;

    /**
     * 街道-人员感知-ygy
     * @param params
     * @return
     * @throws OssRenderException
     */
    Map<String,Object> personSense(Map<String,Object> params) throws OssRenderException;


    /**
     * 街道-人员档案-基础信息查询（单个查询） ygy
     * @param personId
     * @return
     * @throws OssRenderException
     */
    Map<String,Object> queryBaseInfoByPersonId(String personId) throws OssRenderException;

    /**
     * 流动人口统计 hjt
     * @param params
     * @return
     * @throws OssRenderException
     */
    List<Map<String, Object>> queryPersonActivityStatistics(Map<String, Object> params) throws OssRenderException;

    /**
     * 街道-人员档案-车辆信息
     * @param personId
     * @return
     * @throws OssRenderException
     */
    List<Map<String,Object>> carInfo(String personId) throws OssRenderException;

    /**
     * 街道-人员档案-房产信息 ygy
     * @param personId
     * @return
     * @throws OssRenderException
     */
    List<Map<String,Object>> houseInfo(String personId) throws OssRenderException;

    /**
     * 街道-特殊人员管理（列表查询） 接口 dee
     * @param params
     * @return
     * @throws OssRenderException
     */
    List<Map<String,Object>> queryPersonSpecialList(Map<String, Object> params) throws OssRenderException;

    /**
     * 街道-人员档案-布控库标签 ygy
     * @param personId
     * @return
     * @throws OssRenderException
     */
    List<Map<String,Object>> faceType(String personId) throws OssRenderException;

    /**
     * 街道-人员档案-抓拍记录 ygy
     * @param personId
     * @return
     * @throws OssRenderException
     */
    Map<String,Object> captureInfoByPersonId(String personId,Map<String,Object> params) throws  OssRenderException;

    /**
     * 街道-人员档案-抓拍记录详情 ygy
     * @param personId
     * @return
     */
    List<Map<String,Object>> captureInfoDetailsByPersonId(String personId) throws OssRenderException;

    /**
     * 街道-人员档案-关系图 ygy
     * @param personId
     * @return
     * @throws OssRenderException
     */
    Map<String,Object> personRelationByPersonId(String personId)throws OssRenderException;

    /**
     * 街道-出入规律-人员出入信息统计 ygy
     * @return
     */
    Map<String,Object> personStreamRule(Map<String,Object> params) throws OssRenderException;

    /**
     * 公安-街道-人员感知（列表查） ygy
     * @param params
     * @return
     * @throws OssRenderException
     */
    Map<String,Object> strangerSense(Map<String,Object> params) throws OssRenderException;

    /**
     * 公安-街道-现有人员列表查询 ygy
     * @param params
     * @return
     * @throws OssRenderException
     */
    Map<String,Object> personOwnerList(Map<String,Object> params)throws OssRenderException;
    /**
     * 街道-疑似迁出人员列表查询(按小区) ygy
     * @param params
     * @return
     * @throws OssRenderException
     */
    Map<String,Object> personQuitList(Map<String,Object> params) throws OssRenderException;
    Map<String,Object> personQuitList2(Map<String,Object> params) throws OssRenderException;

    /**
     * 疑似新增人员列表查询 ygy
     * @param params
     * @return
     * @throws OssRenderException
     */
    Map<String,Object> personAddedList(Map<String,Object> params) throws OssRenderException;

    /**
     * 街道-特殊人员分布信息 dee
     * @param params
     * @return
     * @throws OssRenderException
     */

    List<Map<String,Object>> querySpecialPersonDistribution(Map<String,Object> params) throws OssRenderException;



    /**
     * 公安-外籍人员列表查询） ygy
     * @param params
     * @return
     * @throws OssRenderException
     */
    Map<String,Object> foreignerSense(Map<String,Object> params) throws OssRenderException;

    /**
     * 人员档案-异常行为标签 ygy
     * @param personId
     * @return
     */
    List<Map<String,Object>> personAbnormalType(String personId) throws OssRenderException;

    /**
     * 人员档案-房产信息 ygy
     * @param personId
     * @return
     * @throws OssRenderException
     */
    Map<String,Object> personHouseBypersonId(String personId) throws OssRenderException;

    /**
     * 人员出入列表查询 ygy
     * @param params
     * @return
     * @throws OssRenderException
     */
    Map<String,Object> personAccess(Map<String,Object> params) throws OssRenderException;

    /**
     * 高频出入人员--{基本同“街道-人员感知（TOP10）”} ygy
     * @param params
     * @return
     * @throws OssRenderException
     */
    Map<String,Object>  personSenseHighAccess(Map<String,Object> params) throws OssRenderException;

    /**
     * 个人出入记录 -ygy
     * @param params
     * @return
     * @throws OssRenderException
     */
    Map<String,Object> personHighAccessByPersonId(Map<String,Object> params) throws OssRenderException;

    List<Map<String,Object>> personStreamRules3(Map<String,Object> params) throws OssRenderException;

    Map<String,Object> personStreamRules(Map<String,Object> params) throws OssRenderException, ParseException;

    Map<String,Object> personStreamRule9(String personId) throws OssRenderException;

    /**
     * 陌生人  个人抓拍记录-ygy
     * @param params
     * @return
     * @throws OssRenderException
     */

    Map<String,Object> personStrangerByPersonId(Map<String,Object> params) throws OssRenderException;

    /**
     * 今日数据感知-ygy
     * @param params
     * @return
     * @throws OssRenderException
     */
    Map<String,Object> subdistrictStatisticsToday(Map<String,Object> params) throws OssRenderException;
    /**
     * @description 获取人脸抓拍列表
     * @param params
     * @return map
     * @author zhoutao
     * @date 2019/12/28 10:05
     */
    Map<String, Object> personCaptureList(Map<String, Object> params);


    /**
     * 国家列表 ygy
     * @param params
     * @return
     */
    List<Map<String,Object>> countryList(Map<String,Object> params);

}
