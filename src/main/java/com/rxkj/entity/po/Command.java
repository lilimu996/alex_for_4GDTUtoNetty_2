package com.rxkj.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * @TableName command
 */
@TableName(value = "command")
@Data
public class Command {
    /**
     *
     */
    @TableId
    private Integer id;

    /**
     *
     */
    private String name;
}
