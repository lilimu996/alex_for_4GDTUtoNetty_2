package com.rxkj.message;



import lombok.Data;

import java.io.Serializable;

@Data
public class MessageOld implements Serializable{


    private String magic;
    private int length;
    private String checksum;
    private String command;
    private String data;

    public MessageOld(String magic, int length, String checksum, String command, String data) {
        this.magic=magic;
        this.length = length;
        this.checksum = checksum;
        this.command = command;
        this.data = data;
    }

}
