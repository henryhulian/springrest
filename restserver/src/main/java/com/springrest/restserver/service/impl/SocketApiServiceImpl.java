package com.springrest.restserver.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springrest.restserver.common.Code;
import com.springrest.restserver.entity.user.Session;
import com.springrest.restserver.handler.HandlerConstant;
import com.springrest.restserver.protocol.VideoGameDataPackage;
import com.springrest.restserver.service.BalanceService;
import com.springrest.restserver.service.SessionService;
import com.springrest.restserver.service.SocketApiService;

@Service
public class SocketApiServiceImpl implements SocketApiService{
	
	private static final Log log = LogFactory.getLog(SocketApiServiceImpl.class);
	
	@Autowired
	private BalanceService balanceService;
	
	@Autowired
	private SessionService sessionService;
	
	@Override
	public VideoGameDataPackage handleCommand(String token,VideoGameDataPackage msg) {
		switch( msg.getCommand()){
			case HandlerConstant.COMMAND_LOGIN:
				break;
			case HandlerConstant.COMMAND_FIND_BALANCE:
				log.debug(HandlerConstant.COMMAND_FIND_BALANCE+"-->"+msg);
				findBalance(token,msg);
				break;
			default:
				log.error("Unknown command:-->"+msg);
				break;
		}
		return msg;
	}

	private VideoGameDataPackage findBalance( String token , VideoGameDataPackage msg) {
		setSession(token,msg);
		msg.getParameters().put(HandlerConstant.PARM_CODE,Code.SUCCESS);
		msg.getParameters().put(HandlerConstant.PARM_MSG, Code.getMessage(Code.SUCCESS));
		msg.getParameters().put(HandlerConstant.PARM_BALANCE, balanceService.findBalance(msg.getSession().getUserId()));
		return msg;
	}

	private void setSession(String token , VideoGameDataPackage msg ) {
		Session session = sessionService.findSessionByToken(token);
		msg.setSession(session);
	}


}
