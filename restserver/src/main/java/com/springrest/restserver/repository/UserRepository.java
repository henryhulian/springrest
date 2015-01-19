package com.springrest.restserver.repository;

import org.springframework.data.gemfire.repository.GemfireRepository;


import com.springrest.restserver.domain.User;

public interface UserRepository extends GemfireRepository<User,Long>{
	
}
