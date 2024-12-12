package com.interland.admin.dto;

import com.interland.admin.validation.CustomSize;
import com.interland.admin.utils.Constants;

import jakarta.validation.constraints.Pattern;

public class CodeRequest {
	
	@CustomSize(minKey="minKey.codeRequest.code", maxKey="maxKey.codeRequest.code", message="{CustomSize.codeRequest.code}")
	private String code;
	@Pattern(regexp = Constants.PATTERN.ALPHANUMERICONLY, message="{Pattern.codeRequest.language}")
	private String language;
	@Pattern(regexp = Constants.PATTERN.ALPHANUMERICONLY, message="{Pattern.codeRequest.questionId}")
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
