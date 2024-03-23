package com.rxkj.enums;

public enum CommandEnum {

   //AlexUtil.bytesToHexString(new byte[]{(byte) 0x00})
    START_COMMAND("00","启动连接"),
    //AlexUtil.bytesToHexString(new byte[]{(byte) 0x01})
    UPLOAD_IDENTITY_COMMAND("01","DTU上传身份"),
    //AlexUtil.bytesToHexString(new byte[]{(byte) 0xFE})
    UPLOAD_STATUS_COMMAND("02","上传设备状态"),

    CONTROLLER_COMMAND("03","控制设备"),
    RESPONSE_COMMAND("FE","指令回复"),


    ;
    public final String value;
    public final String type;
    CommandEnum(String value, String type) {
        this.value = value;
        this.type = type;
    }
}
