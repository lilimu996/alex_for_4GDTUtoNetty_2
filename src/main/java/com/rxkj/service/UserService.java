package com.rxkj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rxkj.common.R;
import com.rxkj.entity.po.Users;

import java.util.HashMap;


public interface UserService extends IService<Users> {


    R<HashMap<String, Object>> login(String userName, String password);

    R<String> logout(String userNumbers);

}
