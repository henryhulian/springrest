package com.springrest.restserver.interceptor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.springrest.restserver.domain.Session;
import com.springrest.restserver.service.SessionService;
import com.springrest.restserver.util.CookieUtil;
import com.springrest.restserver.util.IpUtil;
import com.springrest.restserver.util.TokenUtil;


@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
	private SessionService sessionService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		if (handler instanceof HandlerMethod) {
		    HandlerMethod method = (HandlerMethod) handler;
		    if (method.getMethod().isAnnotationPresent(RolesAllowed.class)) {
		       
		    	 //detect token
	            // if cann't find token return
	            String token = CookieUtil.getCookie(request, TokenUtil.TOKEN_COOKIE_NMAE);
	            if( token == null ){
	            	 response.sendError(401);
	                 return false;
	            }
	            
	            //find session information
                //if cann't find session return
	            Session session = sessionService.findSessionByToken(token);
                if( session == null ){
                	 response.sendError(401);
	                 return false;
                }
            	
                //check ip
	            if( session.getSessionIp()!=null && !IpUtil.getIp(request).contains((String)session.getSessionIp())){
	            	 response.sendError(403);
	                 return false;
	            }
	             
	           
	            //Verify user access
	            RolesAllowed rolesAnnotation = method.getMethodAnnotation(RolesAllowed.class);
	            Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
	            
		    }
		}
		
		return super.preHandle(request, response, handler);
	}
}
