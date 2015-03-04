package com.springrest.restserver.business.repository.order;



import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.springrest.restserver.business.entity.order.DepositOrder;



public interface DepositOrderRepository extends CrudRepository<DepositOrder,String> {
	
	public List<DepositOrder> findDepositOrderByUserId( Long userId );
	
}

