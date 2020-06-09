package com.windaka.suizhi.mpi.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ConvertRtspRtmpConfig {

    @Value("${rtmp.openPath}")
    private String rtmpOpenPath;
    @Value("${rtmp.closePath}")
    private String rtmpClosePath;



}
