package com.rxkj.entity.po;

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
    private Integer samplerStatus;



}
