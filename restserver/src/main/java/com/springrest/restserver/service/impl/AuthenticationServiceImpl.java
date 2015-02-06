package com.springrest.restserver.service.impl;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springrest.restserver.common.Code;
import com.springrest.restserver.entity.user.Role;
import com.springrest.restserver.entity.user.Session;
import com.springrest.restserver.entity.user.User;
import com.springrest.restserver.entity.user.UserRole;
import com.springrest.restserver.repository.user.RoleRepository;
import com.springrest.restserver.repository.user.SessionRepository;
import com.springrest.restserver.repository.user.UserRepository;
import com.springrest.restserver.repository.user.UserRoleRepository;
import com.springrest.restserver.service.Authenticatior;
import com.springrest.restserver.service.Authorization;
import com.springrest.restserver.service.RoleService;
import com.springrest.restserver.service.SessionService;
import com.springrest.restserver.util.AESUtil;
import com.springrest.restserver.util.CookieUtil;
import com.springrest.restserver.util.DigestUtil;
import com.springrest.restserver.util.IpUtil;
import com.springrest.restserver.util.TokenUtil;

@Service
@Transactional
public class AuthenticationServiceImpl implements Authenticatior,Authorization{
	
	private static Log log = LogFactory.getLog(AuthenticationServiceImpl.class);

	@Autowired
	Environment env;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private SessionRepository sessionRepository;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public int login(String userName, String password,
			HttpServletRequest request, HttpServletResponse response) {

		User user = userRepository.findUserByUserName(userName);

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
					env.getProperty("session.key")));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		sessionRepository.save(session);

		CookieUtil.setCookie(response, TokenUtil.TOKEN_COOKIE_NMAE,
				session.getSessionSign(), request.getContextPath(), true, -1);
		
		log.trace("User:"+userName+" login with ip:"+session.getSessionIp());

		return Code.SUCCESS;
	}

	@Override
	public boolean isUserAllowed(Long userId, Set<String> rolesSet) {
		
		Iterator<String> iterator = rolesSet.iterator();
		while( iterator.hasNext() ){
			
			String name = iterator.next();
			
			Set<String> rolesHas = userRoleRepository.findRolesForUserByUserId(userId);
			if(rolesHas.contains(name)){
				return true;
			}
		}
		
		return false;
	}

	@Override
	public int authorization(Long userId, Set<String> rolesSet) {
		
		Iterator<String> iterator = rolesSet.iterator();
		while( iterator.hasNext() ){
			String name = iterator.next();
			Role role = roleService.findOrCreateRoleByName(name);
			UserRole userRole = new UserRole();
			userRole.setUserId(userId);
			userRole.setRoleName(role.getName());
			userRoleRepository.save(userRole);
		}
		
		return Code.SUCCESS;
	}

}
