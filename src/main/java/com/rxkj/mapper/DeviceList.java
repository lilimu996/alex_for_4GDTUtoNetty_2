package com.rxkj.mapper;

import com.rxkj.message.SseMessage;

import java.util.Vector;

/**
 * 功能描述: 管理设备消息队列类
 *
 * @Date 2023/6/7
 */
public class DeviceList {
    private static final Vector DEVICE_VECTOR = new Vector(32, 16);

    public static Vector getDeviceVector() {
        return DEVICE_VECTOR;
    }

    /**
     * 向指定位置添加设备
     */

    public static boolean add(int DeviceId,SseMessage message){
        DEVICE_VECTOR.add(DeviceId,message);

        return true;
    }

    /**
     * 获取指定索引的设备
     */
    public static SseMessage get(Integer id) {
        return new SseMessage();
    }
}
