package com.rxkj.enums;

public enum CommandLengthEnum {
    START_LENGTH(10,"启动连接指令长度"),
    UPLOAD_IDENTITY_LENGTH(45,"DTU上传身份指令长度"),
    UPLOAD_STATUS_LENGTH(8,"控制设备指令长度"),
    RESPONSE_COMMAND(9,"指令回复长度"),
    ;
    public final int value;
    public final String type;

    CommandLengthEnum(int value, String type) {
        this.type = type;
        this.value = value;
    }
}
