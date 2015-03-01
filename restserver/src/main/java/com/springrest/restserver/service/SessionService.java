package com.springrest.restserver.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import com.springrest.restserver.entity.user.Session;

@CacheConfig(cacheNames="session")
public interface SessionService {
	
	public Session createUserSession( String userName , Long userId , String userIp);
	
	@Cacheable
	public Session findSessionByToken(String token);
	
	public Session createGuestSession(String userIp);
	
	public Session updateGuestSession(Session session);
	
	public Session findGuestSessionByToken(String token);
	
}
