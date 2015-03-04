package com.springrest.restserver.business.service.domain.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springrest.restserver.business.entity.user.Role;
import com.springrest.restserver.business.repository.user.RoleRepository;
import com.springrest.restserver.business.repository.user.UserRepository;
import com.springrest.restserver.business.service.domain.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Role findOrCreateRoleByName( String name ){
		
		Role role = roleRepository.findRoleByName(name);
		if( role == null ){
			role=new Role();
			role.setName(name);
			roleRepository.save(role);
		}
		
		return role;
	}

}
