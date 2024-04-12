package com.rxkj.entity.bo;

import com.rxkj.entity.po.Sampler;
import com.rxkj.entity.vo.SamplerVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SamplerGroup {
    private String groupName;
    private List<SamplerVo> samplerList;
}
