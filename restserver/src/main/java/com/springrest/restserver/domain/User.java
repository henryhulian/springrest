package com.springrest.restserver.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value="会员实体")
public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7924386144273140685L;

	@ApiModelProperty(value="用户id",position=1)
	private Long id;
	
	@ApiModelProperty(value="用户名",position=2)
	private String userName;
	
	@ApiModelProperty(value="密码",position=3)
	private String password;
	
	@ApiModelProperty(value="创建时间",position=4)
	private Date createTime = new Date(System.currentTimeMillis());
	
	private BigDecimal balance;

	private Set<Role> roles;
	

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
}