package com.windaka.suizhi.mpi.feign;

import com.windaka.suizhi.mpi.config.FeignHeaderInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * hjt
 */
@FeignClient(name = "sjwl-manageport",configuration = FeignHeaderInterceptor.class)
public interface ManageportClient {
    @GetMapping("/manageport-internal/getMsgRecords")//目前无，若同时操作表造成锁表，则需要manageport实现此接口
    public Map<String,Object> GetMsgRecords(@RequestParam Map<String, Object> params);

}
