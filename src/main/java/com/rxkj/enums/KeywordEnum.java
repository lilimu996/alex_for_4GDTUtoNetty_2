package com.rxkj.enums;

public enum KeywordEnum {
    //AlexUtil.bytesToHexString(new byte[]{(byte) 0xAA,(byte) 0xAA})
    CHANNEL_HEAD("AAAA","包头"),
    //AlexUtil.bytesToHexString(new byte[]{(byte) 0x33,(byte) 0x33})
    KEY("33333333","校验码");
    public final String value;
    public final String type;

    KeywordEnum(String value, String type) {
        this.type = type;
        this.value = value;
    }

}
