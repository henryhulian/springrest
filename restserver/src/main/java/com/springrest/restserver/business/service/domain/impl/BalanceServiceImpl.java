package com.springrest.restserver.business.service.domain.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springrest.restserver.business.common.Code;
import com.springrest.restserver.business.entity.user.Wallet;
import com.springrest.restserver.business.repository.user.WalletRepository;
import com.springrest.restserver.business.service.domain.BalanceService;

@Service
@Transactional
public class BalanceServiceImpl implements BalanceService{
	
	
	@Autowired
	private WalletRepository walletRepository;

	@Override
	public int increaseBalance(Long userId, BigDecimal amount) {
		
		Wallet wallet = walletRepository.findWalletWithLockByUserId(userId);
		wallet.setBalance(wallet.getBalance().add(amount));
		
		walletRepository.save(wallet);
		
		return Code.SUCCESS;
	}

	@Override
	public int decreaseBalance(Long userId, BigDecimal amount) {
		
		Wallet wallet = walletRepository.findWalletWithLockByUserId(userId);
		wallet.setBalance(wallet.getBalance().subtract(amount));
		
		walletRepository.save(wallet);
		
		return Code.SUCCESS;
	}

	@Override
	public BigDecimal findBalance(Long userId) {
		Wallet wallet = walletRepository.findOne(userId);
		return wallet.getBalance();
	}

}
