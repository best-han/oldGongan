package com.windaka.suizhi.manageport.controller;

import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.manageport.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class PersonController extends BaseController {


    @Autowired
    private PersonService personService;



    /**
     * 添加小区人员
     * @param persons
     * @return
     */
    @PostMapping("/person")
    public Map<String,Object> savePerson(@RequestBody Map<String,Object> persons) {
        try{
            personService.savePersons((String) persons.get("xqCode"),(List<Map<String, Object>>) persons.get("persons"));
            return render();
        }catch (Exception e){
            log.info("[PersonController.savePerson,参数：{},异常信息{}]","",e.getMessage());
            return failRender(e);
        }
    }
    @PutMapping("/person")
    public Map<String,Object> updatePerson(@RequestBody Map<String,Object> person){
        try {
            personService.updatePerson(person);
            return render();
        }catch (Exception e){
            log.info("[PersonController.updatePerson,参数：{},异常信息{}]",person,e.getMessage());
            return failRender(e);
        }
    }

    @DeleteMapping("/person/{manageId}")
    public Map<String,Object> deletePerson(@PathVariable("manageId") String manageId){
        try {
            personService.deletePerson(manageId);
            return render();
        }catch (Exception e){
            log.info("[PersonController.deleltePerson,参数：{},异常信息{}]",manageId,e.getMessage());
            return failRender(e);
        }
    }

    @GetMapping("/person/{personCode}")
    public Map<String,Object> queryPerson(@PathVariable("personCode") String personCode){
        try {
            Map<String,Object> personMap=personService.queryPerson(personCode);
            return render(personMap);
        }catch (Exception e){
            log.info("[PersonController.queryPerson,参数：{},异常信息{}]",personCode,e.getMessage());
            return failRender(e);
        }
    }

    @GetMapping("/person/list")
    public Map<String,Object> queryPersonList(@RequestParam Map<String, Object> params){

        try{
            Page page = personService.queryPersonList(params);
            return  render(page);
        }catch (Exception e){
            log.info("[PersonController.queryPersonList,参数：{},异常信息{}]",params,e.getMessage());
            return failRender(e);
        }

    }

    /**
     * 根据图片查询小区单个人员Code
     * @param params
     */
    @GetMapping("/guanliduan")
    public Map<String,Object> queryPersonCode(@RequestParam Map<String, Object> params) {
        try{
            Map map = new HashMap();
            map.put("personCode",personService.queryPersonCode(params));
            return map;
        }catch (Exception e){
            log.info("[PersonController.queryPersonCode,参数：{},异常信息{}]","",e.getMessage());
            return failRender(e);
        }
    }


}
