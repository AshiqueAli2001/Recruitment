package com.interland.candidate.entity;


import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class User implements UserDetails{

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "User_ID",length = 10)
	private String userId;
	
	@Column(name = "Password",length = 100)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "Role",length = 10)
	private Role role;
	
	@Column(name = "Name" ,length = 25)
	private String name;
	@Column(name = "Email",length = 35)
	private String email;
	@Column(name = "Phone",length = 15)
	private String phone ;	
	@Column(name = "College",length = 25)
	private String college;
	@Column(name = "Department",length = 25)
	private String department;
	@Column(name = "Image",columnDefinition = "LONGBLOB") 
	private byte[] image;
	@Column(name = "No_Of_Attempts",length=3) 
	private int noOfAttempts;
	@Column(name = "Highest_Score",length=3) 
	private double highestSCore;
	
	@Column(name = "Status",length=20)
	private String status;
	
	@Column(name = "created_by",length=15)
	private String createdBy;
	
	@Column(name = "verified_by",length=15)
	private String verifiedBy;

	@Column(name = "modified_by",length=15)
	private String modifiededBy;
	
	@Column(name = "created_time")
	private Date createdTime;
	
	@Column(name = "verified_time")
	private Date verifiedTime;

	
	
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
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
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
	public double getHighestSCore() {
		return highestSCore;
	}
	public void setHighestSCore(double highestSCore) {
		this.highestSCore = highestSCore;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getVerifiedBy() {
		return verifiedBy;
	}
	public void setVerifiedBy(String verifiedBy) {
		this.verifiedBy = verifiedBy;
	}
	public String getModifiededBy() {
		return modifiededBy;
	}
	public void setModifiededBy(String modifiededBy) {
		this.modifiededBy = modifiededBy;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getVerifiedTime() {
		return verifiedTime;
	}
	public void setVerifiedTime(Date verifiedTime) {
		this.verifiedTime = verifiedTime;
	}
	
	
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", role=" + role + ", name=" + name + ", email="
				+ email + ", phone=" + phone + ", college=" + college + ", department=" + department + ", image="
				+ Arrays.toString(image) + ", noOfAttempts=" + noOfAttempts + ", highestSCore=" + highestSCore
				+ ", status=" + status + ", createdBy=" + createdBy + ", verifiedBy=" + verifiedBy + ", modifiededBy="
				+ modifiededBy + ", createdTime=" + createdTime + ", verifiedTime=" + verifiedTime + "]";
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}
	@Override
	public String getUsername() {
		return userId;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}


	
}
