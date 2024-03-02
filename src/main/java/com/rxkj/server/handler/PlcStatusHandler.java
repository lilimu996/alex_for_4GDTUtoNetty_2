package com.rxkj.server.handler;

import com.rxkj.enums.CommandEnum;
import com.rxkj.enums.CommandLengthEnum;
import com.rxkj.enums.KeywordEnum;
import com.rxkj.enums.TimeoutEnum;
import com.rxkj.mapper.DeviceList;
import com.rxkj.message.MessageA;
import com.rxkj.message.SseMessage;
import com.rxkj.message.StatusMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class PlcStatusHandler extends SimpleChannelInboundHandler<StatusMessage> {
    /**
     * 状态信息作为心跳包周期性上传，如状态发生变化立即上传。
     *
     * @param statusMessage
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, StatusMessage statusMessage) throws Exception {
        // todo:plc状态信息，转发给前端
        // todo:服务器回复指令0xFE
        log.info("statusMessage " + statusMessage);
        int deviceId = Integer.parseInt(statusMessage.getDeviceId());
        SseMessage message = DeviceList.getDeviceVector().get(deviceId);
        // if ((message = (SseMessage) DeviceList.getDeviceVector().get(Integer.parseInt(statusMessage.getDeviceId()))) != null) {
        String coilAddress = statusMessage.getAuxiliaryCoils();
        String outputCoil = statusMessage.getOutputCoil();
        log.info("coilAddress:" + coilAddress);
        message.setDtuStatus(1);
        if (coilAddress.equals("040C")) {
            message.setInletValve(1);
            log.info("update ssemessage!!");
        } else if (coilAddress.equals("040A")) {
            message.setVentingValve(1);
            log.info("update ssemessage!!");
        } else if (coilAddress.equals("0406")) {
            message.setSampleValve(1);
            log.info("update ssemessage!!");
        } else if (coilAddress.equals("040D")) {
            message.setInletValve(0);
            log.info("update ssemessage!!");
        } else if (coilAddress.equals("040B")) {
            message.setVentingValve(0);
            log.info("update ssemessage!!");
        } else if (coilAddress.equals("0407")) {
            message.setSampleValve(0);
            log.info("update ssemessage!!");
        }
        DeviceList.getDeviceVector().set(deviceId, message);
        log.info("StatusMessage processing:" + DeviceList.getDeviceVector().size());
        // }
        // 回复客服端
        // log.info("CommandEnum "+CommandEnum.UPLOAD_COMMAND.value);
        // todo:更新checksum
        String checksum = "0000";
        String data = CommandEnum.UPLOAD_STATUS_COMMAND.value + TimeoutEnum.COMMON_TIMEOUT.value;
        MessageA messageA = new MessageA(KeywordEnum.CHANNEL_HEAD.value, CommandLengthEnum.RESPONSE_COMMAND.value, checksum, CommandEnum.RESPONSE_COMMAND.value, data);
        log.info("responsemsg  " + messageA);
        ctx.writeAndFlush(messageA);
    }
}
