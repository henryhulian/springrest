package com.springrest.restserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springrest.restserver.domain.Session;
import com.springrest.restserver.domain.User;
import com.springrest.restserver.repository.UserRepository;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SessionService sessionService;


	public User findCurrentyUser(String token) {

		Session session = sessionService.findSessionByToken(token);
		
		User user = userRepository.findBySchemaPropertyValue("userName", session.getUserName());

		return user;
	}
}
