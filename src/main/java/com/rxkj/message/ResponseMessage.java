package com.rxkj.message;

public class ResponseMessage extends MessageA{
    public ResponseMessage(String magic, int length, String checksum, String command, String data) {
        super(magic, length, checksum, command, data);
    }
    public int getMessageType() {
        return ResponseMessage;
    }
}
