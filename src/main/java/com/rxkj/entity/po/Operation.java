package com.rxkj.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * @TableName operation
 */
@TableName(value = "operation")
@Data
@Builder
public class Operation {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    private Integer deviceId;

    /**
     *
     */
    private String userNumbers;

    /**
     *
     */
    private Integer operateId;

    /**
     *
     */
    private Date time;
}
