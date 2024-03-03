package com.rxkj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rxkj.common.R;
import com.rxkj.entity.dto.LoginDto;
import com.rxkj.entity.po.Users;
import com.rxkj.service.UserService;
import com.rxkj.service.impl.UserDetailServiceImpl;
import com.rxkj.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserServiceImpl userService;

    @GetMapping("/")
    public String helloUser() {
        return "users!";
    }

    @GetMapping("/login")
    public R<String> login(@ModelAttribute LoginDto loginDto) {
        return userService.login(loginDto.getUserName(), loginDto.getPassword());
    }


    // @GetMapping("/selectAll")
    // public R<List<Users>> selectAll() {
    //     List<Users> userList = userService.list(null);
    //     return R.success(userList);
    // }
    //
    // @GetMapping("/selectPage")
    // public R<IPage<Users>> selectPage(Integer current, Integer size) {
    //     IPage<Users> page = new Page<>(current, size);
    //     userService.page(page);
    //     return R.success(page);
    // }
    //
    // @PostMapping("/addUser")
    // public R<String> addUser(@RequestBody Users user) {
    //     if (!checkStringNumbers(user.getUserNumbers(), 18)) {
    //         return R.error("Incorrectly formatted User ID number !!");
    //     }
    //     if (userService.save(user)) {
    //         return R.success("add success!");
    //     }
    //     return R.error("add fails!!");
    // }
    //
    // @PostMapping("/updateUserById")
    // public R<String> updateUserById(@RequestBody Users user) {
    //     if (!checkStringNumbers(user.getUserNumbers(), 18)) {
    //         return R.error("Incorrectly formatted User ID number !!");
    //     }
    //     if (userService.updateById(user)) {
    //         return R.success("update success!");
    //     }
    //     return R.error("uptate fails!!");
    // }
    //
    // @PostMapping("/deleteByName")
    // public R<String> deleteUserByName(@RequestBody Users user) {
    //     QueryWrapper<Users> qw = new QueryWrapper<>();
    //     qw.eq("user_name", user.getUserName());
    //     if (userService.remove(qw)) {
    //         return R.success("delete success!!");
    //     }
    //     return R.error("delete fails!!");
    // }
    //
    // @PostMapping("/deleteById")
    // public R<String> deleteUserById(@RequestBody Users user) {
    //     QueryWrapper<Users> qw = new QueryWrapper<>();
    //     qw.eq("user_numbers", user.getUserNumbers());
    //     if (userService.remove(qw)) {
    //         return R.success("delete success!!");
    //     }
    //     return R.error("delete fails!!");
    // }
    //
    // /**
    //  * 校验字符串中的数字格式
    //  * size:数字的个数
    //  */
    // private static boolean checkStringNumbers(String numberString, int size) {
    //     // 校验身份证格式
    //     if (numberString.length() == size) {
    //         if (numberString.substring(0, size - 1).matches("\\d+")) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }

}
