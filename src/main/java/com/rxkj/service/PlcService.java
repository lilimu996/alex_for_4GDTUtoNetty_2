package com.rxkj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rxkj.entity.ControlMessage;
import com.rxkj.entity.PlcDevices;

public interface PlcService extends IService<PlcDevices> {
    /**
     * 发送控制信息给dtu
     * */
     void controller(ControlMessage controlMessage);
}
