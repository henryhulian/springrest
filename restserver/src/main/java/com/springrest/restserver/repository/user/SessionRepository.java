package com.springrest.restserver.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springrest.restserver.entity.user.Session;


public interface SessionRepository extends JpaRepository<Session,String> {
	
	@Override
	public Session findOne(String arg0);
	
	@Override
	public <S extends Session> S save(S arg0);
	
	@Override
	public <S extends Session> List<S> save(Iterable<S> entities);
	
}

