package com.windaka.suizhi.api.user;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 物业信息实体(登陆用，放入缓存中)
 * @author: Windaka
 */
@Data
//开启链式调用
@Accessors(chain = true)
public class WyAndXqInfo implements Serializable {
    private static final long serialVersionUID = 7443655181690659169L;
    //物业编码
    private String wyCode;
    //物业名称
    private String wyName;
    //小区编码
    private String xqCode;
    //小区名称
    private String xqName;
}
