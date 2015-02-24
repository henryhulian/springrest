package com.springrest.restserver.handler;

import io.netty.util.AttributeKey;

public class HandlerConstant {
	
	public static final String TOKEN="token";
	@SuppressWarnings("deprecation")
	public static final AttributeKey<String> TOKEN_KEY = new AttributeKey<String>(HandlerConstant.TOKEN);
	
	public static final String COMMAND_LOGIN="login";
	public static final String COMMAND_FIND_BALANCE="findBalance";
	
	public static final String PARM_CODE="code";
	public static final String PARM_MSG="msg";
	public static final String PARM_BALANCE="balance";
	
	
}
