package com.springrest.restserver.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.springrest.restserver.domain.DepositOrder;


public interface DepositOrderRepository extends CrudRepository<DepositOrder,Long> {
	
	public List<DepositOrder> findDepositOrderByUserId( Long userId );
	public List<DepositOrder> findDepositOrderByStatus( Integer status );
	
}

