package com.springrest.restserver.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Authenticatior {
	int login(String userName, String password,HttpServletRequest request, HttpServletResponse response);
}
