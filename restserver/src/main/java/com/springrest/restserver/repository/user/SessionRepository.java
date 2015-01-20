package com.springrest.restserver.repository.user;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.springrest.restserver.domain.user.Session;

@CacheConfig(cacheNames="session")
public interface SessionRepository extends JpaRepository<Session,Long> {
	
	@Override
	@Cacheable
	public Session findOne(Long arg0);
	
	@Override
	@CachePut
	public <S extends Session> S save(S arg0);
	
	@Override
	@CachePut
	public <S extends Session> List<S> save(Iterable<S> entities);
	
}

