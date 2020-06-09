package com.windaka.suizhi.webapi.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface MsgSuspectedAddDao {

    /**
     * 疑似新增人/车  insert msg_suspected_add 表
     * @param params
     * @return
     */
    Integer insertMsgSuspectedAdd(Map<String,Object> params);

    /**
     * 疑似新增人/车  update msg_suspected_add 表
     * @param params
     * @return
     */
    Integer updateMsgSuspectedAdd(Map<String,Object> params);

    /**
     * 疑似新增人/车 并且超过阈值15天后重新设置短信发送状态
     * @return
     */
    Integer updateMsgSuspectedAddMoreThan15Days();

    /**
     * 疑似新增人/车  select msg_suspected_add 表
     * @param params
     * @return
     */
    int seleteMsgSuspectedAdd(Map<String,Object> params);
}
