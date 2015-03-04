package com.springrest.restserver.business.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.springrest.restserver.business.entity.order.DepositOrder;
import com.springrest.restserver.business.entity.user.User;
import com.springrest.restserver.business.repository.order.DepositOrderRepository;
import com.springrest.restserver.business.service.domain.BalanceService;
import com.springrest.restserver.business.service.domain.UserService;
import com.springrest.restserver.business.service.usecase.DepositOrderService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;


@RestController
@RequestMapping("/deposit")
@Api(value = "/deposit", description = "存款")
public class DepositController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DepositOrderService depositOrderService;
	
	@Autowired
	private DepositOrderRepository depositOrderRepository;
	
	@Autowired
	private BalanceService balanceService;
	
	@RequestMapping(value = "/depositOrder", method = RequestMethod.POST)
	@ApiOperation(value="创建存款订单")
	@RolesAllowed("user")
	public ResponseEntity<Void> regist(
			@ApiParam( required = true, value = "金额") @RequestParam @NotNull @Min(value = 0) BigDecimal amount,
			@ApiIgnore HttpServletRequest request ) {
		
		
		User user = userService.findCurrentUserByRequest(request);
		
		DepositOrder depositOrder = new DepositOrder();
		depositOrder.setUserId(user.getId());
		depositOrder.setUserName(user.getUserName());
		depositOrder.setStatus(0);
		depositOrder.setAmount(amount);
		depositOrder.setCreateTime(new Date());
		
		depositOrderRepository.save(depositOrder);

		return  ResponseEntity.ok().build();
	}
	
	@RequestMapping(value = "/depositOrder", method = RequestMethod.GET)
	@ApiOperation(value="查询存款订单")
	@RolesAllowed("user")
	public List<DepositOrder> findDepositOrderByUserId(
			@ApiIgnore HttpServletRequest request) {
		
		User user = userService.findCurrentUserByRequest(request);
		List<DepositOrder> depositOrders = depositOrderRepository.findDepositOrderByUserId(user.getId());
		
		return  depositOrders;
	}
	
	@RequestMapping(value = "/depositOrder", method = RequestMethod.PUT)
	@ApiOperation(value="审核存款订单")
	public void auditDepositOrder(
			@ApiParam( required = true, value = "存款订单ID") @RequestParam String depositOrderId,
			HttpServletRequest request) {
		
		depositOrderService.auditDepositOrder(depositOrderId);

	}

}
