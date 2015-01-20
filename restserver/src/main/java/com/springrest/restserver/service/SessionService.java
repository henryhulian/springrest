package com.springrest.restserver.service;

import com.springrest.restserver.domain.user.Session;

public interface SessionService {

	public Session findSessionByToken(String token);
}
