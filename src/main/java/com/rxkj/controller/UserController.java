package com.rxkj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rxkj.common.R;
import com.rxkj.entity.bo.MeiFenUser;
import com.rxkj.entity.dto.LoginDto;
import com.rxkj.entity.po.Users;
import com.rxkj.service.impl.UserServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
	public R login(@ModelAttribute LoginDto loginDto) {
		return userService.login(loginDto.getUserName(), loginDto.getPassword());
	}

	@GetMapping("/logout")
	public R<String> logout(@AuthenticationPrincipal MeiFenUser meiFenUser) {
		String userNumbers = meiFenUser.getUser().getUserNumbers();
		return userService.logout(userNumbers);
	}


	@PreAuthorize("hasAuthority('1')")
	@GetMapping("/selectAll")
	public R<List<Users>> selectAll() {
		List<Users> userList = userService.list(null);
		return R.success(userList);
	}

	@PreAuthorize("hasAuthority('1')")
	@GetMapping("/selectPage")
	public R<IPage<Users>> selectPage(Integer current, Integer size) {
		IPage<Users> page = new Page<>(current, size);
		userService.page(page);
		return R.success(page);
	}

	@PreAuthorize("hasAuthority('1')")
	@PostMapping("/addUser")
	public R addUser(@RequestBody Users user) {
		if (!checkStringNumbers(user.getUserNumbers(), 18)) {
			return R.error("Incorrectly formatted User ID number !!");
		}
		return userService.addUser(user);
	}

	@PreAuthorize("hasAuthority('1')")
	@PostMapping("/deleteByName")
	public R<String> deleteUserByName(@RequestBody Users user) {
		QueryWrapper<Users> qw = new QueryWrapper<>();
		qw.eq("user_name", user.getUserName());
		if (userService.remove(qw)) {
			return R.success("delete success!!");
		}
		return R.error("delete fails!!");
	}

	@PreAuthorize("hasAuthority('1')")
	@PostMapping("/deleteById")
	public R<String> deleteUserById(@RequestBody Users user) {
		QueryWrapper<Users> qw = new QueryWrapper<>();
		qw.eq("user_numbers", user.getUserNumbers());
		if (userService.remove(qw)) {
			return R.success("delete success!!");
		}
		return R.error("delete fails!!");
	}

	/**
	 * 校验字符串中的数字格式
	 * size:数字的个数
	 */
	private static boolean checkStringNumbers(String numberString, int size) {
		// 校验身份证格式
		if (numberString.length() == size) {
			if (numberString.substring(0, size - 1).matches("\\d+")) {
				return true;
			}
		}
		return false;
	}

}
