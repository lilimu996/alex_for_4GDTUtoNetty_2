package com.rxkj.message;

import lombok.Data;

@Data
public class IdentityMessage extends Message{

    private String imei;
    private String iccId;
    private String dtuV;
    public IdentityMessage(String imei,String iccId,String dtuV){
        this.imei=imei;
        this.iccId=iccId;
        this.dtuV=dtuV;
    }
    public int getMessageType() {
        return IdentityMessage;
    }
}
