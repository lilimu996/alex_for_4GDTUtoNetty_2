package com.rxkj.server.task;

import com.rxkj.message.SseMessage;
import com.rxkj.service.SseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class SendMessageTask {
    @Autowired
    private SseService sseService;
    /**
     * 定时执行 秒 分 时 日 月 周
     */
    @Scheduled(cron = "*/1 * * * * *")//间隔1S
    public void sendMessageTask(){
        SseMessage message = new SseMessage();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        message.setData(LocalDateTime.now().format(format));
        message.setDtuStatus(1);
        message.setPlcId(01);
        message.setInletValve(0);
        message.setSampleValve(1);
        message.setVentingValve(0);
        message.setDtuStatus(1);
        message.setSamplerStatus("01");
        sseService.sendMessage(message);
    }


}
