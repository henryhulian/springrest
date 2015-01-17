package com.springrest.test.helloworld.interceptor;


import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.springrest.test.helloworld.dao.SessionRepository;

@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
	SessionRepository sessionRepository;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		if (handler instanceof HandlerMethod) {
		    HandlerMethod method = (HandlerMethod) handler;
		    if (method.getMethod().isAnnotationPresent(RolesAllowed.class)) {
		       
		    	response.sendError(203,"no user");
		    	return false;
		    }
		}

		System.out.println(sessionRepository.count());
		
		return super.preHandle(request, response, handler);
	}
}
