package com.windaka.suizhi.manageport.service.impl;

import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.manageport.dao.AnimalAttributeDao;
import com.windaka.suizhi.manageport.service.AnimalAttributeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class AnimalAttributeServiceImpl implements AnimalAttributeService {

    @Autowired
    AnimalAttributeDao animalAttributeDao;

    @Override
    public void saveAnimalAttribute(Map<String, Object> params) throws OssRenderException{

    }

    @Override
    public void updateAnimalAttribute (Map<String, Object> params) throws OssRenderException{

    }

    @Override
    public void deleteAnimalAttribute (Map<String, Object> params) throws OssRenderException{

    }


}
