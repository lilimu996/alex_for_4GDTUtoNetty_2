package com.rxkj.alexfordtu;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.rxkj.entity.User;
import com.rxkj.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AlexfordtuApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Test
    void contextLoads() {

    }
    @Test
    public void testUserSelect(){
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.isTrue(2 == userList.size(), "");
        userList.forEach(System.out::println);
    }
    @Test
    public void testUserInsert(){
        System.out.println("----- UserInsert method test ------");
        User user=new User();
        user.setUserNumbers("410000198710231113");
        user.setUserAddress("福建省福州市");
        user.setPhone("13963458576");
        user.setUserName("王晓");
        user.setUserPwd("wangxiao");
        user.setAge(37);
        userMapper.insert(user);
    }
}
