package com.rxkj.entity.vo;

import com.rxkj.enums.ExecutionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SamplerVo {
    private String samplerName;
    private Integer samplerId;
    private ExecutionStatus samplerStatus;
}
