package com.springrest.restserver.entity.user;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Session  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3600879727090351823L;
	
	
	@Id
	private String id = UUID.randomUUID().toString();
	
	private String userName;
	
	private Long userId;

	private String sessionIp="";
	
	private String sessionSign="";
	
	private String checkCode;
	
	private Timestamp createTime=new Timestamp(System.currentTimeMillis());
	
	private Timestamp lassAccessTime=new Timestamp(System.currentTimeMillis());
	
	
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

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getLassAccessTime() {
		return lassAccessTime;
	}

	public void setLassAccessTime(Timestamp lassAccessTime) {
		this.lassAccessTime = lassAccessTime;
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Session [id=" + id + ", userName=" + userName + ", userId="
				+ userId + ", sessionIp=" + sessionIp + ", sessionSign="
				+ sessionSign + ", createTime=" + createTime
				+ ", lassAccessTime=" + lassAccessTime + "]";
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	

}
