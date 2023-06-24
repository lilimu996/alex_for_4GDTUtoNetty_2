package com.rxkj.message;

public class PongMessage extends MessageA{
    public PongMessage(String magic, int length, String checksum, String command, String data) {
        super(magic, length, checksum, command, data);
    }

    public int getMessageType(){
        return PongMessage;
    }
}
