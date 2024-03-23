package com.rxkj.server.handler;

import com.rxkj.enums.CommandEnum;
import com.rxkj.enums.CommandLengthEnum;
import com.rxkj.enums.KeywordEnum;
import com.rxkj.enums.TimeoutEnum;
import com.rxkj.message.IdentityMessage;
import com.rxkj.message.MessageA;
import com.rxkj.server.session.SessionFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeviceIdentityHandler extends SimpleChannelInboundHandler<IdentityMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, IdentityMessage msg) throws Exception {
        // log.info("身份信息  "+msg);
        // todo:将设备信息保存至数据库

        // 绑定设备和channel,便于调用
        SessionFactory.getSession().bind(ctx.channel(), msg.getIccId());
        // log.info("Iccid:"+msg.getIccid());
        // 回复客服端
        // log.info("CommandEnum "+CommandEnum.UPLOAD_COMMAND.value);
        //#todo:更新checksum
        String checksum = "0000";
        String data = CommandEnum.UPLOAD_IDENTITY_COMMAND.value + TimeoutEnum.COMMON_TIMEOUT.value;
        MessageA messageA = new MessageA(KeywordEnum.CHANNEL_HEAD.value, CommandLengthEnum.RESPONSE_COMMAND.value,
                checksum, CommandEnum.RESPONSE_COMMAND.value, data);
        log.info("responsemsg  " + messageA);
        ctx.writeAndFlush(messageA);

    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Handle any exceptions that occur during message processing
        cause.printStackTrace();
        ctx.close();
    }
}
