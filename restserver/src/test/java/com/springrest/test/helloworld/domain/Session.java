package com.springrest.test.helloworld.domain;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springrest.test.helloworld.util.DateAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@NodeEntity
public class Session  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3600879727090351823L;
	
	
	@GraphId
	private Long id;
	
	@XmlElement(nillable=false)
	private String userName;

	@XmlElement(nillable=false)
	private String sessionId="";
	
	@XmlElement(nillable=false)
	private String sessionIp="";
	
	@XmlElement(nillable=false)
	private String sessionSign="";
	
	@XmlElement(nillable=false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime=new Date(System.currentTimeMillis());
	
	@XmlElement(nillable=false)
	@XmlJavaTypeAdapter(DateAdapter.class)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date lassAccessTime=new Date(System.currentTimeMillis());
	

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

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
		return "Session [id=" + id + ", userName=" + userName + ", sessionId="
				+ sessionId + ", sessionIp=" + sessionIp + ", sessionSign="
				+ sessionSign + ", createTime=" + createTime
				+ ", lassAccessTime=" + lassAccessTime + "]";
	}

}
