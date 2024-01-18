package com.rxkj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rxkj.entity.PlcDevices;
import com.rxkj.entity.ControlMessage;

public interface PlcService extends IService<PlcDevices> {
    /**
     * 发送控制信息给dtu
     * */
    public void controller(ControlMessage controlMessage);
}
