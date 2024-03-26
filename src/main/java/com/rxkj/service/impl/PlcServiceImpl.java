package com.rxkj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rxkj.entity.ControlMessage;
import com.rxkj.entity.bo.MeiFenUser;
import com.rxkj.entity.po.Sampler;
import com.rxkj.mapper.ChannelMap;
import com.rxkj.mapper.DeviceList;
import com.rxkj.mapper.DtuMap;
import com.rxkj.mapper.PlcMapper;
import com.rxkj.entity.po.PlcDevices;
import com.rxkj.enums.CommandEnum;
import com.rxkj.enums.CommandLengthEnum;
import com.rxkj.enums.KeywordEnum;
import com.rxkj.message.MessageA;
import com.rxkj.message.SseMessage;
import com.rxkj.server.session.SessionFactory;
import com.rxkj.service.SamplerService;
import com.rxkj.service.PlcService;
import com.rxkj.util.AlexUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Maps;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Objects;

@Slf4j
@Service
public class PlcServiceImpl extends ServiceImpl<PlcMapper, PlcDevices> implements PlcService {

    @Resource
    OperationServiceImpl operationService;
    @Autowired
    private SamplerService samplerService;

    @Override
    public void controller(ControlMessage controlMessage, MeiFenUser meiFenUser) {
        // 前端传回plc站号，从数据库查询站号对应的iccId;

        /**
         *
         * todo:前端传回设备号
         * 不同的DTU会有相同的plc站号，需要建立一个数据表，映射dtu,plc,和设备，前端传回设备号，再从数据库查找对应的
         * dtu序列号和他所对应的plc站号
         **/

        // String iccId="1100000000000000000000000000000000000011";
        //String iccId="0909090978303000000000000000110000000000";
        //todo:从dtumap获得serialNumber,使用sampler id获得plc id
        //String iccId = DtuMap.getDtuByName("01");
        Sampler sampler = new Sampler();
        Integer samplerId = Integer.parseInt(controlMessage.getDeviceId(),10);
        QueryWrapper<Sampler> qw = new QueryWrapper<>();
        qw.eq("sampler_id",samplerId);
        sampler = samplerService.getOne(qw);
        log.info("sampler_id:"+sampler.getSamplerId()+",plc_station_on:"+sampler.getPlcStationNo());
        //Sampler finalSampler = sampler;
        if(CollectionUtils.isEmpty(DtuMap.getDtuMap())){
            log.info("no channel!");
            return;
        }
        //log.info("DtuSerialNumber:",finalSampler.getDtuSerialNumber());
       // ChannelId channelId = Maps.filterKeys(DtuMap.getDtuMap(), v->v.equals(finalSampler.getDtuSerialNumber())).entrySet().iterator().next().getKey();
        //log.info("channelId:",channelId);
        ChannelId channelId = DtuMap.getDtuByName(sampler.getDtuSerialNumber());
        Channel channel = null;
        channel = ChannelMap.getChannelByName(channelId);
        //log.info("iccId:" + iccId);
//        if (Objects.isNull(iccId)) {
//            log.info("dtu连接数量:0");
//        } else {
//            channel = SessionFactory.getSession().getChannel(iccId);
//        }
//        log.info("session:" + SessionFactory.getSession().toString());
        // 客户端在线
        if (!Objects.isNull(channel)) {
            // 回复客服端
            // log.info("CommandEnum "+CommandEnum.UPLOAD_COMMAND.value);
            String checksum = "0000";
            // todo:从前端获取信息
            // String data PLC站号和控制方式，都由前端传回

            // 无奈之举
            if (Objects.isNull(controlMessage.getDeviceId())) {
                controlMessage.setDeviceId("01");
            }
            //String data = controlMessage.getDeviceId() + controlMessage.getCommand();
            String data = String.format("%02d",sampler.getPlcStationNo()) + controlMessage.getCommand();

//            String data = controlMessage.getDeviceId() + controlMessage.getCommand();
            // 这个地方好像没用
            if (controlMessage.getCommand().equals("03")) {
                SseMessage sseMessage = new SseMessage();
                sseMessage = DeviceList.get(01);
                sseMessage.setSampleValve(0);
                sseMessage.setInletValve(0);
                sseMessage.setSampleValve(0);
            }

            MessageA messageA = new MessageA(KeywordEnum.CHANNEL_HEAD.value, CommandLengthEnum.UPLOAD_STATUS_LENGTH.value, checksum, CommandEnum.CONTROLLER_COMMAND.value, data);
            log.info("responseMsg  " + messageA);
            channel.writeAndFlush(messageA);
        } else {
            log.info("no Channel!");
        }
        // 异步写日志 后续可引入消息中间件
//        int deviceId = Integer.parseInt(controlMessage.getDeviceId());
//        String userNumbers = meiFenUser.getUser().getUserNumbers();
//        int command = Integer.parseInt(controlMessage.getCommand());
//        operationService.saveOperation(deviceId, userNumbers, command);
    }
}
