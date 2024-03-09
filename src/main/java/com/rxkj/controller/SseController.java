package com.rxkj.controller;

import com.rxkj.message.SseMessage;
import com.rxkj.service.SseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("sse")
public class SseController {

    @Autowired
    private SseService sseService;

    @RequestMapping("/")
    public String index() {
        return "sse";
    }

    /**
     * 创建SSE连接
     *
     * @return
     */
    @RequestMapping(path = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter sse() {
        String uuid = UUID.randomUUID().toString();
        log.info("新用户连接:{}", uuid);
        return sseService.connect(uuid);
    }

    /**
     * 广播消息
     *
     * @param message
     */
    @RequestMapping("/sendMessage")
    @ResponseBody
    public void sendMessage(@RequestBody SseMessage message) {
        sseService.sendMessage(message);
    }

}
