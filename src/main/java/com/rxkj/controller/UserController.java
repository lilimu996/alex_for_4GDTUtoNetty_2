package com.rxkj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rxkj.common.R;
import com.rxkj.entity.User;
import com.rxkj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/")
    public String helloUser(){
        return "users!";
    }
    @GetMapping("/selectAll")
    public R<List<User>> selectAll(){
        List<User> userList = userService.list(null);
        return R.success(userList);
    }
    @PostMapping("/addUser")
    public R<String> addUser(@RequestBody User user){
        if(userService.save(user)){
            return R.success("add success!");
        }
        return R.error("add fails!!");
    }
    @PostMapping("/updateUserById")
    public R<String> updateUserById(@RequestBody User user){
        if(userService.updateById(user)){
            return R.success("update success!");
        }
        return R.error("uptate fails!!");
    }
    @PostMapping("/deleteByName")
    public R<String> deleteUserByName(@RequestBody User user){
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("user_name",user.getUserName());
        if(userService.remove(qw)){
            return R.success("delete success!!");
        }
        return R.error("delete fails!!");
    }
}
