package com.springrest.restserver.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springrest.restserver.ConfigurationBean;
import com.springrest.restserver.domain.Session;
import com.springrest.restserver.domain.User;
import com.springrest.restserver.repository.SessionRepository;
import com.springrest.restserver.repository.UserRepository;
import com.springrest.restserver.util.AESUtil;
import com.springrest.restserver.util.CookieUtil;
import com.springrest.restserver.util.DigestUtil;
import com.springrest.restserver.util.IpUtil;
import com.springrest.restserver.util.TokenUtil;

@Service
@Transactional
public class AuthenticationService {
	
	@Autowired
	private ConfigurationBean configurationBean;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SessionRepository sessionRepository;

	public Integer login( String userName , String password , HttpServletRequest request , HttpServletResponse response){
		
		User user = userRepository.findBySchemaPropertyValue("userName", userName);
		
		if(!DigestUtil.sha256_base64(password).equals(user.getPassword())){
			return -1;
		}
		
		Session session = new Session();
		session.setUserName(userName);
		session.setSessionIp(IpUtil.getIp(request));
		sessionRepository.save(session);
		
		
		try {
			CookieUtil.setCookie(response, TokenUtil.TOKEN_COOKIE_NMAE, 
					AESUtil.encrypt(String.valueOf(session.getId()), configurationBean.getSessionKey()) 
							, request.getContextPath()
							, true, -1);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return 0;
	}
}
