package com.rxkj.message;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
@Data
public abstract class Message implements Serializable {

    /**
     *Message
     * 包头 0xAAAA
     * 长度 0xNN
     * 校验 0xNNNN
     * 指令 0xNN
     * 数据 0xNN~
     */

    //接收整数messageType返回一个class对象
    public static Class<?> getMessageClass(int messageType) {
        return messageClasses.get(messageType);
    }
    private int sequenceId;
    private int messageType;

    public abstract int getMessageType();

    public static final int MessageA= 0;

    //服务器发送给客户端的启动消息，指令0x00
    public static final int LoginResponseMessage=1;

    public static final int IdentityMessage=2;

    public static final int StatusMessage=3;

    public static final int ControlMessage=4;

    public static final int MaintenanceMessage=5;

    public static final int ModifyMessage=6;

    public static final int ResponseMessage=7;
    public static final int PingMessage = 14;
    public static final int PongMessage = 15;

    public static final int RPC_MESSAGE_TYPE_REQUEST = 101;
    public static final int  RPC_MESSAGE_TYPE_RESPONSE = 102;


    private static final Map<Integer, Class<?>> messageClasses = new HashMap<>();
    static {
        messageClasses.put(MessageA, MessageA.class);
        messageClasses.put(LoginResponseMessage,LoginResponseMessage.class);
        messageClasses.put(IdentityMessage,IdentityMessage.class);
        messageClasses.put(StatusMessage,StatusMessage.class);
        messageClasses.put(ControlMessage,ControlMessage.class);
        messageClasses.put(MaintenanceMessage,MaintenanceMessage.class);
        messageClasses.put(ModifyMessage,ModifyMessage.class);
        messageClasses.put(ResponseMessage,ResponseMessage.class);
        messageClasses.put(PingMessage,PingMessage.class);
        messageClasses.put(PongMessage,PongMessage.class);
    }
}
