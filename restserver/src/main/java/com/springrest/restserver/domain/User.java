package com.springrest.restserver.domain;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@XmlRootElement
@NodeEntity
@ApiModel(value="会员实体")
public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7924386144273140685L;

	@GraphId
	@ApiModelProperty(value="用户id",position=1)
	private Long id;
	
	@Indexed(unique=true)
	@ApiModelProperty(value="用户名",position=2)
	private String username;
	
	@ApiModelProperty(value="密码",position=3)
	private String password;
	
	@ApiModelProperty(value="创建时间",position=4)
	private Date createTime = new Date(System.currentTimeMillis());

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}