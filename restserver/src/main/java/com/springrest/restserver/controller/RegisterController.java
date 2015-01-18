package com.springrest.restserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springrest.restserver.domain.User;
import com.springrest.restserver.repository.UserRepository;
import com.springrest.restserver.util.DigestUtil;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@RestController("/register")
@Api(value = "/register", description = "注册模块")
public class RegisterController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	@ApiOperation(value="注册")
	public String regist(
			@ApiParam(defaultValue = "test001", required = true, value = "用户名") @RequestParam String userName,
			@ApiParam(defaultValue = "111111", required = true, value = "密码") @RequestParam String password
			) {

		User user = new User();
		user.setUsername(userName);
		user.setPassword(DigestUtil.sha256_base64(password));
		userRepository.save(user);

		return "SUCCESS";
	}

}
