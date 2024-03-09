package com.rxkj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rxkj.common.R;
import com.rxkj.entity.bo.MeiFenUser;
import com.rxkj.entity.po.Users;
import com.rxkj.mapper.UsersMapper;
import com.rxkj.service.UserService;
import com.rxkj.util.Commons;
import com.rxkj.util.JwtUtil;
import com.rxkj.util.RedisUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UsersMapper, Users> implements UserService {

    @Resource
    RedisUtil redisUtil;

    @Resource
    AuthenticationManager authenticationManager;

    @Override
    public R<String> login(String userName, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, password);

        Authentication authenticate = null;

        try {
            authenticate = authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            return R.error("请检查用户名和密码");
        }

        MeiFenUser meiFenUser = (MeiFenUser) authenticate.getPrincipal();

        String userNumbers = meiFenUser.getUser().getUserNumbers();


        // 验证成功

        Map<String, Object> map = new HashMap<>();
        map.put("userNumbers", userNumbers);
        redisUtil.set(Commons.REDIS_KEY_MEI_FEN_USER + userNumbers, meiFenUser);

        String token = JwtUtil.sign(map);
        return R.success(token);
    }
}
