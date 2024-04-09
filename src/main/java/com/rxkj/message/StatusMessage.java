package com.rxkj.message;

import lombok.Data;

@Data
public class StatusMessage extends Message{

    private String deviceId;
    private String coilAddress;
    private String outputCoil;
    public StatusMessage(String deviceId, String coilAddress, String outputCoil){
        this.deviceId=deviceId;
        this.coilAddress = coilAddress;
        this.outputCoil=outputCoil;
    }
    public int getMessageType() {
        return StatusMessage;
    }
}
