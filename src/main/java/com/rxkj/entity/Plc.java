package com.rxkj.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Plc implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String dtuid;

    /**
     * plc的状态， 00 前进
     *            01 后退
     *            02 暂停
     *            03 初始化
     * */
    private String status;
}
