package com.springrest.restserver.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springrest.restserver.domain.Session;
import com.springrest.restserver.domain.User;
import com.springrest.restserver.repository.UserRepository;
import com.springrest.restserver.util.CookieUtil;
import com.springrest.restserver.util.TokenUtil;

@Service
@Transactional
public class UserServiceImpl {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SessionServiceImpl sessionService;


	public User findCurrentyUser(String token) {

		Session session = sessionService.findSessionByToken(token);
		
		User user = userRepository.findOne(session.getUserId());

		return user;
	}


	public User findCurrentyUser(HttpServletRequest request) {
		return findCurrentyUser(CookieUtil.getCookie(request, TokenUtil.TOKEN_COOKIE_NMAE));
	}
}
