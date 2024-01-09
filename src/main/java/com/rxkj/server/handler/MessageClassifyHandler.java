package com.rxkj.server.handler;

import com.rxkj.mapper.DtuMap;
import com.rxkj.message.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import static com.rxkj.mapper.DtuMap.*;

@Slf4j
public class MessageClassifyHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // Process the incoming message here
        //String processedMessage = processMessage((String) msg);
        //接收来自MessageCodecSharable的MessageA
        MessageA messageA=(MessageA) msg;
        //解析MessageA
        String magic=messageA.getMagic();
        int length=messageA.getLength();
        String checksum=messageA.getChecksum();
        String command=messageA.getCommand();
        String data=messageA.getData();
        //从MessageA中读取command，根据command将Message分类成不同的消息
        switch (command){
            case "01":
                String imei=data.substring(0,30);
                String iccid=data.substring(30,70);
                String dtuv=data.substring(70,78);
                //判断dtu设备号是否在DtuMap中，如果不存在则将dtu设备号和plcid存入DtuMap
                if(getDtuByName("01") == null)
                {
                    addDtu("01",data);
                }
                IdentityMessage identityMessage =new IdentityMessage(imei,iccid,dtuv);
                log.info("identityMessage  "+identityMessage.getMessageType());
                ctx.fireChannelRead(identityMessage);
                break;
            case "02"://设备状态信息，作为心跳包周期性上传，如状态发生变化立即上传
                //#todo:deviceId为预留字段，为以后的多台设备做准备
                String deviceId=data.substring(0,2);
                String auxiliaryCoils=data.substring(2,4);
                String outputCoil=data.substring(4,6);
                StatusMessage statusMessage=new StatusMessage(deviceId,auxiliaryCoils,outputCoil);
                log.info("StatusMessage  "+statusMessage.getMessageType());
                ctx.fireChannelRead(statusMessage);
                break;
            case "03":
                /*ControlMessage controlMessage=new ControlMessage(deviceId,command);
                log.info("controlMessage "+controlMessage.getMessageType());
                ctx.fireChannelRead(controlMessage);*/
                break;
            case "04":
                MaintenanceMessage maintenanceMessage =new MaintenanceMessage(magic,length,checksum,command,data);
                log.info("MaintenanceMessage  "+maintenanceMessage.getMessageType());
                ctx.fireChannelRead(maintenanceMessage);
                break;
            case "05":
                ModifyMessage modifyMessage=new ModifyMessage(magic,length,checksum,command,data);
                log.info("ModigyMessage  "+modifyMessage.getMessageType());
                ctx.fireChannelRead(modifyMessage);
                break;
            case "FE":
                ResponseMessage responseMessage=new ResponseMessage(magic,length,checksum,command,data);
                log.info("responseMessage  "+responseMessage.getMessageType());
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

