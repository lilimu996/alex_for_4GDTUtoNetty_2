package com.rxkj.server.handler;

import com.rxkj.mapper.DeviceList;
import com.rxkj.message.StatusMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import com.rxkj.message.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StatusHandler extends SimpleChannelInboundHandler<StatusMessage> {
    /**
     * @param channelHandlerContext
     * @param statusMessage
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, StatusMessage statusMessage) throws Exception {
        SseMessage message = new SseMessage();
        if((message= (SseMessage) DeviceList.getDeviceVector().get(Integer.parseInt(statusMessage.getDeviceId())))!=null){
            String coilAddress=statusMessage.getAuxiliaryCoils();
            String outputCoil=statusMessage.getOutputCoil();
            if(coilAddress.equals("040C")){
                message.setInletValve(1);
            } else if (coilAddress.equals("040A")) {
                message.setVentingValve(1);
            } else if (coilAddress.equals("0406")) {
                message.setSampleValve(1);
            }else if(coilAddress.equals("040D")){
                message.setInletValve(0);
            } else if (coilAddress.equals("040B")) {
                message.setVentingValve(0);
            }else if (coilAddress.equals("0407")) {
                message.setSampleValve(0);
            }

            DeviceList.add(Integer.parseInt(statusMessage.getDeviceId()),message);
            log.info("StatusMessage processing"+DeviceList.getDeviceVector().size());
        }
    }
}
