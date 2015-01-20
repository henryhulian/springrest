package com.springrest.restserver.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.springrest.restserver.domain.UserRole;


public interface UserRoleRepository extends CrudRepository<UserRole,Long>{
	
	@Query("select u.roleName from UserRole u where u.userId = ?#{[0]}")
	public Set<String> findRolesForUserByUserId( Long userId );
	
}

