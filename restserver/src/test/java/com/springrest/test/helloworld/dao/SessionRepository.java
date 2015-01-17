package com.springrest.test.helloworld.dao;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.springrest.test.helloworld.domain.Session;

public interface SessionRepository extends GraphRepository<Session> {}

