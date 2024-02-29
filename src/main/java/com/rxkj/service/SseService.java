package com.rxkj.service;

import com.rxkj.message.SseMessage;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseService {
    /**
     * 创建sse连接
     *
     * @param uuid
     * @return
     */
    SseEmitter connect(String uuid);

    /**
     * 发送消息
     *
     * @param message
     */
    void sendMessage(SseMessage message);
}
