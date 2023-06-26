package com.rxkj.protocol;

import com.rxkj.check.DataCheckFormat;
import com.rxkj.message.MessageOld;
import com.rxkj.message.MessageA;
import com.rxkj.util.AlexUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.List;

/**
 * #################################################################################################
 * ##########                  【自定义】消息 编解码 类   【 支持@Sharable 】                   ########
 * ##########   父类 MessageToMessageCodec 认为是完整的信息 【所以必须保证上一个处理器是 帧解码器】 ########
 * #################################################################################################
 * 相当于两个handler合二为一，【既能入站 也能做出站处理】
 *  <b>魔数     </b>，用来在第一时间判定是否是无效数据包
 *  <b>长度   </b>，数据包的长度
 *  <b>校验</b>，检验码
 *  <b>指令  </b>，0x00：前进；0x01：后退；0x02：暂停；0x03：初始化
 *  <b>数据  </b>，
 */
// 写这个类 肯定的认为 上一个处理器 是 帧解码器，所以不用考虑半包黏包问题，直接解码拿数据
@Slf4j
@ChannelHandler.Sharable
public class MessageCodecSharable extends MessageToMessageCodec<ByteBuf, MessageA> { // 注意这里Message是自定义类
    private DataCheckFormat dataCheckFormat=new DataCheckFormat();

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageA msg, List<Object> outlist) throws Exception {
        //开始encode
        log.info("开始encode-------------------");
        ByteBuf out = ctx.alloc().buffer();
        //写入魔数
        out.writeBytes(AlexUtil.hexStringToByteArray(msg.getMagic()));

        //写入长度
        log.info("hexst "+Integer.toString(msg.getLength()));
        out.writeBytes(AlexUtil.hexStringToByteArray3(Integer.toHexString(msg.getLength())));
        log.info("updata to there  ");
        //# todo 更新校验码
        out.writeBytes(AlexUtil.hexStringToByteArray(msg.getChecksum()));
        //# todo 指令
        out.writeBytes(AlexUtil.hexStringToByteArray(msg.getCommand()));

        //# todo 数据
        out.writeBytes(AlexUtil.hexStringToByteArray(msg.getData()));
        System.out.println("经过encode-------------");
        outlist.add(out);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        // 获取魔数
        byte[] magic=new byte[2];
        msg.readBytes(magic);
        String magichex=AlexUtil.bytesToHexString(magic);
        //获取长度
        byte[] length=new byte[1];
        msg.readBytes(length);
        //log.info("decode length  "+AlexUtil.bytesToHexString(length));
        int lengthDec=Integer.parseInt(AlexUtil.bytesToHexString(length),16);
        log.info("lenthDec  "+lengthDec);
        //获取校验
        byte[] checksum=new byte[2];
        msg.readBytes(checksum);
        String checksumhex=AlexUtil.bytesToHexString(checksum);
        //获取指令
        byte[] command=new byte[1];
        msg.readBytes(command);
        String commandhex=AlexUtil.bytesToHexString(command);
        //获取数据
        byte[] datas=new byte[lengthDec-6];
        msg.readBytes(datas,0,lengthDec-6);
        String datashex=AlexUtil.bytesToHexString(datas);
        //封装消息
        MessageA message=new MessageA(magichex,lengthDec,checksumhex,commandhex,datashex);
        //
        //序列化
        //校验包头和校验码
        if(dataCheckFormat.checkFormat(message)){
            if(dataCheckFormat.checkSum(message)){
                log.info("CheckRight "+message);
                out.add(message);
            }
        }
        /*// 打印获得的信息正文
        System.out.println("===========魔数===========");
        System.out.println(AlexUtil.bytesToHexString(magic));
        System.out.println("===========长度===========");
        System.out.println(lengthDec);
        System.out.println("===========校验码===========");
        System.out.println(AlexUtil.bytesToHexString(checksum));
        System.out.println("===========指令类型===========");
        System.out.println(AlexUtil.bytesToHexString(command));
        System.out.println("===========数据==========");
        System.out.println(AlexUtil.bytesToHexString(datas));*/

    }

    public static void main(String[] args) throws IOException {
        /*int decimal = 10;
        String hexString = Integer.toHexString(decimal);
        System.out.println(hexString); // Output: A
        int length=10;
        log.info("hexStringl "+AlexUtil.hexStringToByteArray(Integer.toHexString(length)));*/
//        int length=Integer.parseInt("8",16);
//        System.out.println(length);
//        String hexString=Integer.toString(10);
//
//        log.info("num   "+hexString);
        //log.info("num "+ Integer.toString(56));

        String hex=Integer.toHexString(45);
        log.info("string "+hex);
        AlexUtil.hexStringToByteArray3(hex);






    }

}
