package com.rxkj.service.impl;

import com.rxkj.message.SseMessage;
import com.rxkj.service.SseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Service
public class SseServiceImpl implements SseService {

    /**
     * sseMesageId的SseEmitter对象映射集
     *
     */
    private static Map<String,SseEmitter> sseEmitterMap = new ConcurrentHashMap<>();


        /**
         * 创建sse连接
         *
         * @param uuid
         * @return
         */
        @Override
        public SseEmitter connect(String uuid) {
            SseEmitter sseEmitter = new SseEmitter();
            //连接成功需要返回数据，否则会出现待处理状态
            try{
                sseEmitter.send(SseEmitter.event().comment("connect success"));
            }catch (IOException e){
                e.printStackTrace();
            }
            //连接断开
            sseEmitter.onCompletion(()->{
                sseEmitterMap.remove(uuid);
            });
            //连接超时
            sseEmitter.onTimeout(()->{
                sseEmitterMap.remove(uuid);
            });
            //连接报错
            sseEmitter.onError((throwable)-> {
                sseEmitterMap.remove(uuid);
            });

            sseEmitterMap.put(uuid,sseEmitter);

            return sseEmitter;
        }

        /**
         * 发送消息
         *
         * @param message
         */
        @Override
        public void sendMessage(SseMessage message) {
            message.setTotal(sseEmitterMap.size());
            sseEmitterMap.forEach((uuid,sseEmiter)->{
                try {
                    sseEmiter.send(message, MediaType.APPLICATION_JSON);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
}
