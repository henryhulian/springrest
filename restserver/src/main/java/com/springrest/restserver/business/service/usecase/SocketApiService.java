package com.springrest.restserver.business.service.usecase;

import com.springrest.restserver.business.entity.user.Session;
import com.springrest.restserver.server.protocol.VideoGameDataPackage;

public interface SocketApiService {
	VideoGameDataPackage handleCommand( Session session , VideoGameDataPackage msg );
}
