package com.rxkj.message;

public class ControlMessage extends Message{
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
