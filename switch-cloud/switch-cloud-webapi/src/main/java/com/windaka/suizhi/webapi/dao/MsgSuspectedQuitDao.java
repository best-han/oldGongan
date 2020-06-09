package com.windaka.suizhi.webapi.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface MsgSuspectedQuitDao {

    /**
     * 疑似迁出人  insert msg_suspected_quit 表
     * @param params
     * @return
     */
    Integer insertMsgSuspectedQuit(Map<String,Object> params);


    /**
     * 疑似迁出人  update msg_suspected_quit 表
     * @param params
     * @return
     */
    Integer updateMsgSuspectedQuit(Map<String,Object> params);


    /**
     * 疑似新增人/车  select msg_suspected_quit 表 通过个数判断是否存在此记录
     * @param params
     * @return
     */
    int selectMsgSuspectedQuit(Map<String,Object> params);
    int selectMsgSuspectedQuit2(Map<String,Object> params);


}
