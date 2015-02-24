package com.springrest.restserver.protocol;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springrest.restserver.entity.user.Session;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author fb421
 *
 */

@ApiModel
public class VideoGameDataPackage {
	
	@ApiModelProperty(required=true,position=1)
	private String command;
	
	@JsonIgnore
	private Session session;
	
	@ApiModelProperty(required=true,position=2)
	private Map<String, Object> parameters = new HashMap<String, Object>();

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public String toString() {
		return "VideoGameDataPackage [command=" + command + ", session="
				+ session + ", parameters=" + parameters + "]";
	}
	
}
