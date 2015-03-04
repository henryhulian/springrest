package com.springrest.restserver.business.service.usecase.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springrest.restserver.business.entity.order.DepositOrder;
import com.springrest.restserver.business.repository.order.DepositOrderRepository;
import com.springrest.restserver.business.service.domain.BalanceService;
import com.springrest.restserver.business.service.usecase.DepositOrderService;

@Service
@Transactional
public class DepositOrderServiceImpl implements DepositOrderService{
	
	
	@Autowired
	private DepositOrderRepository depositOrderRepository;
	
	@Autowired
	private BalanceService balanceService;

	@Override
	public void auditDepositOrder( String depositOrderId ) {
		
			DepositOrder depositOrder =depositOrderRepository.findOne(depositOrderId);
			depositOrder.setStatus(1);
			depositOrderRepository.save(depositOrder);
			
			balanceService.increaseBalance(depositOrder.getUserId(), depositOrder.getAmount());
		
	}

}
