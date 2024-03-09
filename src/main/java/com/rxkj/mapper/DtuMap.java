package com.rxkj.mapper;

import org.springframework.util.CollectionUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 功能描述: 管理Dtu Map类
 *
 * @Date 2024/1/8
 */
public class DtuMap {
    /**
     * 管理一个全局map，保存连接进服务端的plc和他对应的dtu
     */
    private static final ConcurrentHashMap<String, String> DTU_MAP = new ConcurrentHashMap<>(128);

    public static ConcurrentHashMap<String, String> getDtuMap() {
        return DTU_MAP;
    }
    /**
     *  获取指定id的DTU
     */
    public static String getDtuByName(String PlcId){
        if(CollectionUtils.isEmpty(DTU_MAP)){
            return null;
        }
        return DTU_MAP.get(PlcId);
    }
    /**
     *  将id和对应的DTU添加到ConcurrentHashMap
     */
    public static void addDtu(String PlcId,String DtuId){
        DTU_MAP.put(PlcId,DtuId);
    }

    /**
     *  移除掉name对应的channel
     */
    public static boolean removeDtuByName(String PlcId){
        if(DTU_MAP.containsKey(PlcId)){
           DTU_MAP.remove(PlcId);
            return true;
        }
        return false;
    }

}
