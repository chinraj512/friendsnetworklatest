package com.backend.code.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="userdetails")
public class UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="userid",nullable=false,unique=true)
	private int userid;
	@Column(name="username",nullable=false)
	private String username;
	@Column(name="email",nullable=false,unique=true)
	private String email;
	@Column(name="gender",nullable=false)
	private String gender;
	@Column(name="phonenumber",nullable=false,unique=true)
	private String phonenumber;
	@Column(name="password",nullable=false)
	private String password;
	@Column(name="age",nullable=false)
	private String age;
	@Column(name="dateofbirth",nullable=false)
	private Date dateofbirth;
	
	public int getUserid() {
		return userid;
	}
	
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	public String getUsername() {
		return username;
	}
	

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getGender() {
		return gender;
	}
	

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getPhonenumber() {
		return phonenumber;
	}
	

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	
	public String getPassword() {
		return password;
	}
	

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getAge() {
		return age;
	}
	

	public void setAge(String age) {
		this.age = age;
	}

	public Date getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	

}
