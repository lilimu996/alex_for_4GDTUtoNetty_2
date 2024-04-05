package com.rxkj.mapper;

import io.netty.channel.ChannelId;
import org.checkerframework.checker.units.qual.C;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 功能描述: 管理Dtu Map类
 *
 * @Date 2024/1/8
 */
public class DtuMap {
    /**
     * 管理一个全局map，保存dtu和他对应的channel id
     */
    private static final ConcurrentHashMap<String, ChannelId> DTU_MAP = new ConcurrentHashMap<>(128);
    private static final ConcurrentHashMap<ChannelId, String> CHANNEL_DTU_MAP = new ConcurrentHashMap<>(128);
    public static ConcurrentHashMap<String,ChannelId> getDtuMap() {
        return DTU_MAP;
    }
    /**
     *  获取指定DTU的Channel id
     */
    public static ChannelId getDtuByName(String dtuId){
        if(CollectionUtils.isEmpty(DTU_MAP)){
            return null;
        }
        return DTU_MAP.get(dtuId);
    }
    /**
     * 获取指定Channel id的DTU
     */
    public static String getDtuByChannelId(ChannelId channelId){
        if(CollectionUtils.isEmpty(CHANNEL_DTU_MAP)){
            return null;
        }
        return CHANNEL_DTU_MAP.get(channelId);
    }
    /**
     *  将id和对应的DTU添加到ConcurrentHashMap
     */
    public static void addDtu(String dtuId,ChannelId channelId){
        DTU_MAP.put(dtuId, channelId);
        CHANNEL_DTU_MAP.put(channelId,dtuId);
    }

    /**
     *  移除掉name对应的channel
     */
    public static boolean removeDtuByName(String dtuId,ChannelId channelId){
        if(DTU_MAP.containsKey(dtuId)){
           DTU_MAP.remove(dtuId);
           CHANNEL_DTU_MAP.remove(channelId);
            return true;
        }
        return false;
    }

}
