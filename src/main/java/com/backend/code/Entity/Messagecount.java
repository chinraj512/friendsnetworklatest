package com.backend.code.Entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Messagecount")
public class Messagecount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int countid;
	
	@Column(name="user1")
	int user1;
	
	@Column(name="user2")
	int user2;
	
	@Column (name="messagecount")
	int messagecount;

	
	public int getUser1() {
		return user1;
	}

	public void setUser1(int user1) {
		this.user1 = user1;
	}

	public int getUser2() {
		return user2;
	}

	public void setUser2(int user2) {
		this.user2 = user2;
	}

	public int getMessagecount() {
		return messagecount;
	}

	public void setMessagecount(int messagecount) {
		this.messagecount = messagecount;
	}
	
}
