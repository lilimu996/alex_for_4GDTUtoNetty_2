package com.rxkj.enums;

import io.swagger.models.auth.In;

public enum ExecutionStatus {

    PENDING(1,"阻塞"),
    EXECUTING(2,"执行中"),
    COMPLETED(3,"完成"),
    FAILED(4,"错误"),
    READY(5,"就绪")


    ;
    public final Integer value;
    public final String type;
    ExecutionStatus(Integer value, String type) {
        this.value = value;
        this.type = type;
    }
}
