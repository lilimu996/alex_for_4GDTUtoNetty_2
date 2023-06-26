package com.rxkj.enums;

import com.rxkj.util.AlexUtil;

public enum KeywordEnum {

    CHANNEL_HEAD(AlexUtil.bytesToHexString(new byte[]{(byte) 0xAA,(byte) 0xAA}),"包头"),
    CHECKSUM(AlexUtil.bytesToHexString(new byte[]{(byte) 0x33,(byte) 0x33}),"校验码");
    public final String value;
    public final String type;

    KeywordEnum(String value, String type) {
        this.type = type;
        this.value = value;
    }

}
