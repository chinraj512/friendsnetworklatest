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
	private int user_id;
	private String userName;
	private String emailId;
	private String gender;
	private String phoneNumber;
	private String password;
	private String age;
	private String dateOfBirth;
	
	   @Column(name = "genders", nullable = false)
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	   @Column(name = "dateofbirth", nullable = false)
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getUser_id() {
		return user_id;
	}
	
	public void setUserId(int user_id) {
		this.user_id = user_id;
	}
	@Column(name = "username", nullable = false)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
   @Column(name = "email", nullable = false)
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	@Column(name = "sex", nullable = false)
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	 @Column(name = "phonenumber", nullable = false)
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@Column(name = "password", nullable = false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
