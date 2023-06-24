package com.rxkj.message;

public class ModifyMessage extends MessageA{
    public ModifyMessage(String magic, int length, String checksum, String command, String data) {
        super(magic, length, checksum, command, data);
    }
    public int getMessageType() {
        return ModifyMessage;
    }
}
