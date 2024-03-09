package com.rxkj.enums;

public enum TimeoutEnum {
    //AlexUtil.bytesToHexString(new byte[]{(byte) 0x78})
    COMMON_TIMEOUT("0078","通用超时时间");
    public final String value;
    public final String type;

    TimeoutEnum(String  value, String type) {
        this.value = value;
        this.type = type;
    }
}
