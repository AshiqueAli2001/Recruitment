package com.interland.admin.entity;

import java.util.Arrays;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class FinalResult {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Final_Result_ID")
	private Long finalResultId;
	
	@ManyToOne
	@JoinColumn(name = "User_ID") 
	private User user;
	
	@Column(name = "Finishing_Time")
	private double finishingTime;
	@Column(name = "Mcq_Score")
	private double mcqScore;
	@Column(name = "Coding_Score")
	private double codingScore;
	@Column(name = "Total_Score")
	private double score;
	@Column(name="Result",length = 10)
	private String result;
	@ManyToOne
	@JoinColumn(name = "Criteria_ID") 
	private Criteria criteria;
	@Column(name = "Pdf",columnDefinition = "LONGBLOB")
	private byte[] pdf;
	
	
	public Long getFinalResultId() {
		return finalResultId;
	}
	public void setFinalResultId(Long finalResultId) {
		this.finalResultId = finalResultId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
		return "FinalResult [finalResultId=" + finalResultId + ", user=" + user + ", finishingTime=" + finishingTime
				+ ", mcqScore=" + mcqScore + ", codingScore=" + codingScore + ", score=" + score + ", result=" + result
				+ ", criteria=" + criteria + ", pdf=" + Arrays.toString(pdf) + "]";
	}
	
	
	
	
}
