package com.springrest.restserver.repository;

import java.util.Set;


import org.springframework.data.repository.CrudRepository;

import com.springrest.restserver.domain.Role;


public interface RoleRepository extends CrudRepository<Role,Long>{
	
	public Set<String> findRolesForUserByUserId( Long userId );
	
}

