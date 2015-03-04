package com.springrest.restserver.business.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springrest.restserver.business.entity.user.Role;


public interface RoleRepository extends JpaRepository<Role,Long>{
	
	public Role findRoleByName( String name );
	
}

