package com.springrest.restserver.domain;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Session  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3600879727090351823L;
	
	private Long id;
	
	private String userName;
	
	private Long userId;

	private String sessionIp="";
	
	private String sessionSign="";
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime=new Date(System.currentTimeMillis());
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date lassAccessTime=new Date(System.currentTimeMillis());
	
	
	public String getSessionIp() {
		return sessionIp;
	}

	public void setSessionIp(String sessionIp) {
		this.sessionIp = sessionIp;
	}

	public String getSessionSign() {
		return sessionSign;
	}

	public void setSessionSign(String sessionSign) {
		this.sessionSign = sessionSign;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLassAccessTime() {
		return lassAccessTime;
	}

	public void setLassAccessTime(Date lassAccessTime) {
		this.lassAccessTime = lassAccessTime;
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

	@Override
	public String toString() {
		return "Session [id=" + id + ", userName=" + userName + ", sessionIp=" + sessionIp + ", sessionSign="
				+ sessionSign + ", createTime=" + createTime
				+ ", lassAccessTime=" + lassAccessTime + "]";
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
