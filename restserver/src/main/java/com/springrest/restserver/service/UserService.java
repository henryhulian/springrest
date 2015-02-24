package com.springrest.restserver.service;

import com.springrest.restserver.entity.user.User;

public interface UserService {

	User findCurrentUserByToken( String token );
	
	User findUserByUserName( String userName );
	
	User createUserAndAuthorization(String userName , String password);
	
}
