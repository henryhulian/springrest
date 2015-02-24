package com.springrest.restserver.handler;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

public class WebSocketServerHandler extends BinaryWebSocketHandler{
	
	@Override
	protected void handleBinaryMessage(WebSocketSession session,
			BinaryMessage message) throws Exception {
		// TODO Auto-generated method stub
		super.handleBinaryMessage(session, message);
	}

}
