package com.springrest.restserver.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springrest.restserver.common.Code;
import com.springrest.restserver.entity.user.User;
import com.springrest.restserver.repository.user.UserRepository;
import com.springrest.restserver.service.BalanceService;

@Service
@Transactional
public class BalanceServiceImpl implements BalanceService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public int increaseBalance(Long userId, BigDecimal amount) {
		
		User user = userRepository.findOne(userId);
		user.setBalance(user.getBalance().add(amount));
		
		userRepository.save(user);
		
		return Code.SUCCESS;
	}

	@Override
	public int decreaseBalance(Long userId, BigDecimal amount) {
		
		User user = userRepository.findOne(userId);
		user.setBalance(user.getBalance().subtract(amount));
		
		userRepository.save(user);
		
		return Code.SUCCESS;
	}

	@Override
	public BigDecimal findBalance(Long userId) {
		User user = userRepository.findOne(userId);
		return user.getBalance();
	}

}
