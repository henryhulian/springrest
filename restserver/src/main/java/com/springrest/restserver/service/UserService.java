package com.springrest.restserver.service;

import javax.servlet.http.HttpServletRequest;

import com.springrest.restserver.domain.user.User;

public interface UserService {

	User findCurrentUserByRequest( HttpServletRequest request );
	
	User findUserByUserName( String userName );
	
	User createUserAndAuthorization(String userName , String password);
	
}
