package com.windaka.suizhi.webapi.dao;

import com.windaka.suizhi.common.exception.OssRenderException;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.omg.CORBA.OBJ_ADAPTER;

import java.rmi.MarshalledObject;
import java.util.List;
import java.util.Map;

@Mapper
public interface PersonDao {
    /**
     * 新增人员
     *
     * @param person
     * @return
     */
    int savePerson(Map<String, Object> person);

    /**
     * 查询最大ID
     *
     * @param
     * @return
     */
    @Select("select MAX(id) from zs_person_info")
    String maxId();

    /**
     * 修改小区人员
     *
     * @param person
     * @return
     */
    int updatePerson(Map<String, Object> person);

    /**
     * 小区人员删除
     *
     * @param personCode
     * @return
     */
    int deletePerson(String personCode);


    //region  查询

    /**
     * 获取全部小区人员量（只查未删除的）
     *
     * @param params
     * @return
     */
    int totalRows(Map<String, Object> params);

    /**
     * 查询小区单个人员
     *
     * @param personCode
     * @return
     */
    Map<String, Object> queryPerson(String personCode);

    /**
     * 根据条件查询用户列表
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> queryPersonList(Map<String, Object> params);

    /**
     * 根据人名搜索用户列表
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> queryPersonByNameList(Map<String, Object> params);
    //endregion

    /**
     * 根据图片查询小区单个人员Code
     *
     * @param img
     * @return
     */
    String queryPersonCode(@Param("img") String img);


    /**
     * 街道-人口基础信息 ygy
     *
     * @param params
     * @return
     */
    int personTotalNum(Map<String, Object> params);//人口总数

    int monthSenseNum(Map<String, Object> params);//本月感知人数

    int todaySenseNum(Map<String, Object> params);//当日感知人数

    int todaySenseStrangerNum(Map<String, Object> params);//当日感知陌生人数

    int permanentPersonNum(Map<String, Object> params);//常住人口数量

    int floatPersonNum(Map<String, Object> params);//流动人口数量

    int monthAddFloatPersonNum(Map<String, Object> params);//本月新增流动人口

    int foreignerPersonNum(Map<String,Object> params);//外籍人数量


    /**
     * 人员分布信息
     *
     * @param xqCode
     * @return
     */

    int personTotalXq(@Param("xqCode") String xqCode);//each小区总人数

    List<Map<String, Object>> infoXq(Map<String, Object> params);//小区list:xqCode xqName

    /**
     * 街道-人员出入信息统计-ygy
     *
     * @param params
     * @return
     */

    List<Map<String, Object>> personStreamDay(Map<String, Object> params);//统计一天24小时

    List<Map<String, Object>> personStreamMonth(Map<String, Object> params);//统计一个月30天

    List<Map<String, Object>> personStreamWeek(Map<String, Object> params);//近一周


    /**
     * 街道-人员感知-ygy
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> personSensePermanent(Map<String, Object> params);//统计常住人口

    List<Map<String, Object>> personSenseFlow(Map<String, Object> params);//流动人口

    List<Map<String, Object>> personSenseStrange(Map<String, Object> params);//陌生人


    /**
     * 街道-人员档案-基础信息查询（单个查询） ygy
     *
     * @param personId
     * @return
     */
    List<Map<String, Object>> queryBaseInfoByPersonId(@Param("personId") String personId);

    /**
     * 街道-人员档案-车辆信息
     *
     * @param personId
     * @return
     */
    List<Map<String, Object>> carInfo(@Param("personId") String personId);

    /**
     * 街道-人员档案-房产信息 ygy
     *
     * @param personId
     * @return
     */
    List<Map<String, Object>> houseInfo(@Param("personId") String personId);

    /**
     * 按小区查询特殊人员列表 dee
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> queryPersonSpecialListByXq(Map<String, Object> params);

    /**
     * 按街道查询特殊人员列表 dee
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> queryPersonSpecialListBySub(Map<String, Object> params);

    /**
     * 按地区查询特殊人员列表 dee
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> queryPersonSpecialListByArea(Map<String, Object> params);

    /**
     * 街道-人员档案-布控库标签 ygy
     *
     * @param personId
     * @return
     */
    List<Map<String, Object>> faceType(@Param("personId") String personId);



    /**
     * 街道-人员档案-抓拍记录详情 ygy
     *
     * @param personId
     * @return
     */
    List<Map<String, Object>> captureInfoDetailsByPersonId(@Param("personId") String personId);

    /**
     * 街道-人员档案-关系图 ygy
     *
     * @param
     * @return
     */
    List<Map<String, Object>> queryPersonInfo(Map<String, Object> map);

    List<Map<String, Object>> queryPersonFriend(Map<String, Object> map);

    /**
     * 按小区查询特殊人员分布信息 dee
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> querySpecialPersonDistributionByXq(Map<String, Object> params);

    /**
     * 按街道查询特殊人员分布信息 dee
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> querySpecialPersonDistributionBySub(Map<String, Object> params);

    /**
     * 按地区查询特殊人员分布信息 dee
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> querySpecialPersonDistributionByArea(Map<String, Object> params);

    /**
     * 公安-街道-人员感知（列表查） ygy
     *
     * @param params
     * @return
     */
    int totalStrangerSense(Map<String, Object> params);

    List<Map<String, Object>> strangerSense(Map<String, Object> params);

    /**
     * 公安-街道-现有人员列表查询 ygy
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> personOwnerList(Map<String, Object> params);

    int totalPersonOwnerList(Map<String, Object> params);

    /**
     * 街道-疑似迁出人员列表查询(按小区) ygy
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> personQuitList(Map<String, Object> params);

    int totalPersonQuitList(Map<String, Object> params);


    /**
     * 公安-外籍人员感知（列表查询） ygy
     *
     * @param params
     * @return
     * @throws OssRenderException
     */
    List<Map<String, Object>> foreignerSense(Map<String, Object> params);

    int totalForeignerSense(Map<String, Object> params);


    /**
     * 人员档案-异常行为标签 ygy
     *
     * @param personId
     * @return
     */
    List<Map<String, Object>> personAbnormalType(@Param("personId") String personId);

    /**
     * 人员档案-房产信息 ygy
     *
     * @param personId
     * @return
     */
    Map<String, Object> personHouseByPersonId(@Param("personId") String personId);

    List<Map<String, Object>> houseListByPersonId(@Param("personId") String personId);

    /**
     * 人员出入列表查询 ygy
     *
     * @param params
     * @return
     */
    int personAccessTotal(Map<String, Object> params);

    List<Map<String, Object>> personAccess(Map<String, Object> params);


    /**
     * 高频出入人员--{基本同“街道-人员感知（TOP10）”} ygy
     *
     * @param params
     * @return
     */
    int personSenseHighAccessTotal(Map<String, Object> params);

    List<Map<String, Object>> personSenseHighAccess(Map<String, Object> params);

    List<Map<String,Object>>  personHighAccessByPersonId(Map<String,Object> params);//个人出入记录 -ygy

    /**
     * 陌生人  个人抓拍记录-ygy
     * @param params
     * @return
     */
    List<Map<String,Object>> personStrangerByPersonId(Map<String,Object> params);


    int totalPersonOwnerList2(Map<String, Object> params);

    List<Map<String, Object>> personOwnerList2(Map<String, Object> params);

    List<Map<String, Object>> queryZsPersonInfoByOwnerId(String ownerId);

    List<Map<String, Object>> personInfoByPersonId(String personId);//根据personId 获取 个人信息

    String personRoomIdByPersonId(String personId);//根据personId 获取roomId
    List<Map<String,Object>> getPersonByRoomId(String roomId);

    List<Map<String, Object>> personStreamRules3(Map<String, Object> params);

    String xqNameByXqCode(@Param("xqCode") String xqCode);//根据小区code 得小区名

    List<Map<String,Object>> getPersonAttr(String id); //根据 face_person_attr id 获取记录
    List<Map<String,Object>> getPersonAttrByPersonId(Map<String,Object> innerParam); //根据 face_person_attr personId 获取记录

    int personStreamRulesWeekIn(Map<String,Object> params);//获取一周的 入记录
    int personStreamRulesWeekOut(Map<String,Object> params);//获取一周的 出记录
    int personStreamRulesDaysIn(Map<String,Object> params);//获取本日24小时的入记录
    int personStreamRulesDaysOut(Map<String,Object> params);//获取本日24小时的出记录
    int personStreamRulesThirthyDaysIn(Map<String,Object> params);//获取近30天的入记录
    int personStreamRulesThirtyDaysOut(Map<String,Object> params);//获取近30天的出记录
    int captureInfoByPersonId(Map<String,Object> params);//获取30天抓拍次数

    List<Map<String,Object>> getOwnerRoomByPersonId(String personId);//根据personiD获取房屋信息

    List<Map<String,Object>> quitPersonList(Map<String,Object> params);//疑似迁出人
    List<Map<String,Object>> quitCarList(Map<String,Object> params);//疑似迁出车
    List<Map<String,Object>> quitPersonAndCarList(Map<String,Object> params);//疑似迁出人和车

    /**
     * 疑似新增人员列表查询 ygy
     * @param params
     * @return
     */
    List<Map<String,Object>> personAddedList(Map<String,Object> params);

    int personStreamRules9(Map<String,Object> params);

    /**
     * 今日数据感知-ygy
     * @param params
     * @return
     */
    int todayPersonSenseNum(Map<String,Object> params);//今日人员出入数量
    int  todayCarSenseNum(Map<String,Object> params);  //今日车辆出入
    int abnormalTotalNum(Map<String,Object> params);//今日异常数量
    int faceCaptureNum(Map<String,Object> params);//人脸抓拍（新增）
    int carCaptureNum(Map<String,Object> params);//车辆抓拍（新增）

    /**
     * @description 人脸抓拍列表 zhoutao
     * @param  params
     * @return List
     * @author zhoutao
     * @date 2019/12/28 14:31
     */
    List<Map<String, Object>> personCaptureList(Map<String, Object> params);
    List<Map<String, Object>> personCaptureList2(Map<String, Object> params);//根据人名获取个人人脸抓拍的列表


    /**
     * @description 人脸抓拍总条数
     * @return int
     * @author zhoutao
     * @date 2019/12/30 10:24
     */
    int personCaptureNum(Map<String, Object> params);


    /**
     * 国家列表 ygy
     * @param params
     * @return
     */
    List<Map<String,Object>> countryList(Map<String,Object> params);

    /**
     * 根据证件号码更新人员信息表中的人员库主键 hjt
     * @param personPaperNum
     * @return
     */
    int updatePersonFacePersonTypeIdByPaperNum(String personPaperNum);
}
