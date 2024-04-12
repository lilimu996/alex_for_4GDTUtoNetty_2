package com.rxkj.service;

import com.rxkj.enums.SseTypesEnum;
import com.rxkj.message.SseMessage;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseService {
    /**
     * 创建sse连接
     *
     * @param uuid
     * @return
     */
    SseEmitter connect(String uuid, SseTypesEnum connectType);

    /**
     * 发送消息
     *
     * @param message
     * @param sseTypes
     */
<<<<<<< HEAD
<<<<<<< HEAD
    void sendMessage(SseMessage message);
=======
    <T> void sendMessage(T message, SseTypesEnum sseTypes);
>>>>>>> 1329d0f (批量采样的SSE推送)
=======
    void sendMessage(SseMessage message, SseTypesEnum sseTypes);
>>>>>>> b65a5be (根据不同的请求类型推送sseMessage)
}
