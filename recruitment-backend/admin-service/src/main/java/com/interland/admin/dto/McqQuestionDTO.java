package com.interland.admin.dto;

import com.interland.admin.utils.Constants;
import com.interland.admin.validation.CustomSize;

import jakarta.validation.constraints.Pattern;

public class McqQuestionDTO {
	
	@Pattern(regexp = Constants.PATTERN.ALPHANUMERIC_WITH_N_SPACE, message="{Pattern.McqQuestionDTO.questionId}")
	@CustomSize(minKey="minKey.McqQuestionDTO.questionId", maxKey="maxKey.McqQuestionDTO.questionId", message="{CustomSize.McqQuestionDTO.questionId}")
	private Long questionId;	
	@Pattern(regexp = Constants.PATTERN.ALPHABETSONLY, message="{Pattern.McqQuestionDTO.category}")
	private String category;	
	@Pattern(regexp = Constants.PATTERN.ALPHABETSONLY, message="{Pattern.McqQuestionDTO.difficulty}")
	private String difficulty;
	
	@CustomSize(minKey="minKey.McqQuestionDTO.question", maxKey="maxKey.McqQuestionDTO.question", message="{CustomSize.McqQuestionDTO.question}")
	private String question;
	@CustomSize(minKey="minKey.McqQuestionDTO.option1", maxKey="maxKey.McqQuestionDTO.option1", message="{CustomSize.McqQuestionDTO.option1}")
	private String option1;	
	@CustomSize(minKey="minKey.McqQuestionDTO.option2", maxKey="maxKey.McqQuestionDTO.option2", message="{CustomSize.McqQuestionDTO.option2}")
	private String option2;	
	@CustomSize(minKey="minKey.McqQuestionDTO.option3", maxKey="maxKey.McqQuestionDTO.option3", message="{CustomSize.McqQuestionDTO.option3}")
	private String option3;	
	@CustomSize(minKey="minKey.McqQuestionDTO.option4", maxKey="maxKey.McqQuestionDTO.option4", message="{CustomSize.McqQuestionDTO.option4}")
	private String option4;	
	@CustomSize(minKey="minKey.McqQuestionDTO.correctAnswer", maxKey="maxKey.McqQuestionDTO.correctAnswer", message="{CustomSize.McqQuestionDTO.correctAnswer}")
	private String correctAnswer;
	
	@Pattern(regexp = Constants.PATTERN.ALPHANUMERICONLY, message="{Pattern.McqQuestionDTO.userId}")
	@CustomSize(minKey="minKey.McqQuestionDTO.userId", maxKey="maxKey.McqQuestionDTO.userId", message="{CustomSize.McqQuestionDTO.userId}")
	private String userId;

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public String getOption4() {
		return option4;
	}

	public void setOption4(String option4) {
		this.option4 = option4;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "McqQuestionDTO [questionId=" + questionId + ", category=" + category + ", difficulty=" + difficulty
				+ ", question=" + question + ", option1=" + option1 + ", option2=" + option2 + ", option3=" + option3
				+ ", option4=" + option4 + ", correctAnswer=" + correctAnswer + ", userId=" + userId + "]";
	}

	


	
}
