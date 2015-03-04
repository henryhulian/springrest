package com.springrest.restserver.business.service.domain.impl;

import java.util.Arrays;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springrest.restserver.business.entity.user.Session;
import com.springrest.restserver.business.entity.user.User;
import com.springrest.restserver.business.repository.user.UserRepository;
import com.springrest.restserver.business.service.domain.SessionService;
import com.springrest.restserver.business.service.domain.UserService;
import com.springrest.restserver.business.service.usecase.Authorization;
import com.springrest.restserver.business.util.DigestUtil;

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
	public User findCurrentUserByToken(String token) {
		return findCurrentyUser(token);
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
		user.setPassword(DigestUtil.sha256_base64(password));
		userRepository.save(user);
		authorization.authorization(user.getId(), new HashSet<>(Arrays.asList("user")));

		return user;
	}

	@Override
	public User findUserByUserName(String userName) {
		return userRepository.findUserByUserName(userName);
	}

	@Override
	public User findCurrentUserByRequest(HttpServletRequest request) {
		Session session= sessionService.findSessionByRequest(request);
		User user = userRepository.findOne(session.getUserId());
		return user;
	}
}
