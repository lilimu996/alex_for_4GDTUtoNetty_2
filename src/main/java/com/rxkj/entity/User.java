package com.rxkj.entity;

import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;
@Data
public class User {
    //身份证号码
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
