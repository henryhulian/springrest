package com.springrest.restserver.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springrest.restserver.common.Code;
import com.springrest.restserver.repository.user.UserRepository;
import com.springrest.restserver.service.Authenticatior;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/authentication")
@Api(value="/authentication",description="登录模块")
public class AuthenticationController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private Authenticatior authenticatior;
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ApiOperation("登录")
	public Code login(@ApiParam(defaultValue = "test001", required = true, value = "用户名") @RequestParam String userName,
			@ApiParam(defaultValue = "111111", required = true, value = "密码") @RequestParam String password,
			HttpServletRequest request,
			HttpServletResponse response){
		
		authenticatior.login(userName, password, request, response);
		
		return new Code(Code.SUCCESS);
	}

}
