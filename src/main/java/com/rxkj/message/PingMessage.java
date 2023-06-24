package com.rxkj.message;

public class PingMessage extends MessageA {
    public PingMessage(String magic, int length, String checksum, String command, String data) {
        super(magic, length, checksum, command, data);
    }

    public int getMessageType(){ return PingMessage;}
}
