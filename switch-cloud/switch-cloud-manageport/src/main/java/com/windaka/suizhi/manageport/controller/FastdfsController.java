package com.windaka.suizhi.manageport.controller;

import com.windaka.suizhi.manageport.service.FastdfsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @ClassName FastdfsController
 * @Description 测试类
 * @Author lixianhua
 * @Date 2019/12/10 15:58
 * @Version 1.0
 */
@RestController
@Slf4j
public class FastdfsController {

    @Autowired
    private FastdfsService fastdfsService;


    @PostMapping("/image")
    public void uploadImage(@RequestParam("image") MultipartFile image) {
        try {
            Map<String ,Object> map = fastdfsService.uploadFile(image.getInputStream(),"jpg");
            System.out.println(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
