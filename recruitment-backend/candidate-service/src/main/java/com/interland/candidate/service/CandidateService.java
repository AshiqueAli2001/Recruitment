package com.interland.candidate.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.interland.candidate.dto.CodeRequest;
import com.interland.candidate.entity.CodingQuestion;

public interface CandidateService {

	

	ResponseEntity<Object> compileAndExecute(CodeRequest codeRequest);
	
	public List<CodingQuestion> getAllEntities();

	

	


}
