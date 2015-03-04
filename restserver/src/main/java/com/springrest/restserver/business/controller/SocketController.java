package com.springrest.restserver.business.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.springrest.restserver.business.service.domain.SessionService;
import com.springrest.restserver.business.service.usecase.SocketApiService;
import com.springrest.restserver.server.protocol.VideoGameDataPackage;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/socket")
@Api(value = "/socket", description = "Socket接口")
public class SocketController {
	
	@Autowired
	private SocketApiService socketApiService;
	
	@Autowired
	private SessionService sessionService;

	@RequestMapping(method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Flash AMF3 命令接口",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public VideoGameDataPackage findBalance(
			@ApiParam( required = true, value = "数据包")  @RequestBody VideoGameDataPackage msg ,
			@ApiIgnore HttpServletRequest request
												){
		socketApiService.handleCommand(sessionService.findSessionByRequest(request),msg);
		
		return msg;
		
	}

}
