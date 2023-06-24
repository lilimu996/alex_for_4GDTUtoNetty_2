package com.rxkj.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public  class MessageA extends Message {
    /*
    所有的消息都是MessageA
     */
    private String magic;
    private int length;
    private String checksum;
    private String command;
    private String data;

    public MessageA(String magic, int length, String checksum, String command, String data) {
        this.magic=magic;
        this.length = length;
        this.checksum = checksum;
        this.command = command;
        this.data = data;
    }


    @Override
    public int getMessageType() {
        return MessageA;
    }
}
