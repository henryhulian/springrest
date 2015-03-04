package com.springrest.restserver.business.repository.user;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springrest.restserver.business.entity.user.UserRole;


public interface UserRoleRepository extends JpaRepository<UserRole,Long>{
	
	@Query("SELECT roleName FROM UserRole  WHERE userId=:userId")
	public Set<String> findRolesForUserByUserId( @Param("userId") Long userId );
	
}

