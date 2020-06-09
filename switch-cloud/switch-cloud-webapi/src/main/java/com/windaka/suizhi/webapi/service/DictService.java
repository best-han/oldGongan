package com.windaka.suizhi.webapi.service;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName DictService
 * @Description 字典接口
 * @Author lixianhua
 * @Date 2019/12/25 14:29
 * @Version 1.0
 */
public interface DictService {
    /**
     * 功能描述: 获取事件类型
     * @auther: lixianhua
     * @date: 2019/12/25 14:32
     * @param:
     * @return:
     */
    List<Map<String, Object>> getEventList(Map<String, Object> params);
}
