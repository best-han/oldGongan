package com.windaka.suizhi.webapi.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SingleTableDao {

    /**
     * 单表查询 house zdq
     * @param map
     * @return
     */
    List<Map<String,Object>> selectHouseRoom(Map<String,Object> map);

    /**
     * 单表查询 小区 zdq
     * @param map
     * @return
     */
    List<Map<String,Object>> selectHtXqInfo(Map<String,Object> map);

    /**
     * 单表查询 单元 zdq
     * @param map
     * @return
     */
    List<Map<String,Object>> selectHouseCell(Map<String,Object> map);

    /**
     * 单表查询 楼栋 zdq
     * @param map
     * @return
     */
    List<Map<String,Object>> selectHouseBuilding(Map<String,Object> map);

    /**
     * 单表查询 房屋-户主关系 zdq
     * @param map
     * @return
     */
    List<Map<String,Object>> selectHouseOwnerRoom(Map<String,Object> map);

    /**
     * 单表查询 个人信息 zdq
     * @param map
     * @return
     */
    List<Map<String,Object>> selectZsPersonInfo(Map<String,Object> map);

    /**
     * 房间 居住人数 zdq
     * @param map
     * @return
     */
    List<Map<String,Object>> selectRoomLiveNum(Map<String,Object> map);

    /**
     * 房间 绑定车辆数 zdq
     * @param map
     * @return
     */
    List<Map<String,Object>> selectBindingCarNum(Map<String,Object> map);

    /**
     * 单表查询 登记车辆 zdq
     * @param map
     * @return
     */
    List<Map<String,Object>> selectCarInfo(Map<String,Object> map);

    /**
     * 出入车辆感知 zdq
     * @param map
     * @return
     */
    List<Map<String,Object>> selectCarAccessRecordSenseNum(Map<String,Object> map);

    /**
     * 出入车辆单表查询 zdq
     * @param map
     * @return
     */
    List<Map<String,Object>> selectCarAccessRecord(Map<String,Object> map);

    /**
     * 抓拍车辆 maxId zdq
     * @param map
     * @return
     */
    List<Map<String,Object>> selectCarAttributeMaxId(Map<String,Object> map);

    /**
     * 抓拍车辆单表查询
     * @param map
     * @return
     */
    List<Map<String,Object>> selectCarAttribute(Map<String,Object> map);

    /**
     * 登记车辆&居住身份 zdq
     * @param map
     * @return
     */
    List<Map<String,Object>> selectCarInfoAndHouseOwnerRoom(Map<String,Object> map);

    /**
     * 房屋用水记录 zdq
     * @param map
     * @return
     */
    List<Map<String,Object>> selectWaterUserAndRoomInfo(Map<String,Object> map);

    /**
     * 房屋欠费记录 zdq
     * @param map
     * @return
     */
    List<Map<String,Object>> selectArrearageRecordAndRoomInfo(Map<String,Object> map);

    List<Map<String,Object>> selectArrearageRecordAndRoomInfoLikeStr(Map<String,Object> map);

    /**
     * 房屋最后缴费时间 zdq
     * @param map
     * @return
     */
    List<Map<String,Object>> selectArrearageLastPaymentTime(Map<String,Object> map);

    List<Map<String,Object>> selectArrearageLastPaymentTimeWy(Map<String,Object> map);

    /**
     * 房屋缴费记录 zdq
     * @param map
     * @return
     */
    List<Map<String,Object>> selectPayRecord(Map<String,Object> map);

    /**
     * 房屋用水记录 zdq
     * @param map
     * @return
     */
    List<Map<String,Object>> selectWaterUse(Map<String,Object> map);
}
