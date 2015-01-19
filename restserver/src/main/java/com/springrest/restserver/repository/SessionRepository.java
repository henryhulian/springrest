package com.springrest.restserver.repository;

import org.springframework.data.repository.CrudRepository;

import com.springrest.restserver.domain.DepositOrder;


public interface SessionRepository extends CrudRepository<DepositOrder,Long> {}

