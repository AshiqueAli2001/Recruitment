package com.interland.candidate.dto;

public class CodeRequest {
	
	private String code;
	private String language;
	private Long questionId;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	@Override
	public String toString() {
		return "CodeRequest [code=" + code + ", language=" + language + ", questionId=" + questionId + "]";
	}
	
	
}
