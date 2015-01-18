package com.springrest.restserver.service;

import java.util.Set;

public interface Authorization {

	boolean isUserAllowed(final Long userId , final Set<String> rolesSet);
   
	int authorization(final Long userId, final Set<String> rolesSet);
}
