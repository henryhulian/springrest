package com.springrest.restserver.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springrest.restserver.domain.DepositOrder;
import com.springrest.restserver.repository.DepositOrderRepository;
import com.springrest.restserver.service.BalanceService;
import com.springrest.restserver.util.TokenUtil;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController("/bechmark")
@Api(value = "/bechmark", description = "压力测试模块")
public class BechmarkController {
	
	private static Log log = LogFactory.getLog(BechmarkController.class);


	@Autowired
	private DepositOrderRepository depositOrderRepository;
	
	@Autowired
	private BalanceService balanceService;
	
	@RequestMapping(value = "/depositOrder", method = RequestMethod.GET)
	@ApiOperation(value="创建存款订单")
	public ResponseEntity<Void> regist(
			HttpServletRequest request) {
		
		DepositOrder depositOrder = new DepositOrder();
		depositOrder.setId(TokenUtil.createRandomToken());
		depositOrder.setUserId(1000L);
		depositOrder.setUserName("BENCHMARK");
		depositOrder.setStatus(0);
		depositOrder.setAmount(new BigDecimal("100.00"));
		
		depositOrderRepository.save(depositOrder);

		return  ResponseEntity.ok().build();
	}
	
	@RequestMapping(value = "/depositOrderAuto", method = RequestMethod.GET)
	@ApiOperation(value="创建存款订单")
	public ResponseEntity<Void> depositAuCreat(  @RequestParam  Integer count) {
		
		long timeStart = System.currentTimeMillis();
		log.debug("insert "+count+" start:"+timeStart);
		for( int i=0 ;i < count ; i++){
			DepositOrder depositOrder = new DepositOrder();
			depositOrder.setId(TokenUtil.createRandomToken());
			depositOrder.setUserId(1000L);
			depositOrder.setUserName("BENCHMARK");
			depositOrder.setStatus(0);
			depositOrder.setAmount(new BigDecimal("100.00"));
			depositOrderRepository.save(depositOrder);
		}
		long timeEnd = System.currentTimeMillis();
		log.debug("insert "+count+" end:"+timeEnd);
		log.debug("cost:"+(timeEnd-timeStart));
		return  ResponseEntity.ok().build();
	}
	
	@RequestMapping(value = "/depositOrderOneTP", method = RequestMethod.GET)
	@ApiOperation(value="创建存款订单")
	@Transactional
	public ResponseEntity<Void> depositAuCreatOneTP(  @RequestParam  Integer count) {
		
		long timeStart = System.currentTimeMillis();
		log.debug("insert "+count+" start:"+timeStart);
		for( int i=0 ;i < count ; i++){
			DepositOrder depositOrder = new DepositOrder();
			depositOrder.setId(TokenUtil.createRandomToken());
			depositOrder.setUserId(1000L);
			depositOrder.setUserName("BENCHMARK");
			depositOrder.setStatus(0);
			depositOrder.setAmount(new BigDecimal("100.00"));
			depositOrderRepository.save(depositOrder);
		}
		long timeEnd = System.currentTimeMillis();
		log.debug("insert "+count+" end:"+timeEnd);
		log.debug("cost:"+(timeEnd-timeStart));
		return  ResponseEntity.ok().build();
	}


	@RequestMapping(value = "/depositOrderCount", method = RequestMethod.GET)
	@ApiOperation(value="存款订单count")
	public Long count() {
		return  depositOrderRepository.count();
	}
	
}
