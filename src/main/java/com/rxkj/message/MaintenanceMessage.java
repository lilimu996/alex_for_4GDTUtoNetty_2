package com.rxkj.message;

public class MaintenanceMessage extends MessageA{
    public MaintenanceMessage(String magic, int length, String checksum, String command, String data) {
        super(magic, length, checksum, command, data);
    }
    public int getMessageType() {
        return MaintenanceMessage;
    }
}
