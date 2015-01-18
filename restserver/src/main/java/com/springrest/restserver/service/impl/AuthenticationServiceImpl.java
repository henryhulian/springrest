package com.springrest.restserver.service.impl;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springrest.restserver.ConfigurationBean;
import com.springrest.restserver.common.Code;
import com.springrest.restserver.domain.Session;
import com.springrest.restserver.domain.User;
import com.springrest.restserver.repository.SessionRepository;
import com.springrest.restserver.repository.UserRepository;
import com.springrest.restserver.service.Authenticatior;
import com.springrest.restserver.service.Authorization;
import com.springrest.restserver.util.AESUtil;
import com.springrest.restserver.util.CookieUtil;
import com.springrest.restserver.util.DigestUtil;
import com.springrest.restserver.util.IpUtil;
import com.springrest.restserver.util.TokenUtil;

@Service
@Transactional
public class AuthenticationServiceImpl implements Authenticatior,Authorization{

	@Autowired
	private ConfigurationBean configurationBean;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SessionRepository sessionRepository;

	@Override
	public int login(String userName, String password,
			HttpServletRequest request, HttpServletResponse response) {

		User user = userRepository.findBySchemaPropertyValue("userName",
				userName);

		if (!DigestUtil.sha256_base64(password).equals(user.getPassword())) {
			return Code.ERROR_PASSWORD_OR_USERNAME_NOT_MATCH;
		}

		Session session = new Session();
		session.setUserName(userName);
		session.setUserId(user.getId());
		session.setSessionIp(IpUtil.getIp(request));
		session=sessionRepository.save(session);

		try {
			session.setSessionSign(AESUtil.encrypt(
					String.valueOf(session.getId()),
					configurationBean.getSessionKey()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		sessionRepository.save(session);

		CookieUtil.setCookie(response, TokenUtil.TOKEN_COOKIE_NMAE,
				session.getSessionSign(), request.getContextPath(), true, -1);

		return Code.SUCCESS;
	}

	@Override
	public boolean isUserAllowed(String username, Set<String> rolesSet) {
		
		return true;
	}

}
