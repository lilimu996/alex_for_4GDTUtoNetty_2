package com.rxkj.service;

import com.rxkj.enums.SseTypesEnum;
import com.rxkj.message.SseMessage;
import io.swagger.models.auth.In;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseService {
    /**
     * 创建sse连接
     *
     * @param uuid
     * @return
     */
    SseEmitter connect(String uuid, Integer connectType);

    /**
     * 发送消息
     *
     * @param message
     * @param sseTypes
     */
    <T> void sendMessage(T message, SseTypesEnum sseTypes);

}
