package com.rxkj.message;

public class LoginResponseMessage extends MessageA{
    public LoginResponseMessage(String magic, int length, String checksum, String command, String data) {
        super(magic, length, checksum, command, data);
    }
    public int getMessageType() {
        return LoginResponseMessage;
    }
}
