package com.rxkj.entity.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
