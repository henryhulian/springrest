package com.springrest.test.helloworld.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springrest.test.helloworld.dao.SessionRepository;
import com.springrest.test.helloworld.domain.Session;

@RestController
@RequestMapping("/hello")
public class HelloController {
	
	@Autowired SessionRepository sessionRepository;
	
	@Transactional
    @RequestMapping(value="/")
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
