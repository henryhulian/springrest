package com.springrest.restserver.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.springrest.restserver.domain.User;

public interface UserRepository extends GraphRepository<User>{
	
}
