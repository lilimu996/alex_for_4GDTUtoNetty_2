package com.rxkj.mapper;

import com.rxkj.message.SseMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.Vector;

/**
 * 功能描述: 管理设备消息队列类
 *
 * @Date 2023/6/7
 */
@Slf4j
public class DeviceList {
    private static final Vector<SseMessage> DEVICE_VECTOR = new Vector<SseMessage>(32, 16);

    public static void initDEVICE_VECTOR() {
        for (int i = 0; i < 32; i++) {
            SseMessage sseMessage = new SseMessage();
            sseMessage.setDtuStatus(-1);
            sseMessage.setInletValve(0);
            sseMessage.setSampleValve(0);
            sseMessage.setVentingValve(0);
            sseMessage.setData("0");
            sseMessage.setSamplerStatus("0");
            sseMessage.setDtuSerialNumber("0");
            sseMessage.setPlcId(i);
            DEVICE_VECTOR.add(sseMessage);
        }
        log.info("DEVICE_VECTOR 初始化完成");
    }

    public static Vector<SseMessage> getDeviceVector() {
        return DEVICE_VECTOR;
    }

    /**
     * 向指定位置添加设备
     */

    public static boolean add(int DeviceId, SseMessage message) {
        DEVICE_VECTOR.add(DeviceId, message);

        return true;
    }

    /**
     * 获取指定索引的设备
     */
    public static SseMessage get(Integer id) {
        return new SseMessage();
    }
}
