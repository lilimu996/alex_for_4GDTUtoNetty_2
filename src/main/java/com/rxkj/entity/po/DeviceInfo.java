package com.rxkj.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * by alex
 * 2020.11.18
 * DTU设备信息
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeviceInfo implements Serializable {

    //用于数据索引id,主键自增
    private Integer deviceId;

    //设备通信id，心跳及登录发送内容为设备id
    private String deviceCtId;

    //设备出厂身份识别id，为厂家编制的唯一身份识别id,物联网云id
    private String deviceVerifyId;

    //设备名称
    private String deviceName;

    //所在省份
    private String deviceProvince;

    //所在市（区）县
    private String deviceCityAndCounty;

    //设备地址
    private String deviceLocation;

    //设备型号
    private String deviceType;

    //设备信息描述
    private String deviceDescription;

    //设备管理人，使用者
    private String deviceKeeper;

    //设备管理/使用者联系电话
    private String deviceKeeperPhone;

    //设备创建时间
    private Date deviceFirstOnline;

    //设备状态，1是在线，2是不在线
    private Integer deviceState;

    //设备权限等级，预留设计项
    private String deviceAuthLevel;

    //备注
    private String deviceMark;

}
