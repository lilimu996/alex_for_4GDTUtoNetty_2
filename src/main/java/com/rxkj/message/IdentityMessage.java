package com.rxkj.message;

public class IdentityMessage extends MessageA{
    public IdentityMessage(String magic, int length, String checksum, String command, String data) {
        super(magic, length, checksum, command, data);
    }
    public int getMessageType() {
        return IdentityMessage;
    }
}
