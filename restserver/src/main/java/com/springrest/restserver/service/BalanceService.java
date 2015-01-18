package com.springrest.restserver.service;

import java.math.BigDecimal;

public interface BalanceService {

	int increaseBalance( Long userId , BigDecimal amount );
	int decreaseBalance( Long userId , BigDecimal amount );
	
}
