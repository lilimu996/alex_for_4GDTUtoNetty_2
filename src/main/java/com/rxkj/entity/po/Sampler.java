package com.rxkj.entity.po;

import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class Sampler {

    //设备索引
    private Integer idsampler;
    //进气阀(球阀C) 0:球阀关闭 1:球阀打开 -1:球阀异常
    private Integer inletValve;
    //排气阀(球阀B) 0:球阀关闭 1:球阀打开 -1:球阀异常
    private Integer ventingValve;
    //取样阀(球阀A) 0:球阀关闭 1:球阀打开 -1:球阀异常
    private Integer sampleValve;
    //取样器状态
    private Integer samplerStatus;
    //plc id
    private Integer plcDevicesid;
    //dtunumber
    private String dtuSerialNumber;


}
