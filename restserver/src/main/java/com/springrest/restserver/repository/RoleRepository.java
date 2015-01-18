package com.springrest.restserver.repository;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.springrest.restserver.domain.Role;


public interface RoleRepository extends GraphRepository<Role> {
	
	@Query(value = "start n=node({0}) match (n)-[r:HAS_ROLE]->(role) return role.name")
	public Set<String> findRolesForUserByUserId( Long userId );
	
}

