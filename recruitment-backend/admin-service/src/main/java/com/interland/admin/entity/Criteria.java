package com.interland.admin.entity;



import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Criteria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Criteria_ID")
	private Long criteriaId;
	@Column(name="Title",length = 15)
	private String title;
	
	@Column(name = "Total_Questions")
	private int totalQuestions;
	
	@Column(name = "Easy_Mcq_Questions")
	private int easyMcqQuestions;
	@Column(name = "Medium_Mcq_Questions")
	private int mediumMcqQuestions;
	@Column(name = "Hard_Mcq_Questions")
	private int hardMcqQuestions;
	
	@Column(name = "Easy_Coding_Questions")
	private int easyCodingQuestions;
	@Column(name = "Medium_Coding_Questions")
	private int mediumCodingQuestions;
	@Column(name = "Hard_Coding_Questions")
	private int hardCodingQuestions;
	
	@Column(name = "Test_Duration")
	private double testDuration;
	
	@ManyToOne
	@JoinColumn(name = "User_ID",nullable = false) 
	private User user;
	
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
	
	
//	@OneToMany(mappedBy = "criteria")
//    private List<Candidate> candidates;
//
// @PreRemove
//    private void preRemove() {
//        for (Candidate candidate : candidates) {
//            candidate.setCriteria(null);
//        }
//    }



	public Long getCriteriaId() {
		return criteriaId;
	}

	public void setCriteriaId(Long criteriaId) {
		this.criteriaId = criteriaId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getTotalQuestions() {
		return totalQuestions;
	}

	public void setTotalQuestions(int totalQuestions) {
		this.totalQuestions = totalQuestions;
	}

	public int getEasyMcqQuestions() {
		return easyMcqQuestions;
	}

	public void setEasyMcqQuestions(int easyMcqQuestions) {
		this.easyMcqQuestions = easyMcqQuestions;
	}

	public int getMediumMcqQuestions() {
		return mediumMcqQuestions;
	}

	public void setMediumMcqQuestions(int mediumMcqQuestions) {
		this.mediumMcqQuestions = mediumMcqQuestions;
	}

	public int getHardMcqQuestions() {
		return hardMcqQuestions;
	}

	public void setHardMcqQuestions(int hardMcqQuestions) {
		this.hardMcqQuestions = hardMcqQuestions;
	}

	public int getEasyCodingQuestions() {
		return easyCodingQuestions;
	}

	public void setEasyCodingQuestions(int easyCodingQuestions) {
		this.easyCodingQuestions = easyCodingQuestions;
	}

	public int getMediumCodingQuestions() {
		return mediumCodingQuestions;
	}

	public void setMediumCodingQuestions(int mediumCodingQuestions) {
		this.mediumCodingQuestions = mediumCodingQuestions;
	}

	public int getHardCodingQuestions() {
		return hardCodingQuestions;
	}

	public void setHardCodingQuestions(int hardCodingQuestions) {
		this.hardCodingQuestions = hardCodingQuestions;
	}

	public double getTestDuration() {
		return testDuration;
	}

	public void setTestDuration(double testDuration) {
		this.testDuration = testDuration;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
		return "Criteria [criteriaId=" + criteriaId + ", title=" + title + ", totalQuestions=" + totalQuestions
				+ ", easyMcqQuestions=" + easyMcqQuestions + ", mediumMcqQuestions=" + mediumMcqQuestions
				+ ", hardMcqQuestions=" + hardMcqQuestions + ", easyCodingQuestions=" + easyCodingQuestions
				+ ", mediumCodingQuestions=" + mediumCodingQuestions + ", hardCodingQuestions=" + hardCodingQuestions
				+ ", testDuration=" + testDuration + ", user=" + user + ", status=" + status + ", createdBy="
				+ createdBy + ", verifiedBy=" + verifiedBy + ", modifiededBy=" + modifiededBy + ", createdTime="
				+ createdTime + ", verifiedTime=" + verifiedTime + "]";
	}
	
	
	


	

	
	
	

	
}
