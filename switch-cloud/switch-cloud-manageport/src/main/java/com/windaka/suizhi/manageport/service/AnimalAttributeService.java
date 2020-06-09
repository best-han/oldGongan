package com.windaka.suizhi.manageport.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.util.Map;

public interface AnimalAttributeService {

    /**
     * 宠物属性保存
     * @param params
     */
    public void saveAnimalAttribute(Map<String, Object> params) throws OssRenderException;

    /**
     * 宠物属性修改
     * @param params
     * @throws OssRenderException
     */
    public void updateAnimalAttribute (Map<String, Object> params) throws OssRenderException;

    /**
     * 宠物属性删除
     * @param params
     * @throws OssRenderException
     */
    public void deleteAnimalAttribute (Map<String, Object> params) throws OssRenderException;
}
