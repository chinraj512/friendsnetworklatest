package com.backend.code.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name="message")
public class message {
	@EmbeddedId
	primarykeys key; 
	
	@Column(name="message")
	private String message;
	
	@Column(name="sender")
	private int sender;
	
	@Column(name="createdtime")
	private Date createdtime;

	public primarykeys getKey() {
		return key;
	}

	public void setKey(primarykeys key) {
		this.key = key;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getSender() {
		return sender;
	}

	public void setSender(int sender) {
		this.sender = sender;
	}

	public Date getCreatedtime() {
		return createdtime;
	}

	public void setCreatedtime(Date createdtime) {
		this.createdtime = createdtime;
	}
}
