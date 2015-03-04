package com.springrest.restserver.business.service.domain.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.springrest.restserver.business.entity.user.Session;
import com.springrest.restserver.business.repository.user.SessionRepository;
import com.springrest.restserver.business.repository.user.UserRepository;
import com.springrest.restserver.business.service.domain.SessionService;
import com.springrest.restserver.business.util.AESUtil;

@Service
@Transactional
public class SessionServiceImpl implements SessionService{
	
	private static final Log log = LogFactory.getLog(SessionServiceImpl.class);
	
	@Autowired
	Environment env;
	
	@Autowired
	private CacheManager cacheManager;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SessionRepository sessionRepository;
	
	public Session createUserSession( String userName , Long userId , String userIp) {
		
		Session session = new Session();
		session.setUserName(userName);
		session.setUserId(userId);
		session.setSessionIp(userIp);
		try {
			session.setSessionSign(AESUtil.encrypt(
					String.valueOf(session.getId())+":"+System.currentTimeMillis(),
					env.getProperty("session.key")));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		sessionRepository.save(session);

		return session;
	}
	

	@Override
	public Session findSessionByToken(String token) {
		String sessionId = null;
		try {
			
			String sessionInfo = AESUtil.decrypt(token,env.getProperty("session.key"));
			sessionId = sessionInfo.split(":")[0];
			
		} catch (Exception e) {
			log.error(e);
		}
		
		if(StringUtils.isEmpty(sessionId)){
			return null;
		}
		
		Session session = sessionRepository.findOne(sessionId);
		

		return session;
	}
	
	@Override
	public Session createGuestSession(String userIp) {
		
		Session session = new Session();
		session.setUserName("Guest");
		session.setUserId(0L);
		session.setSessionIp(userIp);
		try {
			session.setSessionSign(AESUtil.encrypt(
					String.valueOf(session.getId())+":"+System.currentTimeMillis(),
					env.getProperty("session.key")));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		cacheManager.getCache("session").put(session.getId(), session);
		
		return session;
	}
	
	@Override
	public Session updateGuestSession(Session session) {
		
		cacheManager.getCache("session").put(session.getId(), session );
		
		return session;
	}

	@Override
	public Session findGuestSessionByToken(String token) {
		String sessionId = null;
		try {
			
			String sessionInfo = AESUtil.decrypt(token,env.getProperty("session.key"));
			sessionId = sessionInfo.split(":")[0];
			
		} catch (Exception e) {
			log.error(e);
		}
		
		if(StringUtils.isEmpty(sessionId)){
			return null;
		}
		
		ValueWrapper valueWrapper = cacheManager.getCache("session").get(sessionId);
		
		Session session = null;
		if( valueWrapper!= null ){
			session = (Session)cacheManager.getCache("session").get(sessionId).get();
		}
		
		return session;
	}


	@Override
	public void logoutByToken(String token) {
		Session session = findSessionByToken(token);
		if( session != null ){
			session.setStatus(0);
			sessionRepository.save(session);
		}
	}


	@Override
	public void logoutSessions(Long userId) {
		sessionRepository.updateSessionStatusByUserId(Session.STATUS_LOGOUT, userId);
	}


	@Override
	public Session findSessionByRequest(HttpServletRequest request) {
		Object session = request.getAttribute(Session.NAME);
		if(session == null){
			return null;
		}
		return (Session)session;
	}

}
