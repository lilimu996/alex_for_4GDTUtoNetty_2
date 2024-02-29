package com.rxkj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * by alex
 * 2020.11.18
 * 设备下挂载的泵的信息
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PumpCtrl implements Serializable {

    //id
    private Integer id;

    //泵的编号
    private String pumpId;

    //泵名称，用处
    private String pumpName;

    //所属设备，挂载到哪个通信id下的
    private String ownDeviceId;

    //状态，开关状态,1表示开着，2表示关闭状态
    private Integer state;

    //开启指令，hex字符串
    private String openCommand;

    //关闭指令，hex字符串
    private String offCommand;

    //从机地址，或者mobus地址 hex
    private String codeHead;

    //写寄存器code功能码
    private String writerEgCode;

    //寄存器地址
    private String registerAddr;

    //开启的功能码
    private String pumpOnCode;

    //关闭的功能码
    private String pumpOffCode;

    //额定电压
    private Double ratedVoltage;

    //额定电流
    private Double ratedCurrent;

    //额定功率
    private Double ratedPower;

    //过载电流 保护值
    private Double overloadCurrent;

    //备注
    private String mark;

}
