package com.rxkj.message;

public class MaintenanceMessage extends MessageA{
    /**
     * 维护信息
     * @param magic
     * @param length
     * @param checksum
     * @param command
     * @param data
     */
    public MaintenanceMessage(String magic, int length, String checksum, String command, String data) {
        super(magic, length, checksum, command, data);
    }
    public int getMessageType() {
        return MaintenanceMessage;
    }
}
