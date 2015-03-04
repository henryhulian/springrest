package com.springrest.restserver.business.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springrest.restserver.business.entity.user.User;

public interface UserRepository extends JpaRepository<User,Long>{
	
	public User findUserByUserName( String userName );
}
