package com.rxkj.service.impl;

import com.rxkj.common.R;
import com.rxkj.enums.SseTypesEnum;
import com.rxkj.message.SseMessage;
import com.rxkj.service.SseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class SseServiceImpl implements SseService {

    @Resource
    private RedisTemplate redisTemplate;
    /**
     * sseMesageId的SseEmitter对象映射集
     */
    private static Map<String, SseEmitter> sseEmitterMap = new ConcurrentHashMap<>();


    /**
     * 创建sse连接
     *
     * @param uuid
     * @return
     */
    @Override
    public SseEmitter connect(String uuid, SseTypesEnum connectType) {
        SseEmitter sseEmitter = new SseEmitter(-1L);
        // 连接成功需要返回数据，否则会出现待处理状态
        try {
            sseEmitter.send(SseEmitter.event().data(R.success("connect success!!"), MediaType.APPLICATION_JSON));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 连接断开
        sseEmitter.onCompletion(() -> {
            sseEmitterMap.remove(uuid);
            redisTemplate.opsForValue().getOperations().delete(uuid);
        });
        // 连接超时
        sseEmitter.onTimeout(() -> {
            sseEmitterMap.remove(uuid);
            redisTemplate.opsForValue().getOperations().delete(uuid);
        });
        // 连接报错
        sseEmitter.onError((throwable) -> {
            sseEmitterMap.remove(uuid);
            redisTemplate.opsForValue().getOperations().delete(uuid);
        });
        //todo:将uuid和connectType缓存
        sseEmitterMap.put(uuid, sseEmitter);
        redisTemplate.opsForValue().set(uuid,connectType);


        return sseEmitter;
    }

    /**
     * 发送消息
     *
     * @param message
     * @param sseTypes
     */
    @Override
<<<<<<< HEAD
<<<<<<< HEAD
    public void sendMessage(SseMessage message) {
=======
    public void sendMessage(SseMessage message, SseTypesEnum sseTypes) {
>>>>>>> b65a5be (根据不同的请求类型推送sseMessage)
        message.setTotal(sseEmitterMap.size());
        R<SseMessage> r = new R<>();
        //todo:根据传入的sseTypes向不同的连接发送消息
        //从Redis查询,只关注特定的sseTypes
        sseEmitterMap.forEach((uuid, sseEmiter) -> {
            try {
                r.setCode(1);
                r.setData(message);
<<<<<<< HEAD
=======
    public <T> void sendMessage(T message, SseTypesEnum sseTypes) {
        R<T> r = new R<>();
        r.setCode(1);
        //todo:根据传入的sseTypes向不同的连接发送消息
        //从Redis查询,只关注特定的sseTypes
        sseEmitterMap.forEach((uuid, sseEmiter) -> {
            try {
                //消息只推送给特定的连接
                if(redisTemplate.opsForValue().get(uuid).equals(SseTypesEnum.PART_STATUS_CONNECT)){
                    ((SseMessage) message).setTotal(sseEmitterMap.size());
                    r.setData(message);
=======
                //消息只推送给特定的连接
                if(redisTemplate.opsForValue().get(uuid).equals(SseTypesEnum.PART_STATUS_CONNECT)){
>>>>>>> b65a5be (根据不同的请求类型推送sseMessage)
                    sseEmiter.send(r, MediaType.APPLICATION_JSON);
                }
                if (redisTemplate.opsForValue().get(uuid).equals(SseTypesEnum.SAMPLE_CONNECT)){
                    sseEmiter.send(r, MediaType.APPLICATION_JSON);
                }

<<<<<<< HEAD
>>>>>>> 1329d0f (批量采样的SSE推送)
=======
>>>>>>> b65a5be (根据不同的请求类型推送sseMessage)
                /*
                 sseEmitter.send(r, MediaType.APPLICATION_JSON);
                 传递自定义类型
                 SseEmitter.SseEventBuilder event = SseEmitter.event().id(String.valueOf(1)).name("message").data(message);
                 sseEmitter.send(r, MediaType.TEXT_EVENT_STREAM);
                */
                //sseEmiter.send(r, MediaType.APPLICATION_JSON);
            } catch (Exception ex) {
                sseEmiter.completeWithError(ex);
            }
        });
    }
}
