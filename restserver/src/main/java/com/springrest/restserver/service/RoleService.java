package com.springrest.restserver.service;

import com.springrest.restserver.domain.Role;

public interface RoleService {

	public Role findOrCreateRoleByName( String name );
	
}
