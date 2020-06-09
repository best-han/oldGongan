package com.windaka.suizhi.mpi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName DatasourceProperties
 * @Description 数据源配置类
 * @Author lixianhua
 * @Date 2019/12/11 11:59
 * @Version 1.0
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource")
@Data
public class DataConnectProperties {
    private String url;
    private String username;
    private String password;
}
