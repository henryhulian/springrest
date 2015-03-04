package com.springrest.restserver.business.service.usecase.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springrest.restserver.business.common.Code;
import com.springrest.restserver.business.entity.order.DepositOrder;
import com.springrest.restserver.business.entity.user.Session;
import com.springrest.restserver.business.repository.order.DepositOrderRepository;
import com.springrest.restserver.business.service.domain.BalanceService;
import com.springrest.restserver.business.service.domain.SessionService;
import com.springrest.restserver.business.service.usecase.SocketApiService;
import com.springrest.restserver.server.handler.HandlerConstant;
import com.springrest.restserver.server.protocol.VideoGameDataPackage;

@Service
public class SocketApiServiceImpl implements SocketApiService{
	
	private static final Log log = LogFactory.getLog(SocketApiServiceImpl.class);
	
	@Autowired
	private BalanceService balanceService;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private DepositOrderRepository depositOrderRepository;
	
	@Override
	public VideoGameDataPackage handleCommand(Session session,VideoGameDataPackage msg) {
		msg.setSession(session);
		switch( msg.getCommand()){
			case HandlerConstant.COMMAND_LOGIN:
				break;
			case HandlerConstant.COMMAND_FIND_BALANCE:
				log.debug(HandlerConstant.COMMAND_FIND_BALANCE+"-->"+msg);
				findBalance(session,msg);
				break;
			case HandlerConstant.COMMAND_INSERT_BENCHMARK:
				log.debug(HandlerConstant.COMMAND_INSERT_BENCHMARK+"-->"+msg);
				insertBenchmark(session,msg);
				break;
			default:
				log.error("Unknown command:-->"+msg);
				break;
		}
		return msg;
	}


	private VideoGameDataPackage findBalance( Session session , VideoGameDataPackage msg) {
		msg.getParameters().put(HandlerConstant.PARM_CODE,Code.SUCCESS);
		msg.getParameters().put(HandlerConstant.PARM_MSG, Code.getMessage(Code.SUCCESS));
		msg.getParameters().put(HandlerConstant.PARM_BALANCE, balanceService.findBalance(msg.getSession().getUserId()));
		return msg;
	}
	
	private void insertBenchmark(Session session, VideoGameDataPackage msg) {
		DepositOrder depositOrder = new DepositOrder();
		depositOrder.setUserId(1000L);
		depositOrder.setUserName("BENCHMARK");
		depositOrder.setStatus(0);
		depositOrder.setCreateTime(new Date());
		depositOrder.setAmount(new BigDecimal("100.00"));
		depositOrderRepository.save(depositOrder);
	}

}
