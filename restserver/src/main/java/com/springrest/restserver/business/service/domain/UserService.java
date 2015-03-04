package com.springrest.restserver.business.service.domain;

import javax.servlet.http.HttpServletRequest;

import com.springrest.restserver.business.entity.user.User;

public interface UserService {
	
	User findCurrentUserByRequest( HttpServletRequest request );

	User findCurrentUserByToken( String token );
	
	User findUserByUserName( String userName );
	
	User createUserAndAuthorization(String userName , String password);
	
}
