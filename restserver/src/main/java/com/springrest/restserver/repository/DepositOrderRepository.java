package com.springrest.restserver.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.springrest.restserver.domain.DepositOrder;


public interface DepositOrderRepository extends GraphRepository<DepositOrder> {}

