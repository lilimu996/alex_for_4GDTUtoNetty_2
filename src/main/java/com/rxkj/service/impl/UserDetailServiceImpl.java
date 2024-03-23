package com.rxkj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rxkj.entity.bo.MeiFenUser;
import com.rxkj.entity.po.Users;
import com.rxkj.mapper.UsersMapper;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    UsersMapper usersMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Users user = usersMapper.selectOne(new QueryWrapper<Users>().eq("user_name", userName));
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        MeiFenUser meiFenUser = new MeiFenUser();
        meiFenUser.setUser(user);
        return meiFenUser;
    }
}
