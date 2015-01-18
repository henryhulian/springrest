package com.springrest.restserver.domain;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

import com.wordnik.swagger.annotations.ApiModel;

@NodeEntity
@ApiModel(value="会员实体")
public class Role {
	
	@GraphId
	private Long id;
	
	private String name;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
