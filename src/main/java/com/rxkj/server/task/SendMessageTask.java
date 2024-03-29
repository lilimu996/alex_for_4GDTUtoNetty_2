package com.rxkj.server.task;

import com.rxkj.mapper.DeviceList;
import com.rxkj.message.SseMessage;
import com.rxkj.service.SseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

@Slf4j
@Configuration
public class SendMessageTask {
    @Autowired
    private SseService sseService;

    /**
     * 定时执行 秒 分 时 日 月 周
     */
    @Scheduled(cron = "*/1 * * * * *")// 间隔1S
    public void sendMessageTask() {
        SseMessage message = new SseMessage();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        message.setData(LocalDateTime.now().format(format));
        message.setDtuStatus(0);
        message.setPlcId(01);
        message.setInletValve(0);
        message.setSampleValve(0);
        message.setVentingValve(0);
        // message.setDtuStatus(1);
        message.setSamplerStatus("00");
        // log.info("SendMessageTask:"+DeviceList.getDeviceVector().size());
        if (DeviceList.getDeviceVector().size() != 0) {
            /**
             *遍历设备Sse消息队列，并把队列中的每个消息推送至前端
             */


            DeviceList.getDeviceVector().forEach(new Consumer<SseMessage>() {
                /**
                 * Performs this operation on the given argument.
                 *
                 * @param message the input argument
                 */
                @Override
                public void accept(SseMessage message) {
                    sseService.sendMessage(message);
                }
            });
        } else {
            sseService.sendMessage(message);
        }
    }


}
