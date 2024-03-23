package com.rxkj.server.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rxkj.entity.po.DtuDevices;
import com.rxkj.entity.po.Sampler;
import com.rxkj.mapper.DeviceList;
import com.rxkj.mapper.DtuMap;
import com.rxkj.message.*;
import com.rxkj.service.DtuService;
import com.rxkj.service.PlcService;
import com.rxkj.service.SamplerService;
import com.rxkj.util.AlexUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.rxkj.mapper.DtuMap.*;

@Slf4j
@Component
public class MessageClassifyHandler extends ChannelInboundHandlerAdapter {
    @Autowired
    private DtuService dtuService;
    @Autowired
    private SamplerService samplerService;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // Process the incoming message here
        // String processedMessage = processMessage((String) msg);
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
                //imei格式:00001-FF:FF:FF:FF:FF
                String imei = data.substring(0, 30);
                //iccId格式:格式说明：5位版本号-MAC1:MAC2:MAC3:MAC4:MAC5:MAC6
                String iccId = data.substring(30, 70);
                String dtuV = data.substring(70, 78);
                //int sampleKey = imei.substring(9,10);
                int sampleKey = Integer.parseInt(imei.substring(0,5), 16);

                /**
                 * dtu连接后上报身份信息，以此身份信息和plc站号，煤粉取样器编号建立映射关系
                 * [iccId:plc:煤粉取样器num]
                 * 一个mac对应站号为01-08的plc,一台plc对应一台煤粉取样器，煤粉取样器号码唯一。
                 * */
                //判断dtu序列号是否存在于数据库，不存在的话存入数据库
                String serialNumber = data;
                QueryWrapper<DtuDevices> qw = new QueryWrapper<>();
                if(qw.eq("serial_number",serialNumber) != null){//dtu在数据库
                    log.info("dtu上线:"+serialNumber);
                }else{
                    DtuDevices devices=new DtuDevices();
                    devices.setUptime(new Date());
                    devices.setSerialNumber(serialNumber);
                    if(dtuService.save(devices)){
                        log.info("保存dtu:"+serialNumber);
                    }
                    //dtu第一次上线，使用sampleKey计算dtu对应的sample id并存入数据库
                    Map<Integer,Integer> sampleMap = new HashMap<Integer,Integer>();
                    sampleMap = AlexUtil.sampleMap(sampleKey);
                    Sampler sampler = new Sampler();
                    sampler.setDtuSerialNumber(serialNumber);
                    for (Integer i : sampleMap.keySet()) {
                       // System.out.println("key: " + i + " value: " + sampleMap.get(i));
                        sampler.setPlcDevicesid(i);
                        sampler.setIdsampler(sampleMap.get(i));
                        samplerService.save(sampler);
                    }
                }

                // 判断dtu设备号是否在DtuMap中，如果不存在则将dtu设备号和plcId存入DtuMap
                //dtumap保存dtu serialNumber和channel id,plc id和sampler id对应在0x06建立
                DtuMap.addDtu(ctx.channel().id(),serialNumber);
                /*if (getDtuByName("01") == null) {
                    addDtu("01", iccId);
                }*/
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
                /*ControlMessage controlMessage=new ControlMessage(deviceId,command);
                log.info("controlMessage "+controlMessage.getMessageType());
                ctx.fireChannelRead(controlMessage);*/
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
                //todo:dtu上传设备状态的实现
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

