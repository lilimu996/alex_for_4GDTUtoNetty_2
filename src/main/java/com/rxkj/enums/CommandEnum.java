package com.rxkj.enums;

import com.rxkj.util.AlexUtil;

public enum CommandEnum {

    START_COMMAND(AlexUtil.bytesToHexString(new byte[]{(byte) 0x00}),"启动连接"),
    UPLOAD_COMMAND(AlexUtil.bytesToHexString(new byte[]{(byte) 0x01}),"DTU上传身份"),
    RESPONSE_COMMAND(AlexUtil.bytesToHexString(new byte[]{(byte) 0xFE}),"指令回复"),
    ;
    public final String value;
    public final String type;
    CommandEnum(String value, String type) {
        this.value = value;
        this.type = type;
    }
}
