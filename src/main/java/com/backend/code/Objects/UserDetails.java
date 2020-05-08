package com.backend.code.Objects;

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
	private int userid;
	private String username;
	private String email;
	private String gender;
	private String phonenumber;
	private String password;
	private String age;
	private String dateofbirth;
	
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
	
	public String getDateofbirth() {
		return dateofbirth;
	}
	

	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	
	
	
	

}
