package com.springrest.restserver.repository.user;

import java.util.Set;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springrest.restserver.entity.user.UserRole;


public interface UserRoleRepository extends JpaRepository<UserRole,Long>{
	
	@Cacheable("roles")
	@Query("select u.roleName from UserRole u where u.userId = ?#{[0]}")
	public Set<String> findRolesForUserByUserId( Long userId );
	
}

