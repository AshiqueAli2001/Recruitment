package com.interland.admin.dto;

import java.util.Arrays;

import com.interland.admin.entity.Criteria;
import com.interland.admin.utils.Constants;
import com.interland.admin.validation.CustomSize;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;

public class FinalResultDTO {

	@CustomSize(minKey="minKey.FinalResultDTO.finalResultId", maxKey="maxKey.FinalResultDTO.finalResultId", message="{CustomSize.FinalResultDTO.finalResultId}")
	@Pattern(regexp = Constants.PATTERN.ALPHANUMERICONLY, message="{Pattern.FinalResultDTO.finalResultId}")
	private Long finalResultId;
	
	@CustomSize(minKey="minKey.FinalResultDTO.userId", maxKey="maxKey.FinalResultDTO.userId", message="{CustomSize.FinalResultDTO.userId}")
	@Pattern(regexp = Constants.PATTERN.ALPHANUMERICONLY, message="{Pattern.FinalResultDTO.userId}")
	private String userId;
	
	@Pattern(regexp = Constants.PATTERN.NUMERICSONLY, message="{Pattern.FinalResultDTO.finishingTime}")
	private double finishingTime;
	
	@Pattern(regexp = Constants.PATTERN.NUMERICSONLY, message="{Pattern.FinalResultDTO.mcqScore}")
	private double mcqScore;
	
	@Pattern(regexp = Constants.PATTERN.NUMERICSONLY, message="{Pattern.FinalResultDTO.codingScore}")
	private double codingScore;
	
	@Pattern(regexp = Constants.PATTERN.NUMERICSONLY, message="{Pattern.FinalResultDTO.score}")
	private double score;
	
	@Pattern(regexp = Constants.PATTERN.ALPHANUMERICONLY, message="{Pattern.FinalResultDTO.result}")
	private String result;
	
	@CustomSize(minKey="minKey.FinalResultDTO.userId", maxKey="maxKey.FinalResultDTO.userId", message="{CustomSize.FinalResultDTO.userId}")
	@Pattern(regexp = Constants.PATTERN.ALPHANUMERICONLY, message="{Pattern.FinalResultDTO.userId}")
	private Long criteriaId;
	private byte[] pdf;
	
	public Long getFinalResultId() {
		return finalResultId;
	}
	public void setFinalResultId(Long finalResultId) {
		this.finalResultId = finalResultId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public double getFinishingTime() {
		return finishingTime;
	}
	public void setFinishingTime(double finishingTime) {
		this.finishingTime = finishingTime;
	}
	public double getMcqScore() {
		return mcqScore;
	}
	public void setMcqScore(double mcqScore) {
		this.mcqScore = mcqScore;
	}
	public double getCodingScore() {
		return codingScore;
	}
	public void setCodingScore(double codingScore) {
		this.codingScore = codingScore;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Long getCriteriaId() {
		return criteriaId;
	}
	public void setCriteriaId(Long criteriaId) {
		this.criteriaId = criteriaId;
	}
	public byte[] getPdf() {
		return pdf;
	}
	public void setPdf(byte[] pdf) {
		this.pdf = pdf;
	}
	@Override
	public String toString() {
		return "FinalResultDTO [finalResultId=" + finalResultId + ", userId=" + userId + ", finishingTime="
				+ finishingTime + ", mcqScore=" + mcqScore + ", codingScore=" + codingScore + ", score=" + score
				+ ", result=" + result + ", criteriaId=" + criteriaId + ", pdf=" + Arrays.toString(pdf) + "]";
	}
	
	

	

}
