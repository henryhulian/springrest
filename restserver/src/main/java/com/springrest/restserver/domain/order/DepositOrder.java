package com.springrest.restserver.domain.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.datastax.driver.core.utils.UUIDs;


@Entity
@Cacheable
public class DepositOrder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1985797646890820686L;

	@Id
	private String id = UUIDs.timeBased().toString();
	
	private Long userId;
	
	private String userName;
	
	private BigDecimal amount;
	
	private Integer status=0;
	
	private Date createTime;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
