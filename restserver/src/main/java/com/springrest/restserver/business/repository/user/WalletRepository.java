package com.springrest.restserver.business.repository.user;


import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.springrest.restserver.business.entity.user.Wallet;


public interface WalletRepository extends JpaRepository<Wallet,Long>{
	
	@Lock(LockModeType.OPTIMISTIC)
	public Wallet findWalletWithLockByUserId(Long userId);
}

