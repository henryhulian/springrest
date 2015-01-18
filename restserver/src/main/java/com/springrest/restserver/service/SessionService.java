package com.springrest.restserver.service;

import com.springrest.restserver.domain.Session;

public interface SessionService {

	public Session findSessionByToken(String token);
}
