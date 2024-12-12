package com.interland.candidate.dto;

public class CriteriaDTO {
	
	private Long criteriaId;
	private String title;
	private int totalQuestions;
	
	private int easyMcqQuestions;
	private int mediumMcqQuestions;
	private int hardMcqQuestions;
	
	private int easyCodingQuestions;
	private int mediumCodingQuestions;
	private int hardCodingQuestions;
	
	private double testDuration;
	private String userId;
	
	
	
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "CriteriaDTO [criteriaId=" + criteriaId + ", title=" + title + ", totalQuestions=" + totalQuestions
				+ ", easyMcqQuestions=" + easyMcqQuestions + ", mediumMcqQuestions=" + mediumMcqQuestions
				+ ", hardMcqQuestions=" + hardMcqQuestions + ", easyCodingQuestions=" + easyCodingQuestions
				+ ", mediumCodingQuestions=" + mediumCodingQuestions + ", hardCodingQuestions=" + hardCodingQuestions
				+ ", testDuration=" + testDuration + ", userId=" + userId + "]";
	}
	
	

	

	
}
