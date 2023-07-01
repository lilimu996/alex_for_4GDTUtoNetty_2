package com.rxkj.handler;

import com.rxkj.message.StatusMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlcStatusHandler extends SimpleChannelInboundHandler<StatusMessage> {
    /**
     * 状态信息作为心跳包周期性上传，如状态发生变化立即上传。
     * @param channelHandlerContext
     * @param statusMessage
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, StatusMessage statusMessage) throws Exception {
        //#todo:plc状态信息，转发给前端
        log.info("statusMessage "+statusMessage);
    }
}
