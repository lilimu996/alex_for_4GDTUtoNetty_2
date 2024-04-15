package com.rxkj.entity.bo;

import com.rxkj.entity.vo.SamplerVo;
import com.rxkj.enums.ExecutionStatus;
import lombok.Data;

@Data
public class ProcessingSampler {
    private String groupName;
    private SamplerVo sampler;
    private ExecutionStatus status;
}
