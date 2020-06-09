package com.windaka.suizhi.manageport.service;

import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.api.user.Role;
import com.windaka.suizhi.common.exception.OssRenderException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface PersonService {
    /**
     * 批量保存小区人员
     * @param xq_code
     * @param person
     * @throws OssRenderException
     */
    void savePersons(String xq_code,List<Map<String,Object>> person) throws OssRenderException;

    /**
     * 小区人员修改
     * @param person
     * @throws OssRenderException
     */
    void updatePerson(Map<String,Object> person) throws OssRenderException, IOException;

    /**
     * 删除小区人员
     * @param personCode
     * @throws OssRenderException
     */
    void deletePerson(String personCode) throws OssRenderException;

    /**
     * 查询小区人员
     * @param personCode
     * @throws OssRenderException
     */
    Map<String,Object> queryPerson(String personCode) throws OssRenderException;

    /**
     * 分页查询小区人员列表
     * @param params
     * @return
     * @throws OssRenderException
     */
     Page<Map<String,Object>> queryPersonList(Map<String, Object> params) throws OssRenderException;

    /**
     * 根据图片查询小区单个人员Code
     * @param params
     * @return
     */
    String queryPersonCode(Map<String, Object> params) throws OssRenderException;

}
