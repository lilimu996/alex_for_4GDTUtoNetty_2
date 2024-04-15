package com.rxkj.server.task;

import com.rxkj.common.RedisKeys;
import com.rxkj.entity.vo.SamplerVo;
import com.rxkj.enums.ExecutionStatus;
import com.rxkj.enums.SseTypesEnum;
import com.rxkj.mapper.DeviceList;
import com.rxkj.message.SseMessage;
import com.rxkj.service.SseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@Slf4j
@Configuration
public class SendMessageTask {
    @Autowired
    private SseService sseService;
    @Resource
    private RedisTemplate redisTemplate;
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
             * 发送Sampler部件状态
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
                    sseService.sendMessage(message, SseTypesEnum.PART_STATUS_CONNECT);
                }
            });
        } else {
            sseService.sendMessage(message, SseTypesEnum.PART_STATUS_CONNECT);
        }
    }

    /**
     * 发送sampler状态
     */
    @Scheduled(cron = "*/2 * * * * *")// 间隔2S
    public void sendSamplerMessage(){
        checkAndSendUpdates();
    }
    public void checkAndSendUpdates() {

        List<SamplerVo> updatedSamplerList = findAllByStatusIn();
        for (SamplerVo sampler : updatedSamplerList) {
            //更新设备状态(暂定)
            // 根据设备通信更新状态的逻辑(在Handler中执行)
            // 发送SSE通知
            sseService.sendMessageWithSampler(sampler, SseTypesEnum.SAMPLE_CONNECT);
//                SseEmitter.Event event = buildSseEvent(device);
//                sseEmitter.send(event);
        }
    }
    public List<SamplerVo> findAllByStatusIn(){
        List<SamplerVo> samplerList = new ArrayList<>();
        Set<String> keys = redisTemplate.keys(RedisKeys.BASE_BATCHSAMPLE+"*");
        for(String key:keys){
            SamplerVo sampler = (SamplerVo) redisTemplate.opsForValue().get(key);
            samplerList.add(sampler);
        }
        return samplerList;
    }

}
