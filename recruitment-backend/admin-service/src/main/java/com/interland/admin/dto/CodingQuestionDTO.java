package com.interland.admin.dto;

import com.interland.admin.utils.Constants;
import com.interland.admin.validation.CustomSize;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class CodingQuestionDTO {
	
	@Pattern(regexp = Constants.PATTERN.ALPHANUMERICONLY, message="{Pattern.CodingQuestionDTO.questionId}")
	@CustomSize(minKey="minKey.CodingQuestionDTO.questionId", maxKey="maxKey.CodingQuestionDTO.questionId", message="{CustomSize.CodingQuestionDTO.questionId}")
	private Long questionId;
	@Pattern(regexp = Constants.PATTERN.ALPHABETSONLY, message="{Pattern.CodingQuestionDTO.difficulty}")
	private String difficulty;
	
	@NotNull(message = "{NotNull.CodingQuestionDTO.question}")
	private String question;
	@NotNull(message = "{NotNull.CodingQuestionDTO.testCase1}")
	private String testCase1;
	@NotNull(message = "{NotNull.CodingQuestionDTO.expected1}")
	private String expected1;	
	@NotNull(message = "{NotNull.CodingQuestionDTO.testCase2}")
	private String testCase2;	
	@NotNull(message = "{NotNull.CodingQuestionDTO.expected2}")
	private String expected2;	
	@NotNull(message = "{NotNull.CodingQuestionDTO.testCase3}")
	private String testCase3;	
	@NotNull(message = "{NotNull.CodingQuestionDTO.expected3}")
	private String expected3;
	@NotNull(message = "{NotNull.CodingQuestionDTO.testCase4}")
	private String testCase4;	
	@NotNull(message = "{NotNull.CodingQuestionDTO.expected4}")
	private String expected4;		
	@NotNull(message = "{NotNull.CodingQuestionDTO.testCase5}")
	private String testCase5;	
	@NotNull(message = "{NotNull.CodingQuestionDTO.expected5}")
	private String expected5;
	@Pattern(regexp = Constants.PATTERN.ALPHANUMERICONLY, message="{Pattern.CodingQuestionDTO.userId}")
	@CustomSize(minKey="minKey.CodingQuestionDTO.userId", maxKey="maxKey.CodingQuestionDTO.userId", message="{CustomSize.CodingQuestionDTO.userId}")
	private String userId;
	
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "CodingQuestionDTO [questionId=" + questionId + ", difficulty=" + difficulty + ", question=" + question
				+ ", testCase1=" + testCase1 + ", expected1=" + expected1 + ", testCase2=" + testCase2 + ", expected2="
				+ expected2 + ", testCase3=" + testCase3 + ", expected3=" + expected3 + ", testCase4=" + testCase4
				+ ", expected4=" + expected4 + ", testCase5=" + testCase5 + ", expected5=" + expected5 + ", userId="
				+ userId + "]";
	}
	
	

}
