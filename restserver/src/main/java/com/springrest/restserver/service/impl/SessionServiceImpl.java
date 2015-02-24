package com.springrest.restserver.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springrest.restserver.entity.user.Session;
import com.springrest.restserver.repository.user.SessionRepository;
import com.springrest.restserver.repository.user.UserRepository;
import com.springrest.restserver.service.SessionService;
import com.springrest.restserver.util.AESUtil;

@Service
@Transactional
public class SessionServiceImpl implements SessionService{
	
	private static Log log = LogFactory.getLog(SessionServiceImpl.class);
	
	@Autowired
	Environment env;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SessionRepository sessionRepository;

	@Override
	public Session findSessionByToken(String token) {

		Long sessionId = null;
		try {
			
			String sessionInfo = AESUtil.decrypt(token,env.getProperty("session.key"));
			sessionId = Long.parseLong(sessionInfo.split(":")[0]);
			
		} catch (NumberFormatException e) {
			log.warn(e);
		} catch (Exception e) {
			log.error(e);
		}
		
		if(sessionId==null){
			return null;
		}
		
		Session session = sessionRepository.findOne(sessionId);
		

		return session;
	}
}
