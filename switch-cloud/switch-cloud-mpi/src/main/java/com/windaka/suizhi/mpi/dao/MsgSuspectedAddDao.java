package com.windaka.suizhi.mpi.dao;

import com.windaka.suizhi.mpi.model.CarInOut;
import com.windaka.suizhi.mpi.model.RecordAbnormal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 疑似新增短信发送记录 hjt
 */
@Mapper
public interface MsgSuspectedAddDao {

    /**
     *查询某段时间（前一天）疑似新增的人/车未发送的列表
     * @param params
     * @return
     */
    List<Map<String,Object>> queryMsgSuspectedAddList(Map params);

    /**
     * 更新短信发送状态
     * @param id
     * @return
     */
    int updateMsgSuspectedAddById(int id);

}
