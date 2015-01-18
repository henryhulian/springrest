package com.springrest.restserver.controller;

import java.math.BigDecimal;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springrest.restserver.domain.DepositOrder;
import com.springrest.restserver.domain.User;
import com.springrest.restserver.repository.DepositOrderRepository;
import com.springrest.restserver.service.BalanceService;
import com.springrest.restserver.service.UserService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;


@RestController("/deposit")
@Api(value = "/deposit", description = "存款模块")
public class DepositController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DepositOrderRepository depositOrderRepository;
	
	@Autowired
	private BalanceService balanceService;
	
	@RequestMapping(value = "/depositOrder", method = RequestMethod.POST)
	@ApiOperation(value="创建存款订单")
	@RolesAllowed("user")
	public ResponseEntity<Void> regist(
			@ApiParam( required = true, value = "金额") @RequestParam @NotNull @Min(value = 0) BigDecimal amount,
			HttpServletRequest request) {
		
		
		User user = userService.findCurrentUserByRequest(request);
		
		DepositOrder depositOrder = new DepositOrder();
		depositOrder.setUserId(user.getId());
		depositOrder.setUserName(user.getUserName());
		depositOrder.setStatus(0);
		depositOrder.setAmount(amount);
		
		depositOrderRepository.save(depositOrder);
		
		balanceService.increaseBalance(user.getId(), amount);
		

		return  ResponseEntity.ok().build();
	}

}
