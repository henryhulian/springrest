package com.springrest.restserver.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springrest.restserver.domain.Session;
import com.springrest.restserver.domain.User;
import com.springrest.restserver.repository.UserRepository;
import com.springrest.restserver.service.Authorization;
import com.springrest.restserver.service.SessionService;
import com.springrest.restserver.service.UserService;
import com.springrest.restserver.util.CookieUtil;
import com.springrest.restserver.util.DigestUtil;
import com.springrest.restserver.util.TokenUtil;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private Authorization authorization;


	@Override
	public User findCurrentUserByRequest(HttpServletRequest request) {
		return findCurrentyUser(CookieUtil.getCookie(request, TokenUtil.TOKEN_COOKIE_NMAE));
	}
	
	public User findCurrentyUser(String token) {

		Session session = sessionService.findSessionByToken(token);
		
		User user = userRepository.findOne(session.getUserId());

		return user;
	}

	@Override
	public User createUserAndAuthorization(String userName, String password) {
		
		User user = new User();
		user.setUserName(userName);
		user.setBalance(new BigDecimal("0.00"));
		user.setPassword(DigestUtil.sha256_base64(password));
		userRepository.save(user);
		
		authorization.authorization(user.getId(), new HashSet<>(Arrays.asList("user")));

		return user;
	}

	@Override
	public User findUserByUserName(String userName) {
		return userRepository.findBySchemaPropertyValue("userName", userName);
	}
}
