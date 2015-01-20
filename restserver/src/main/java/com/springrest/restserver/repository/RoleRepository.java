package com.springrest.restserver.repository;

import org.springframework.data.repository.CrudRepository;

import com.springrest.restserver.domain.Role;


public interface RoleRepository extends CrudRepository<Role,Long>{
	
	public Role findRoleByName( String name );
	
}

