package com.interland.admin.dto;

import com.interland.admin.utils.Constants;
import com.interland.admin.validation.CustomSize;

import jakarta.validation.constraints.Pattern;

public class CriteriaDTO {
	
	@Pattern(regexp = Constants.PATTERN.ALPHABETSONLY, message="{Pattern.CriteriaDTO.criteriaId}")
	@CustomSize(minKey="minKey.CriteriaDTO.criteriaId", maxKey="maxKey.CriteriaDTO.criteriaId", message="{CustomSize.CriteriaDTO.criteriaId}")
	private Long criteriaId;
	@Pattern(regexp = Constants.PATTERN.ALPHANUMERICONLY, message="{Pattern.CriteriaDTO.title}")
	private String title;
	@Pattern(regexp = Constants.PATTERN.NUMERICSONLY, message="{Pattern.CriteriaDTO.totalQuestions}")
	private int totalQuestions;
	
	@Pattern(regexp = Constants.PATTERN.ALPHANUMERICONLY, message="{Pattern.CriteriaDTO.easyMcqQuestions}")
	private int easyMcqQuestions;
	@Pattern(regexp = Constants.PATTERN.ALPHANUMERICONLY, message="{Pattern.CriteriaDTO.mediumMcqQuestions}")
	private int mediumMcqQuestions;
	@Pattern(regexp = Constants.PATTERN.ALPHANUMERICONLY, message="{Pattern.CriteriaDTO.hardMcqQuestions}")
	private int hardMcqQuestions;
	
	@Pattern(regexp = Constants.PATTERN.ALPHANUMERICONLY, message="{Pattern.CriteriaDTO.easyCodingQuestions}")
	private int easyCodingQuestions;
	@Pattern(regexp = Constants.PATTERN.ALPHANUMERICONLY, message="{Pattern.CriteriaDTO.mediumCodingQuestions}")
	private int mediumCodingQuestions;
	@Pattern(regexp = Constants.PATTERN.ALPHANUMERICONLY, message="{Pattern.CriteriaDTO.hardCodingQuestions}")
	private int hardCodingQuestions;
	
	@Pattern(regexp = Constants.PATTERN.ALPHANUMERICONLY, message="{Pattern.CriteriaDTO.testDuration}")
	private double testDuration;
	@Pattern(regexp = Constants.PATTERN.ALPHANUMERICONLY, message="{Pattern.CriteriaDTO.userId}")
	@CustomSize(minKey="minKey.CriteriaDTO.userId", maxKey="maxKey.CriteriaDTO.userId", message="{CustomSize.CriteriaDTO.userId}")
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
