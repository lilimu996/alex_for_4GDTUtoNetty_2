package com.rxkj.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * by alex
 * 2020.11.18
 * 泵运行参数类
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PumpOperateData implements Serializable {

    //id
    private Integer id;

    //所属泵的id
    private Integer ownerPumpId;

    //所属设备通信id
    private String ownerDeviceId;

    //实时电压
    private Double voltage;

    //实时电流
    private Double current;

    //实时流量
    private Double flow;

    //采集时间
    private Date collectionTime;

    //备注
    private String mark;
}
