package com.windaka.suizhi.mpi.config;

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
    private String basePath;
    private String httpPath;

}
