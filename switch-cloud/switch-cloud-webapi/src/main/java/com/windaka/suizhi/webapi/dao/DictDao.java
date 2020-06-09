package com.windaka.suizhi.webapi.dao;

import org.apache.ibatis.annotations.Mapper;

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
     * 根据犯罪类型、字典标签  得到对应颜色表示 ygy
     * @param params
     * @return
     */
    String getDictColor(Map<String,Object> params);

    /**
     * 根据颜色、犯罪类型  得到字典标签 ygy
     * @param params
     * @return
     */
    List<Map<String,Object>> getDictLabel(Map<String,Object> params);
}
