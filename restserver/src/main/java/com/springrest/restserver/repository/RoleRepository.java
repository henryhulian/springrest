package com.springrest.restserver.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.springrest.restserver.domain.DepositOrder;


public interface RoleRepository extends  CrudRepository<DepositOrder,Long> {
	
	public Set<String> findRolesForUserByUserId( Long userId );
	
}

