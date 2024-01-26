package com.rxkj.message;

import lombok.Data;

@Data
public class SseMessage {

    private Integer total;
    private String data;
    /**
     * 煤粉取样器设备号
     */
    private Integer samplerId;
    /**
     * plc设备号
     */
    private Integer plcId;
    /**
     * 运动状态 00:前进 01:后退
     */
    private String samplerStatus;
    /**
     * 进气阀(球阀C) 0:球阀关闭 1:球阀打开 -1:球阀异常
     */
    private Integer inletValve;
    /**
     * 排气阀(球阀B) 0:球阀关闭 1:球阀打开 -1:球阀异常
     */
    private Integer ventingValve;
    /**
     * 取样阀(球阀A) 0:球阀关闭 1:球阀打开 -1:球阀异常
     */
    private Integer sampleValve;
    /**
     * DTU序列号
     */
    private String dtuSerialNumber;
    /**
     * DTU状态 0：dtu离线 1：dtu在线
     */
    private Integer dtuStatus;
}
