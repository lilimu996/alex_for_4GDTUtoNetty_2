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
                    sseEmiter.send(r, MediaType.APPLICATION_JSON);
                }
                if (redisTemplate.opsForValue().get(uuid).equals(SseTypesEnum.SAMPLE_CONNECT)){
                    sseEmiter.send(r, MediaType.APPLICATION_JSON);
                }
            } catch (Exception ex) {
                sseEmiter.completeWithError(ex);
            }
        });
    }
}
