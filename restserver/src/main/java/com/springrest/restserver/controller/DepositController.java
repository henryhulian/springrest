package com.springrest.restserver.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springrest.restserver.entity.order.DepositOrder;
import com.springrest.restserver.entity.user.User;
import com.springrest.restserver.repository.order.DepositOrderRepository;
import com.springrest.restserver.service.BalanceService;
import com.springrest.restserver.service.DepositOrderService;
import com.springrest.restserver.service.UserService;
import com.springrest.restserver.util.TokenUtil;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;


@RestController("/deposit")
@Api(value = "/deposit", description = "存款模块")
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
			@CookieValue(TokenUtil.TOKEN_COOKIE_NMAE) String token ) {
		
		
		User user = userService.findCurrentUserByToken(token);
		
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
			@CookieValue(TokenUtil.TOKEN_COOKIE_NMAE) String token) {
		
		User user = userService.findCurrentUserByToken(token);
		List<DepositOrder> depositOrders = depositOrderRepository.findDepositOrderByUserId(user.getId());
		
		return  depositOrders;
	}
	
	@RequestMapping(value = "/depositOrder", method = RequestMethod.PUT)
	@ApiOperation(value="审核存款订单")
	public void auditDepositOrder(
			@ApiParam( required = true, value = "会员ID") @RequestParam String userid,
			@ApiParam( required = true, value = "存款订单ID") @RequestParam String depositOrderId,
			HttpServletRequest request) {
		
		depositOrderService.auditDepositOrder(userid,depositOrderId);

	}

}
