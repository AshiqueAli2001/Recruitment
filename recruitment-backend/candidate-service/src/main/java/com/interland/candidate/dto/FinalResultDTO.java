package com.interland.candidate.dto;

import java.util.Arrays;

import com.interland.candidate.entity.Criteria;

public class FinalResultDTO {

	private Long finalResultId;
	private String userId;
	private double finishingTime;
	private double score;
	private String result;
	private Criteria criteria;
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
	public Criteria getCriteria() {
		return criteria;
	}
	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
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
				+ finishingTime + ", score=" + score + ", result=" + result + ", criteria=" + criteria + ", pdf="
				+ Arrays.toString(pdf) + "]";
	}

	

}
