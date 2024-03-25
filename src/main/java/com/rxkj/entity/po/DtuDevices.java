package com.rxkj.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.lang.reflect.Type;
import java.util.Date;

@Data
public class DtuDevices {
    //DTU id
    @TableId(type= IdType.AUTO)
    private Long dtuId;
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
