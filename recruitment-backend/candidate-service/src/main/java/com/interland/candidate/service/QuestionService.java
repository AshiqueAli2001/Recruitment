package com.interland.candidate.service;

import java.security.Principal;
import java.util.List;

import com.interland.candidate.dto.CodingQuestionDTO;
import com.interland.candidate.dto.McqQuestionDTO;
import com.interland.candidate.exception.RecordNotFoundException;

public interface QuestionService {



	List<McqQuestionDTO> generateMcqQuestionsByCriteriaId(Long criteriaId, Principal principal) throws RecordNotFoundException;

	List<CodingQuestionDTO> generateCodingQuestionsByCriteriaId(Long criteriaId) throws RecordNotFoundException;

	
	
	
}
