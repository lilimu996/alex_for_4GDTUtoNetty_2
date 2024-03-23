package com.rxkj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rxkj.entity.po.DtuDevices;
import com.rxkj.entity.po.Users;
import com.rxkj.mapper.DtuMapper;
import com.rxkj.mapper.UsersMapper;
import com.rxkj.service.DtuService;
import com.rxkj.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class DtuServiceImpl extends ServiceImpl<DtuMapper, DtuDevices> implements DtuService {
}
