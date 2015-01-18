package com.springrest.restserver.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springrest.restserver.ConfigurationBean;
import com.springrest.restserver.domain.Session;
import com.springrest.restserver.repository.SessionRepository;
import com.springrest.restserver.repository.UserRepository;
import com.springrest.restserver.service.SessionService;
import com.springrest.restserver.util.AESUtil;

@Service
@Transactional
public class SessionServiceImpl implements SessionService{
	
	private static Log log = LogFactory.getLog(SessionServiceImpl.class);

	@Autowired
	private ConfigurationBean configurationBean;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SessionRepository sessionRepository;

	@Override
	public Session findSessionByToken(String token) {

		Long sessionId = null;
		try {
			sessionId = Long.parseLong(AESUtil.decrypt(token, configurationBean.getSessionKey()));
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
