package com.interland.admin.dto;

import java.util.Arrays;

import com.interland.admin.utils.Constants;
import com.interland.admin.validation.CustomSize;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class UserDTO {

	@Pattern(regexp = Constants.PATTERN.ALPHANUMERICONLY, message="{Pattern.UserDTO.userId}")
	@CustomSize(minKey="minKey.UserDTO.userId", maxKey="maxKey.UserDTO.userId", message="{CustomSize.UserDTO.userId}")
	private String userId;	
	@CustomSize(minKey="minKey.UserDTO.password", maxKey="maxKey.UserDTO.password", message="{CustomSize.UserDTO.password}")
	private String password;	
	@Pattern(regexp = Constants.PATTERN.ALPHABETSONLY, message="{Pattern.UserDTO.name}")
	private String name;
	@Email(message = "Email.UserDTO.email")
	private String email;
	@Pattern(regexp = Constants.PATTERN.NUMERICSONLY , message="{Pattern.UserDTO.phone}")
	private String phone ;
	
	@Pattern(regexp = Constants.PATTERN.ALPHABETSONLY, message="{Pattern.UserDTO.college}")
	private String college;
	@Pattern(regexp = Constants.PATTERN.ALPHABETSONLY, message="{Pattern.UserDTO.department}")
	private String department;
	
	private byte[] image;
	
	@Pattern(regexp = Constants.PATTERN.NUMERICSONLY, message="{Pattern.UserDTO.noOfAttempts}")
	private int noOfAttempts;
	private String status;
	
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "UserDTO [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email
				+ ", phone=" + phone + ", college=" + college + ", department=" + department + ", image="
				+ Arrays.toString(image) + ", noOfAttempts=" + noOfAttempts + ", status=" + status + "]";
	}
	
	
	
}
