package com.rxkj.entity.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName users
 */
@TableName(value = "users")
@Data
public class Users implements Serializable {
    /**
     *
     */
    @TableId
    private String userNumbers;

    private Integer isRoot;

    /**
     *
     */
    private String userName;

    /**
     *
     */
    private Integer gender;

    /**
     *
     */
    private Integer age;

    /**
     *
     */
    private Date brithData;

    /**
     *
     */
    private String userAddress;

    /**
     *
     */
    private String phone;

    /**
     *
     */
    private String email;

    /**
     *
     */
    private String userPwd;
}
