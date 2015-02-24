package com.springrest.restserver.common;

import java.util.HashMap;
import java.util.Map;

public class Code {
	
	public static int SUCCESS=200;
	
	public static int ERROR_USER_NOT_EXISED=-1000;
	public static int ERROR_PASSWORD_OR_USERNAME_NOT_MATCH=-1001;
	
	private static Map<Integer, String> messages = new HashMap<Integer, String>();
	static{
		messages.put(SUCCESS, "SUCCESS");
		messages.put(ERROR_USER_NOT_EXISED, "ERROR_USER_NOT_EXISED");
		messages.put(ERROR_PASSWORD_OR_USERNAME_NOT_MATCH, "ERROR_PASSWORD_OR_USERNAME_NOT_MATCH");
	}
	
	public static String getMessage( Integer code ){
		return messages.get(code);
	}
}
