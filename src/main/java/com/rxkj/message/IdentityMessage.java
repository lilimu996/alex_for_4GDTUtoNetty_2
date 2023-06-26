package com.rxkj.message;

import lombok.Data;

@Data
public class IdentityMessage extends Message{

    private String imei;
    private String iccid;
    private String dtuv;
    public IdentityMessage(String imei,String iccid,String dtuv){
        this.imei=imei;
        this.iccid=iccid;
        this.dtuv=dtuv;
    }
    public int getMessageType() {
        return IdentityMessage;
    }
}
