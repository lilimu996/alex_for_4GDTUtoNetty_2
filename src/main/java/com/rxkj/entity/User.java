package com.rxkj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;
@Data
@TableName("users")
public class User {
    //身份证号码
    //设置主键生成策略为手动设置
    @TableId(type = IdType.INPUT)
    private String userNumbers;
    //用户姓名
    private String userName;
    //用户性别
    private Integer gender;
    //用户年龄
    private Integer age;
    //用户出生年月日
    private Date brithData;
    //用户地址
    private String userAddress;
    //设备管理/使用者联系电话
    private String phone;
    //设备管理/使用者联系邮箱
    private String email;
    private String userPwd;
}
