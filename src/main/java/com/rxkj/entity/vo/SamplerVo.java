package com.rxkj.entity.vo;

import com.rxkj.enums.ExecutionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SamplerVo implements Serializable {
    private String samplerName;
    private Integer samplerId;
    private ExecutionStatus samplerStatus;
    private String groupName;
}
