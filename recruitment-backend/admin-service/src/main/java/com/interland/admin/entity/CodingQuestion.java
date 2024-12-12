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
public class CodingQuestion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Question_ID")
	private Long questionId;

	@Column(name="Difficulty",length = 10)	
	private String difficulty;
	@Column(name="Question",length = 500)	
	private String question;
	
	@Column(name="TestCase1",length = 200)	
	private String testCase1;
	@Column(name="Expected1",length = 200)	
	private String expected1;
	@Column(name="TestCase2",length = 200)	
	private String testCase2;
	@Column(name="Expected2",length = 200)	
	private String expected2;
	@Column(name="TestCase3",length = 200)	
	private String testCase3;
	@Column(name="Expected3",length = 200)	
	private String expected3;
	@Column(name="TestCase4",length = 200)	
	private String testCase4;
	@Column(name="Expected4",length = 200)	
	private String expected4;	
	@Column(name="TestCase5",length = 200)	
	private String testCase5;
	@Column(name="Expected5",length = 200)	
	private String expected5;
	
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

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getTestCase1() {
		return testCase1;
	}

	public void setTestCase1(String testCase1) {
		this.testCase1 = testCase1;
	}

	public String getExpected1() {
		return expected1;
	}

	public void setExpected1(String expected1) {
		this.expected1 = expected1;
	}

	public String getTestCase2() {
		return testCase2;
	}

	public void setTestCase2(String testCase2) {
		this.testCase2 = testCase2;
	}

	public String getExpected2() {
		return expected2;
	}

	public void setExpected2(String expected2) {
		this.expected2 = expected2;
	}

	public String getTestCase3() {
		return testCase3;
	}

	public void setTestCase3(String testCase3) {
		this.testCase3 = testCase3;
	}

	public String getExpected3() {
		return expected3;
	}

	public void setExpected3(String expected3) {
		this.expected3 = expected3;
	}

	public String getTestCase4() {
		return testCase4;
	}

	public void setTestCase4(String testCase4) {
		this.testCase4 = testCase4;
	}

	public String getExpected4() {
		return expected4;
	}

	public void setExpected4(String expected4) {
		this.expected4 = expected4;
	}

	public String getTestCase5() {
		return testCase5;
	}

	public void setTestCase5(String testCase5) {
		this.testCase5 = testCase5;
	}

	public String getExpected5() {
		return expected5;
	}

	public void setExpected5(String expected5) {
		this.expected5 = expected5;
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
		return "CodingQuestion [questionId=" + questionId + ", difficulty=" + difficulty + ", question=" + question
				+ ", testCase1=" + testCase1 + ", expected1=" + expected1 + ", testCase2=" + testCase2 + ", expected2="
				+ expected2 + ", testCase3=" + testCase3 + ", expected3=" + expected3 + ", testCase4=" + testCase4
				+ ", expected4=" + expected4 + ", testCase5=" + testCase5 + ", expected5=" + expected5 + ", user="
				+ user + ", status=" + status + ", createdBy=" + createdBy + ", verifiedBy=" + verifiedBy
				+ ", modifiededBy=" + modifiededBy + ", createdTime=" + createdTime + ", verifiedTime=" + verifiedTime
				+ "]";
	}

	
	
}
