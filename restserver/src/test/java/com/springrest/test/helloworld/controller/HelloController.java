package com.springrest.test.helloworld.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springrest.test.helloworld.dao.SessionRepository;
import com.springrest.test.helloworld.domain.Session;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/hello")
@Api(value="/hello",description="hello world example")
public class HelloController {
	
	@Autowired SessionRepository sessionRepository;
	
	@Transactional
    @RequestMapping(value="/",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="query")
    public List<Session> test() {
    	
    	sessionRepository.save(new Session());
    	
    	Iterator<Session> iterator = sessionRepository.findAll().iterator();
    	
    	List<Session> sessions = new ArrayList<>();
    	
    	while( iterator.hasNext() ){
    		sessions.add(iterator.next());
    	}
    	
    	return sessions;
    }

}
