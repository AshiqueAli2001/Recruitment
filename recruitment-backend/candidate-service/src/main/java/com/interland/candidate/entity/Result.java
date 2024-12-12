package com.interland.candidate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Result {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Result_ID")
	private Long resultId;
	@ManyToOne
	@JoinColumn(name = "User_ID")
	private User user;
	@ManyToOne
	@JoinColumn(name = "McqQuestion_ID")
	private McqQuestion mcqQuestion;
	@ManyToOne
	@JoinColumn(name = "CodingQuestion_ID")
	private CodingQuestion codingQuestion;
	@Column(name = "Type", length = 15)
	private String type;
	@Column(name = "TestCase1_Status", length = 20)
	private String testCase1Status;
	@Column(name = "TestCase2_Status", length = 20)
	private String testCase2Status;
	@Column(name = "TestCase3_Status", length = 20)
	private String testCase3Status;
	@Column(name = "TestCase4_Status", length = 20)
	private String testCase4Status;
	@Column(name = "TestCase5_Status", length = 20)
	private String testCase5Status;
	@Column(name = "Code", length = 1500)
	private String code;
	@Column(name = "Selected_Option", length = 200)
	private String selectedOption;
	@Column(name = "Attempt_No", length = 3)
	private int attemptNo;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getResultId() {
		return resultId;
	}
	public void setResultId(Long resultId) {
		this.resultId = resultId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public McqQuestion getMcqQuestion() {
		return mcqQuestion;
	}
	public void setMcqQuestion(McqQuestion mcqQuestion) {
		this.mcqQuestion = mcqQuestion;
	}
	public CodingQuestion getCodingQuestion() {
		return codingQuestion;
	}
	public void setCodingQuestion(CodingQuestion codingQuestion) {
		this.codingQuestion = codingQuestion;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public int getAttemptNo() {
		return attemptNo;
	}
	public void setAttemptNo(int attemptNo) {
		this.attemptNo = attemptNo;
	}
	@Override
	public String toString() {
		return "Result [resultId=" + resultId + ", user=" + user + ", mcqQuestion=" + mcqQuestion + ", codingQuestion="
				+ codingQuestion + ", type=" + type + ", testCase1Status=" + testCase1Status + ", testCase2Status="
				+ testCase2Status + ", testCase3Status=" + testCase3Status + ", testCase4Status=" + testCase4Status
				+ ", testCase5Status=" + testCase5Status + ", code=" + code + ", selectedOption=" + selectedOption
				+ ", attemptNo=" + attemptNo + "]";
	}
	
	


	

}
