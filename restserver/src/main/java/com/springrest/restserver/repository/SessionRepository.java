package com.springrest.restserver.repository;

import org.springframework.data.repository.CrudRepository;

import com.springrest.restserver.domain.Session;


public interface SessionRepository extends CrudRepository<Session,Long> {}

