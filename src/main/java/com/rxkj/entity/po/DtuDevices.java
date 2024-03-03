package com.rxkj.entity.po;

import lombok.Data;

import java.util.Date;

@Data
public class DtuDevices {
    //DTU序列号
    private String serialNumber;
    //DTU上线时间
    private Date uptime;
    //DTU状态
    private Integer status;
    //DTU下线时间
    private Date downTime;
    //DTU类型 modbusDTU或TCPDTU
    private String deviceType;
    //DTU接口类型
    private String interfaceType;
    //DTU密匙
    private String deviceKey;
    //DTU通信协议
    private String deviceProtocols;
}
