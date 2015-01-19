package com.springrest.restserver.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springrest.restserver.domain.Role;
import com.springrest.restserver.repository.RoleRepository;
import com.springrest.restserver.repository.UserRepository;
import com.springrest.restserver.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Role findOrCreateRoleByName( String name ){
		
		Role role = roleRepository.findOne(1l);
		if( role == null ){
			role=new Role();
			role.setName(name);
			roleRepository.save(role);
		}
		
		return role;
	}

}
