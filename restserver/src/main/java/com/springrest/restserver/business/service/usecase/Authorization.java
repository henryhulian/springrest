package com.springrest.restserver.business.service.usecase;

import java.util.Set;

import org.springframework.cache.annotation.Cacheable;

public interface Authorization {

	@Cacheable(value="roles")
	boolean isUserAllowed(final Long userId , final Set<String> rolesSet);
   
	int authorization(final Long userId, final Set<String> rolesSet);
}
