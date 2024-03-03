package com.rxkj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rxkj.common.R;
import com.rxkj.entity.po.Users;


public interface UserService extends IService<Users> {


    R<String> login(String userName, String password);

}
