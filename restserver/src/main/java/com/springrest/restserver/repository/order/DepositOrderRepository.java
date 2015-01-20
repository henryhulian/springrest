package com.springrest.restserver.repository.order;



import java.util.List;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.springrest.restserver.domain.order.DepositOrder;



public interface DepositOrderRepository extends CrudRepository<DepositOrder,String> {
	
	@Query("select * from depositorder where userId=? limit 10")
	public List<DepositOrder> findDepositOrderByUserId( Long userId );
	
}

