package com.rxkj.server.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rxkj.entity.po.DtuDevices;
import com.rxkj.entity.po.PlcDevices;
import com.rxkj.entity.po.Sampler;
import com.rxkj.enums.CommandEnum;
import com.rxkj.enums.CommandLengthEnum;
import com.rxkj.enums.KeywordEnum;
import com.rxkj.enums.TimeoutEnum;
import com.rxkj.mapper.DtuMap;
import com.rxkj.message.IdentityMessage;
import com.rxkj.message.MessageA;
import com.rxkj.server.session.SessionFactory;
import com.rxkj.service.DtuService;
import com.rxkj.service.PlcService;
import com.rxkj.service.SamplerService;
import com.rxkj.service.impl.DtuServiceImpl;
import com.rxkj.service.impl.PlcServiceImpl;
import com.rxkj.service.impl.SamplerServiceImpl;
import com.rxkj.util.AlexUtil;
import com.rxkj.util.SpringUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class DeviceIdentityHandler extends SimpleChannelInboundHandler<IdentityMessage> {
    private static DtuServiceImpl dtuService;
    private static SamplerServiceImpl samplerService;
    private static PlcServiceImpl plcService;
    static {
        dtuService = SpringUtils.getBean(DtuServiceImpl.class);
        samplerService = SpringUtils.getBean(SamplerServiceImpl.class);
        plcService = SpringUtils.getBean(PlcServiceImpl.class);
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, IdentityMessage msg) throws Exception {
        // log.info("身份信息  "+msg);
        // todo:将设备信息保存至数据库
        String imei = msg.getImei();
        int sampleKey = Integer.parseInt(imei.substring(0, 6), 16);
        //log.info("imeisub:" + imei.substring(0, 6));
        //log.info("sampleKey:" + sampleKey);
        /**
         * dtu连接后上报身份信息，以此身份信息和plc站号，煤粉取样器编号建立映射关系
         * [iccId:plc:煤粉取样器num]
         * 一个mac对应站号为01-08的plc,一台plc对应一台煤粉取样器，煤粉取样器号码唯一。
         * */
        // 判断dtu序列号是否存在于数据库，不存在的话存入数据库
        String serialNumber = msg.toString();
        DtuDevices serialNumberExists = dtuService.getOne(new QueryWrapper<DtuDevices>().eq("serial_number", serialNumber));
        if (!Objects.isNull(serialNumberExists)) {// dtu在数据库
            log.info("dtu上线:" + serialNumber);
        } else {
            DtuDevices devices = new DtuDevices();
            devices.setUptime(new Date());
            devices.setSerialNumber(serialNumber);
            if (dtuService.save(devices)) {
                log.info("保存dtu:" + serialNumber);
            }
            // dtu第一次上线，使用sampleKey计算dtu对应的sample id并存入数据库
            Map<Integer, Integer> sampleMap = new HashMap<Integer, Integer>();
            sampleMap = AlexUtil.sampleMap(sampleKey);
            Sampler sampler = new Sampler();
            PlcDevices plcDevices = new PlcDevices();
            sampler.setDtuSerialNumber(serialNumber);
            plcDevices.setDtuSerialNumber(serialNumber);
            for (Integer i : sampleMap.keySet()) {
                // System.out.println("key: " + i + " value: " + sampleMap.get(i));
                sampler.setPlcStationNo(i);
                sampler.setSamplerId(sampleMap.get(i));
                plcDevices.setPlcStationNo(i);
                plcService.save(plcDevices);
                samplerService.save(sampler);
            }
        }

        /** 判断dtu设备号是否在DtuMap中，如果不存在则将dtu设备号和plcId存入DtuMap
         * dtumap保存dtu serialNumber和channel id,plc id和sampler id映射由工具类计算*/
        //dtu上线,更新DtuMap中的channel
        DtuMap.addDtu(serialNumber,ctx.channel().id());
        // 绑定设备和channel,便于调用
        //SessionFactory.getSession().bind(ctx.channel(), msg.getIccId());
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
