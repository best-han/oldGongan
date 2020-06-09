package com.windaka.suizhi.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @description:调用sjwl-platsys接口，完成管理员子系统关联查询
 * @author: hjt
 * @create: 2018-12-06 11:07
 * @version: 1.0.0
 **/
@FeignClient("sjwl-platsys")
public interface PlatSysClient {

    /**
     * 查询使用中的所有APP信息
     * @return
     */
    @GetMapping("apps/apps-anon/internal/list")
    public Map<String, Object> queryListAnon();

}
