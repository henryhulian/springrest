package com.springrest.restserver.business.service.domain;

import javax.servlet.http.HttpServletRequest;

import com.springrest.restserver.business.entity.user.Session;


public interface SessionService {
	
	/**
	 * 创建Session
	 * @param userName
	 * @param userId
	 * @param userIp
	 * @return
	 */
	public Session createUserSession( String userName , Long userId , String userIp);
	
	public Session findSessionByRequest(HttpServletRequest request);
	
	public Session findSessionByToken(String token);
	
	public Session createGuestSession(String userIp);
	
	public Session updateGuestSession(Session session);
	
	public Session findGuestSessionByToken(String token);
	
	/**
	 * 根据Token登出
	 * @param token
	 */
	public void logoutByToken(String token);
	
	/**
	 * 清除该会员已经登录的Session
	 * @param userId
	 */
	public void logoutSessions( Long userId );
	
}
