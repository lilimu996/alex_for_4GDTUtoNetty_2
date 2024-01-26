package com.rxkj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rxkj.mapper.DtuMap;
import com.rxkj.mapper.PlcMapper;
import com.rxkj.entity.PlcDevices;
import com.rxkj.enums.CommandEnum;
import com.rxkj.enums.CommandLengthEnum;
import com.rxkj.enums.KeywordEnum;
import com.rxkj.entity.ControlMessage;
import com.rxkj.message.MessageA;
import com.rxkj.service.PlcService;
import com.rxkj.server.session.SessionFactory;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PlcServiceImpl extends ServiceImpl<PlcMapper, PlcDevices> implements PlcService{
    @Override
    public void controller(ControlMessage controlMessage) {
        //前端传回plc站号，从数据库查询站号对应的iccid;
        /**
         *
         * #todo:前端传回设备号
         * 不同的DTU会有相同的plc站号，需要建立一个数据表，映射dtu,plc,和设备，前端传回设备号，再从数据库查找对应的
         * dtu序列号和他所对应的plc站号
         * */
        //String iccid="1100000000000000000000000000000000000011";
        //String iccid="0909090978303000000000000000110000000000";

        String iccid = DtuMap.getDtuByName("01");
        log.info("iccid:"+iccid);
        Channel channel= SessionFactory.getSession().getChannel(iccid);
        //客户端在线
        if(channel!=null){
            //回复客服端
            // log.info("CommandEnum "+CommandEnum.UPLOAD_COMMAND.value);
            String checksum="0000";
            //#todo:从前端获取信息
            //String data PLC站号和控制方式，都由前端传回
            String data= controlMessage.getDeviceId()+ controlMessage.getCommand();
            MessageA messageA=new MessageA(KeywordEnum.CHANNEL_HEAD.value, CommandLengthEnum.UPLOAD_STATUS_LENGTH.value,
                    checksum,CommandEnum.CONTROLLER_COMMAND.value, data);
            log.info("responsemsg  "+messageA);
            channel.writeAndFlush(messageA);
        }
        else{
            log.info("no Channel!");
        }
    }
}
