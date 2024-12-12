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
public class McqQuestion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Question_ID")
	private Long questionId;
	@Column(name="Category",length = 15)
	private String category;

	@Column(name="Difficulty",length = 10)	
	private String difficulty;
	@Column(name="Question",length = 1000)	
	private String question;
	@Column(name="Option1",length = 200)	
	private String option1;
	@Column(name="Option2",length = 200)	
	private String option2;
	@Column(name="Option3",length = 200)	
	private String option3;
	@Column(name="Option4",length = 200)	
	private String option4;
	@Column(name="Correct_Answer",length = 200)	
	private String correctAnswer;
	
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public String getOption4() {
		return option4;
	}

	public void setOption4(String option4) {
		this.option4 = option4;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
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
		return "McqQuestion [questionId=" + questionId + ", category=" + category + ", difficulty=" + difficulty
				+ ", question=" + question + ", option1=" + option1 + ", option2=" + option2 + ", option3=" + option3
				+ ", option4=" + option4 + ", correctAnswer=" + correctAnswer + ", user=" + user + ", status=" + status
				+ ", createdBy=" + createdBy + ", verifiedBy=" + verifiedBy + ", modifiededBy=" + modifiededBy
				+ ", createdTime=" + createdTime + ", verifiedTime=" + verifiedTime + "]";
	}
	
	
	
	

}
