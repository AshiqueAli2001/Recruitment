package com.interland.admin.dto;

import com.interland.admin.utils.Constants;
import com.interland.admin.validation.CustomSize;

import jakarta.validation.constraints.Pattern;

public class ResultDTO {
	
	
	@Pattern(regexp = Constants.PATTERN.ALPHANUMERICONLY, message="{Pattern.ResultDTO.resultId}")
	@CustomSize(minKey="minKey.ResultDTO.resultId", maxKey="maxKey.ResultDTO.resultId", message="{CustomSize.ResultDTO.resultId}")
	private Long resultId;
	
	@Pattern(regexp = Constants.PATTERN.ALPHANUMERICONLY, message="{Pattern.ResultDTO.userId}")
	@CustomSize(minKey="minKey.ResultDTO.userId", maxKey="maxKey.ResultDTO.userId", message="{CustomSize.ResultDTO.userId}")
	private String userId;
	
	@Pattern(regexp = Constants.PATTERN.ALPHANUMERICONLY, message="{Pattern.ResultDTO.questionId}")
	@CustomSize(minKey="minKey.ResultDTO.questionId", maxKey="maxKey.ResultDTO.questionId", message="{CustomSize.ResultDTO.questionId}")
	private Long questionId;

	private String testCase1Status;
	private String testCase2Status;
	private String testCase3Status;
	private String testCase4Status;
	private String testCase5Status;
	
	private String selectedOption;
	private String code;

	public Long getResultId() {
		return resultId;
	}

	public void setResultId(Long resultId) {
		this.resultId = resultId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getTestCase1Status() {
		return testCase1Status;
	}

	public void setTestCase1Status(String testCase1Status) {
		this.testCase1Status = testCase1Status;
	}

	public String getTestCase2Status() {
		return testCase2Status;
	}

	public void setTestCase2Status(String testCase2Status) {
		this.testCase2Status = testCase2Status;
	}

	public String getTestCase3Status() {
		return testCase3Status;
	}

	public void setTestCase3Status(String testCase3Status) {
		this.testCase3Status = testCase3Status;
	}

	public String getTestCase4Status() {
		return testCase4Status;
	}

	public void setTestCase4Status(String testCase4Status) {
		this.testCase4Status = testCase4Status;
	}

	public String getTestCase5Status() {
		return testCase5Status;
	}

	public void setTestCase5Status(String testCase5Status) {
		this.testCase5Status = testCase5Status;
	}

	public String getSelectedOption() {
		return selectedOption;
	}

	public void setSelectedOption(String selectedOption) {
		this.selectedOption = selectedOption;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "ResultDTO [resultId=" + resultId + ", userId=" + userId + ", questionId=" + questionId
				+ ", testCase1Status=" + testCase1Status + ", testCase2Status=" + testCase2Status + ", testCase3Status="
				+ testCase3Status + ", testCase4Status=" + testCase4Status + ", testCase5Status=" + testCase5Status
				+ ", selectedOption=" + selectedOption + ", code=" + code + "]";
	}

	
	
	
	
	
	
	

}
