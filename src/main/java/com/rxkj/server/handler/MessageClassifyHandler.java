package com.rxkj.server.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rxkj.entity.po.DtuDevices;
import com.rxkj.entity.po.PlcDevices;
import com.rxkj.entity.po.Sampler;
import com.rxkj.mapper.DtuMap;
import com.rxkj.mapper.SamplerMapper;
import com.rxkj.message.*;
import com.rxkj.service.DtuService;
import com.rxkj.service.SamplerService;

import com.rxkj.service.impl.DtuServiceImpl;
import com.rxkj.service.impl.SamplerServiceImpl;

import com.rxkj.service.impl.DtuServiceImpl;
import com.rxkj.service.impl.PlcServiceImpl;
import com.rxkj.service.impl.SamplerServiceImpl;
import com.rxkj.util.AlexUtil;
import com.rxkj.util.SpringUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
public class MessageClassifyHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 接收来自MessageCodecSharable的MessageA
        MessageA messageA = (MessageA) msg;
        // 解析MessageA
        String magic = messageA.getMagic();
        int length = messageA.getLength();
        String checksum = messageA.getChecksum();
        String command = messageA.getCommand();
        String data = messageA.getData();
        // 从MessageA中读取command，根据command将Message分类成不同的消息
        switch (command) {
            case "01":
                // imei格式:00001-FF:FF:FF:FF:FF
                String imei = data.substring(0, 30);
                // iccId格式:格式说明：5位版本号-MAC1:MAC2:MAC3:MAC4:MAC5:MAC6
                String iccId = data.substring(30, 70);
                String dtuV = data.substring(70, 78);
                // int sampleKey = imei.substring(9,10);
                IdentityMessage identityMessage = new IdentityMessage(imei, iccId, dtuV);
                log.info("identityMessage  " + identityMessage.getMessageType());
                ctx.fireChannelRead(identityMessage);
                break;
            case "02":// 设备状态信息，作为心跳包周期性上传，如状态发生变化立即上传
                // todo:deviceId为预留字段，为以后的多台设备做准备
                String deviceId = data.substring(0, 2);
                String coilAddress = data.substring(2, 6);
                String outputCoil = data.substring(6, 8);

               /*a
                SseMessage message = new SseMessage();
                if((message= (SseMessage) DeviceList.getDeviceVector().get(Integer.parseInt(deviceId)))!=null){
                    if(coilAddress.equals("0803")){
                        message.setInletValve(Integer.parseInt(outputCoil));
                    } else if (coilAddress.equals("0802")) {
                        message.setVentingValve(Integer.parseInt(outputCoil));
                    } else if (coilAddress.equals("0804")) {
                        message.setSampleValve(Integer.parseInt(outputCoil));
                    }
                    DeviceList.add(Integer.parseInt(deviceId),message);
                }
               */

                StatusMessage statusMessage = new StatusMessage(deviceId, coilAddress, outputCoil);
                log.info("StatusMessage  " + statusMessage.getMessageType());
                ctx.fireChannelRead(statusMessage);
                break;
            case "03":
                /*
                ControlMessage controlMessage=new ControlMessage(deviceId,command);
                log.info("controlMessage "+controlMessage.getMessageType());
                ctx.fireChannelRead(controlMessage);
                */
                break;
            case "04":
                MaintenanceMessage maintenanceMessage = new MaintenanceMessage(magic, length, checksum, command, data);
                log.info("MaintenanceMessage  " + maintenanceMessage.getMessageType());
                ctx.fireChannelRead(maintenanceMessage);
                break;
            case "05":
                ModifyMessage modifyMessage = new ModifyMessage(magic, length, checksum, command, data);
                log.info("ModigyMessage  " + modifyMessage.getMessageType());
                ctx.fireChannelRead(modifyMessage);
                break;
            case "06":

                // todo:dtu上传设备状态的实现
            case "FE":
                ResponseMessage responseMessage = new ResponseMessage(magic, length, checksum, command, data);
                log.info("responseMessage  " + responseMessage.getMessageType());
                ctx.fireChannelRead(responseMessage);
                break;
            default:
                log.info("不存在相匹配的命令！");
        }
    }

    private String processMessage(String message) {
        // Implement your message processing logic here
        // For example, you can perform transformations or validations
        // on the message before forwarding it to the next handler
        return message.toUpperCase();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // Handle any exceptions that occur during message processing
        cause.printStackTrace();
        ctx.close();
    }
}

