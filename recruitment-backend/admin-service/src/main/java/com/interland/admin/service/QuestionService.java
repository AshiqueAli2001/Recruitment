package com.interland.admin.service;

import java.security.Principal;

import org.json.simple.JSONObject;

import com.interland.admin.dto.CodingQuestionDTO;
import com.interland.admin.dto.McqQuestionDTO;
import com.interland.admin.dto.ServiceResponse;

public interface QuestionService {


	ServiceResponse addMcqQuestion(McqQuestionDTO dto, Principal principal);

	ServiceResponse updateMcqQuestion(McqQuestionDTO dto, Principal principal);

	ServiceResponse deleteMcqQuestion(Long questionId, Principal principal);

	ServiceResponse addCodingQuestion(CodingQuestionDTO dto, Principal principal);

	ServiceResponse updateCodingQuestion(CodingQuestionDTO dto, Principal principal);

	ServiceResponse deleteCodingQuestion(Long questionId, Principal principal);

	JSONObject searchMcqQuestion(String searchParam, int parseInt, int parseInt2);
	
	JSONObject searchCodingQuestion(String searchParam, int parseInt, int parseInt2);

	
	
	
}
