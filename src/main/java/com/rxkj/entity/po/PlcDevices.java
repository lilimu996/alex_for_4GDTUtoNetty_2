package com.rxkj.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
@Data
public class PlcDevices implements Serializable {
    //private static final long serialVersionUID = 1L;
    //plc id
    @TableId(type = IdType.AUTO)
    private Long plcId;
    //PLC站号
    private Integer plcStationNo;
    //PLC M线圈
    private String plcMcol;
    //PLC X线圈
    private String plcXcol;
    //PLC Y线圈
    private String plcYcol;
    //PLC状态
    private String plcStatus;
    //private String dtuid;
    private String dtuSerialNumber;
    /**
     * plc的状态， 00 前进
     *            01 后退
     *            02 暂停
     *            03 初始化
     * */
    //private String status;
}
