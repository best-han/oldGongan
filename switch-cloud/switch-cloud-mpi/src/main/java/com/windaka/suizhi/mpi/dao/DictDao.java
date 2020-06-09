package com.windaka.suizhi.mpi.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName DictDao
 * @Description 字典dao层
 * @Author lixianhua
 * @Date 2019/12/25 14:30
 * @Version 1.0
 */
@Mapper
public interface DictDao {
    /**
     * 功能描述: 根据条件获取字典数据
     *
     * @auther: lixianhua
     * @date: 2019/12/25 14:43
     * @param:
     * @return:
     */
    List<Map<String, Object>> getDictList(Map<String, Object> params);

    /**
     * 功能描述: 获取证件号码
     * @auther: lixianhua
     * @date: 2020/1/11 15:11
     * @param:
     * @return:
     */
    @Select("SELECT * FROM temp_hb_info")
    List<String> getIdNoList();
    /**
     * 功能描述: 添加
     * @auther: lixianhua
     * @date: 2020/1/11 15:42
     * @param:
     * @return:
     */
    @Insert("insert into temp_hb_info(id_no) values(#{idNo})")
    Integer insertHbRecord(String idNo);
}
