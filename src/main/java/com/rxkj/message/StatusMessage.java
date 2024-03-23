package com.rxkj.message;

import lombok.Data;

@Data
public class StatusMessage extends Message{

    private String deviceId;
    private String auxiliaryCoils;
    private String outputCoil;
    public StatusMessage(String deviceId,String auxiliaryCoils,String outputCoil){
        this.deviceId=deviceId;
        this.auxiliaryCoils=auxiliaryCoils;
        this.outputCoil=outputCoil;
    }
    public int getMessageType() {
        return StatusMessage;
    }
}
