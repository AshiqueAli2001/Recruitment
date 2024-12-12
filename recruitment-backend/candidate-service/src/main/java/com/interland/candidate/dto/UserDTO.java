package com.interland.candidate.dto;

import java.util.Arrays;

public class UserDTO {

	private String userId;	
	private String password;	
	private String name;
	private String email;
	private String phone ;
	
	private String college;
	private String department;
	
	private byte[] image;
	
	private int noOfAttempts;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public int getNoOfAttempts() {
		return noOfAttempts;
	}
	public void setNoOfAttempts(int noOfAttempts) {
		this.noOfAttempts = noOfAttempts;
	}
	@Override
	public String toString() {
		return "UserDTO [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email
				+ ", phone=" + phone + ", college=" + college + ", department=" + department + ", image="
				+ Arrays.toString(image) + ", noOfAttempts=" + noOfAttempts + "]";
	}
	
	
	
}
