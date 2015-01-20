package com.springrest.restserver.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springrest.restserver.domain.order.DepositOrder;
import com.springrest.restserver.repository.order.DepositOrderRepository;
import com.springrest.restserver.service.BalanceService;
import com.springrest.restserver.service.DepositOrderService;

@Service
@Transactional
public class DepositOrderServiceImpl implements DepositOrderService{
	
	
	@Autowired
	private DepositOrderRepository depositOrderRepository;
	
	@Autowired
	private BalanceService balanceService;

	@Override
	public void auditDepositOrder( String userId , String depositOrderId ) {
		
			DepositOrder depositOrder =depositOrderRepository.findOne(depositOrderId);
			depositOrder.setStatus(1);
			depositOrderRepository.save(depositOrder);
			
			balanceService.increaseBalance(depositOrder.getUserId(), depositOrder.getAmount());
		
	}

}
