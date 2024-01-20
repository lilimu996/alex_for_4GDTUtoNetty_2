package com.rxkj.alexfordtu;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.rxkj.entity.User;
import com.rxkj.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserTests {
    @Autowired
    private UserService userService;


    @Test
    public void DeleteUserByName(){
        QueryWrapper<User> qw=new QueryWrapper<>();

        System.out.println(("----- DeleteByNameUser method test ------"));
        User user=new User();
        user.setUserName("joy");
        qw.eq("user_name",user.getUserName());
        userService.remove(qw);
    }

    @Test
    public void UpdateUserByIdTest(){
        System.out.println(("----- updateByIdUser method test ------"));
        User user=new User();
        user.setUserNumbers("513340198902112456");
        user.setUserAddress("江西省南昌市");
        user.setPhone("15165340980");
        user.setUserName("陈中");
        user.setUserPwd("chenzhong");
        user.setAge(35);
        user.setEmail("chengzhong@gmail.com");
        if(userService.updateById(user)){
            System.out.println("update success!!");
        }
    }
    @Test
    public void AddUserTest(){
        System.out.println(("----- AddUser method test ------"));
        User user=new User();
        user.setUserNumbers("513340198902112456");
        user.setUserAddress("江西省南昌市");
        user.setPhone("15165340980");
        user.setUserName("陈中");
        user.setUserPwd("chenzhong");
        user.setAge(35);
        if(userService.save(user)){
            System.out.println("--------Save Success!!----------");
        }
    }
    @Test
    public void UserSelectAllTest(){
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userService.list(null);
        Assert.isTrue(2 == userList.size(), "");
        userList.forEach(System.out::println);
    }
}
