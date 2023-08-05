package com.rxkj.entity;

import com.rxkj.message.Message;
import lombok.Data;

@Data
public class ControlMessage extends Message {
    String deviceId;
    String command;
    public ControlMessage(String deviceId,String command) {
        this.deviceId=deviceId;
        this.command=command;
    }
    public int getMessageType() {
        return ControlMessage;
    }
}
