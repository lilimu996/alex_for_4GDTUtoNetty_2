package com.rxkj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rxkj.entity.ControlMessage;
import com.rxkj.entity.bo.MeiFenUser;
import com.rxkj.entity.bo.SamplerGroup;
import com.rxkj.entity.po.PlcDevices;

import java.util.List;
import java.util.concurrent.Future;

public interface PlcService extends IService<PlcDevices> {
    /**
     * 发送控制信息给dtu
     */
    void controller(ControlMessage controlMessage, MeiFenUser meiFenUser);

    /**
     * 批量采样接口
     *
     * @param groupList
     * @param meiFenUser
     * @return
     */
    List<Future<String>> batchSample(List<SamplerGroup> groupList, MeiFenUser meiFenUser);
}
