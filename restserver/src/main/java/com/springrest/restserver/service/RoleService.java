package com.springrest.restserver.service;

import com.springrest.restserver.domain.user.Role;

public interface RoleService {

	public Role findOrCreateRoleByName( String name );
	
}
