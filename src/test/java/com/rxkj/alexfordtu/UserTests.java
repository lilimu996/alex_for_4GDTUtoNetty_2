package com.rxkj.alexfordtu;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rxkj.entity.po.Users;
import com.rxkj.mapper.UsersMapper;
import com.rxkj.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@SpringBootTest
public class UserTests {
    @Autowired
    private UserService userService;

    @Resource
    UsersMapper usersMapper;

    @Test
    public void testSelectPage() {
        IPage<Users> page = new Page<>(1, 3);
        userService.page(page);
        System.out.println("当前页码" + page.getCurrent());
        System.out.println("本页条数" + page.getSize());
        System.out.println("总页数" + page.getPages());
        System.out.println("总条数" + page.getTotal());
        System.out.println(page.getRecords());


    }

    @Test
    public void DeleteUserByName() {
        QueryWrapper<Users> qw = new QueryWrapper<>();

        System.out.println(("----- DeleteByNameUser method test ------"));
        Users user = new Users();
        user.setUserName("joy");
        qw.eq("user_name", user.getUserName());
        userService.remove(qw);
    }

    @Test
    public void UpdateUserByIdTest() throws ParseException {
        System.out.println(("----- updateByIdUser method test ------"));
        Users user = new Users();
        user.setUserNumbers("513340198902112456");
        user.setUserAddress("江西省南昌市");
        user.setPhone("15165340980");
        user.setUserName("陈中");
        user.setUserPwd("chenzhong");
        String time = "1987-12-31";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = format.parse(time);
        user.setBrithData(date);
        user.setAge(35);
        user.setEmail("chengzhong@gmail.com");
        if (userService.updateById(user)) {
            System.out.println("update success!!");
        }
    }

    @Test
    public void AddUserTest() {
        System.out.println(("----- AddUser method test ------"));
        Users user = new Users();
        user.setUserNumbers("513340198902112456");
        user.setUserAddress("江西省南昌市");
        user.setPhone("15165340980");
        user.setUserName("陈中");
        user.setUserPwd("chenzhong");
        user.setAge(35);
        if (userService.save(user)) {
            System.out.println("--------Save Success!!----------");
        }
    }

    @Test
    public void UserSelectAllTest() {
        System.out.println(("----- selectAll method test ------"));
        List<Users> userList = userService.list(null);
        Assert.isTrue(2 == userList.size(), "");
        userList.forEach(System.out::println);
    }

    @Test
    void testPass() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String admin = bCryptPasswordEncoder.encode("admin");
        usersMapper.update(null, new UpdateWrapper<Users>().eq("user_name", "admin").set("user_pwd", admin));
    }

}
