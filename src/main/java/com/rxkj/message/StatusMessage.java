package com.rxkj.message;

import com.alibaba.druid.support.spring.stat.annotation.Stat;

public class StatusMessage extends MessageA{
    public StatusMessage(String magic, int length, String checksum, String command, String data) {
        super(magic, length, checksum, command, data);
    }
    public int getMessageType() {
        return StatusMessage;
    }
}
