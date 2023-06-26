package com.rxkj.enums;

import com.rxkj.util.AlexUtil;

public enum TimeoutEnum {
    COMMON_TIMEOUT(AlexUtil.bytesToHexString(new byte[]{(byte) 0x78}),"通用超时时间");
    public final String value;
    public final String type;

    TimeoutEnum(String  value, String type) {
        this.value = value;
        this.type = type;
    }
}
