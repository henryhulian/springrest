package com.springrest.restserver.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springrest.restserver.domain.user.User;

public interface UserRepository extends JpaRepository<User,Long>{
	
	public User findUserByUserName( String userName );
}
