package com.rxkj.check;

import com.rxkj.message.MessageA;
import com.rxkj.util.ICheckFormat;

public class DataCheckFormat implements ICheckFormat {
    @Override
    public  Boolean checkFormat(MessageA msg) {
        return true;
    }

    @Override
    public byte[] substring(byte[] msg) {
        return msg;
    }
    public static Boolean isBeat(byte[] msg){
        if (msg[5]==0x02) return true;
        else return false;
    }
    public  Boolean checkSum(MessageA msg){
        return true;
    }
}
