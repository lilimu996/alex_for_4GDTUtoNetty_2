package com.rxkj.enums;

public enum SseTypesEnum {

    PART_STATUS_CONNECT(1,"部件状态推送连接"),
    SAMPLE_CONNECT(2,"采样连接");



    public final Integer value;
    public final String type;
    SseTypesEnum(Integer value, String type) {
        this.value = value;
        this.type = type;
    }

}
