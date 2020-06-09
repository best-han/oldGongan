package com.windaka.suizhi.webapi.service.impl;

import com.windaka.suizhi.common.utils.PageUtil;
import com.windaka.suizhi.webapi.dao.DictDao;
import com.windaka.suizhi.webapi.service.DictService;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName DictServiceImpl
 * @Description 字典实现类
 * @Author lixianhua
 * @Date 2019/12/25 14:29
 * @Version 1.0
 */
@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private DictDao dictDao;

    /**
     * 功能描述: 获取条件获取字典数据
     *
     * @auther: lixianhua
     * @date: 2019/12/25 14:33
     * @param:
     * @return:
     */
    @Override
    public List<Map<String, Object>> getEventList(Map<String, Object> params) {
        if (null != params.get("dictLabels")) {
            String labels = (String) params.get("dictLabels");
            params.put("dictLabels", Arrays.asList(labels.split(",")));
        }
        // 可继续添加其他条件
        return this.dictDao.getDictList(params);
    }
}
