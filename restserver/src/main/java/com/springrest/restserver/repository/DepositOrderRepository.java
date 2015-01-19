package com.springrest.restserver.repository;


import org.springframework.data.repository.CrudRepository;

import com.springrest.restserver.domain.DepositOrder;


public interface DepositOrderRepository extends CrudRepository<DepositOrder,Long> {}

