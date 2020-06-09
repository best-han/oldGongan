package com.windaka.suizhi.mpi.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class SearchByImageConfig {
    @Value("${search.image-by-image-path}")
    private String searchPath;

}
