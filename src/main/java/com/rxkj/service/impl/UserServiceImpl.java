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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UsersMapper, Users> implements UserService {

    @Resource
    RedisUtil redisUtil;

    @Resource
    PasswordEncoder passwordEncoder;

    @Resource
    AuthenticationManager authenticationManager;

    @Override
    public R<HashMap<String, Object>> login(String userName, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, password);

        Authentication authenticate = null;

        try {
            authenticate = authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return R.error("请检查用户名和密码");
        }

        MeiFenUser meiFenUser = (MeiFenUser) authenticate.getPrincipal();

        String userNumbers = meiFenUser.getUser().getUserNumbers();


        // 验证成功

        Map<String, Object> map = new HashMap<>();
        map.put("userNumbers", userNumbers);
        redisUtil.set(Commons.REDIS_KEY_MEI_FEN_USER + userNumbers, meiFenUser, RedisUtil.ONE_HOUR);

        String token = JwtUtil.sign(map);
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        Integer isRoot = meiFenUser.getUser().getIsRoot();
        if (isRoot.equals(1)) {
            resultMap.put("identify", "superAdmin");
        } else {
            resultMap.put("identify", "admin");
        }

        return R.success(resultMap);
    }

    @Override
    public R<String> logout(String userNumbers) {
        redisUtil.delete(Commons.REDIS_KEY_MEI_FEN_USER + userNumbers);
        return R.success();
    }

    @Override
    public R addUser(Users user) {
        // 判断是否已经存在
        String userNumbers = user.getUserNumbers();
        Users users = query().eq("user_numbers", userNumbers).one();
        if (!Objects.isNull(users)) {
            return R.error("用户已存在");
        }
        user.setUserPwd(passwordEncoder.encode("123456"));
        save(user);
        return R.success();
    }
}
