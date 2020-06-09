package com.windaka.suizhi.manageport.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.ftp")
@Data
public class FtpProperties {

    private String host;
    private int port;
    private String username;
    private String password;
    private String basePath;//ftp上传的根目录
    private String imagePah; // 图片地址
    private String httpPath;//回显地址

}
