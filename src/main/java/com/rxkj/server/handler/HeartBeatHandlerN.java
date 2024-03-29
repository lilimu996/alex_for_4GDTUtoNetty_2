package com.rxkj.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeartBeatHandlerN extends ChannelInboundHandlerAdapter {


    private static final ByteBuf HEARTBEAT_PACKET = Unpooled.copiedBuffer("Heartbeat", CharsetUtil.UTF_8);


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {

                // Send a heartbeat packet to keep the connection alive
                // MessageA messageA=new MessageA(KeywordEnum.CHANNEL_HEAD.value, CommandLengthEnum.RESPONSE_COMMAND.value,
                //      checksum, CommandEnum.RESPONSE_COMMAND.value,data);
                // ctx.writeAndFlush();
                // 5s读空闲触发心跳事件

                log.info("beat+++++++++");
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // Handle exceptions
        cause.printStackTrace();
        ctx.close();
    }

}
