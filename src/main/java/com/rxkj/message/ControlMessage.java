package com.rxkj.message;

public class ControlMessage extends MessageA{
    public ControlMessage(String magic, int length, String checksum, String command, String data) {
        super(magic, length, checksum, command, data);
    }
    public int getMessageType() {
        return ControlMessage;
    }
}
