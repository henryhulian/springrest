package com.springrest.restserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController("/authentication")
@Api(value="/authentication",description="登录模块")
public class AuthenticationController {
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ApiOperation("登录")
	public String login(){
		
		return "SUCCESS";
	}

}
