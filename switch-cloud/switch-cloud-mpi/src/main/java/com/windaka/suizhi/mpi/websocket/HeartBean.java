package com.windaka.suizhi.mpi.websocket;

import lombok.Data;

@Data
public class HeartBean {
    private boolean isHeart;//是否已经心跳
    private long heartTime;//上一次心跳时间

}
