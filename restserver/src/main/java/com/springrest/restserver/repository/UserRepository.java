package com.springrest.restserver.repository;



import org.springframework.data.repository.CrudRepository;

import com.springrest.restserver.domain.User;

public interface UserRepository extends CrudRepository<User,Long>{
	
}
