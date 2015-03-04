package com.springrest.restserver.business.service.domain;

import com.springrest.restserver.business.entity.user.Role;

public interface RoleService {

	public Role findOrCreateRoleByName( String name );
	
}
