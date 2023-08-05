package com.rxkj.check;

import com.rxkj.message.MessageA;
import com.rxkj.util.ICheckFormat;

public class DataCheckFormat implements ICheckFormat {
    @Override
    public Boolean checkFormat(MessageA msg) {
        return true;
    }

    @Override
    public byte[] substring(byte[] msg) {
        return msg;
    }

    public static Boolean isBeat(byte[] msg) {
        if (msg[5] == 0x02) return true;
        else return false;
    }

     public  Boolean checkSum(MessageA msg){
         return true;
     }
 }
    /*// 计算校验和算法，pktData为完整的数据包，key为传输密钥
    public byte[] checksum(byte[] pktData, byte[] key) {
        byte[] checksum = new byte[2];
        pktData[3] = pktData[4] = 0; //计算密钥时密钥2字节为0x00,计算完成后用结果赋值
        byte[] sum = new byte[]{0, 0, 0, 0};

        for (int j = 0; j < 4; j++) {
            for (int i = j; i < pktData.length; i += 4) {
                sum[j] += pktData[i];
            }
        }
        checksum[0] = pktData[3] = (byte) (sum[0] + sum[1] + key[1] + key[0]);
        checksum[1] = pktData[4] = (byte) (sum[2] + sum[3] + key[3] + key[2] + pktData[3]);
        return checksum;
    }

}*/