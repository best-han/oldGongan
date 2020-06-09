package com.windaka.suizhi.mpi.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 疑似迁出短信发送记录 hjt
 */
@Mapper
public interface MsgSuspectedQuitDao {

    /**
     *查询某段时间（前一天）疑似迁出的人未发送的列表
     * @param params
     * @return
     */
    List<Map<String,Object>> queryMsgSuspectedQuitList(Map params);

    /**
     * 更新短信发送状态
     * @param id
     * @return
     */
    int updateMsgSuspectedQuitById(int id);

}
