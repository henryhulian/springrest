package com.springrest.restserver.service;

import com.springrest.restserver.protocol.VideoGameDataPackage;

public interface SocketApiService {
	VideoGameDataPackage handleCommand( String token , VideoGameDataPackage msg );
}
