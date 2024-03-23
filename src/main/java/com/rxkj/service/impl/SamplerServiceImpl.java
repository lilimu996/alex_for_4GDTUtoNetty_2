package com.rxkj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rxkj.entity.po.Sampler;
import com.rxkj.mapper.SamplerMapper;
import com.rxkj.service.SamplerService;
import org.springframework.stereotype.Service;

@Service
public class SamplerServiceImpl extends ServiceImpl<SamplerMapper, Sampler> implements SamplerService {
}
