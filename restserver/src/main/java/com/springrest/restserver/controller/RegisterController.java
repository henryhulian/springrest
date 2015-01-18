package com.springrest.restserver.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	
	private static Log log = LogFactory.getLog(RegisterController.class);

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	@ApiOperation(value="注册")
	public ResponseEntity<Void> regist(
			@ApiParam(defaultValue = "test001", required = true, value = "用户名") @RequestParam String userName,
			@ApiParam(defaultValue = "111111", required = true, value = "密码") @RequestParam String password
			) {
		
		User user = userRepository.findBySchemaPropertyValue("userName", userName);
		if(user!=null){
			 return ResponseEntity.status(201).build();
		}
		
		user = new User();
		user.setUserName(userName);
		user.setPassword(DigestUtil.sha256_base64(password));
		userRepository.save(user);
		
		log.trace("Create:"+user.getUserName());

		return  ResponseEntity.status(200).build();
	}

}
