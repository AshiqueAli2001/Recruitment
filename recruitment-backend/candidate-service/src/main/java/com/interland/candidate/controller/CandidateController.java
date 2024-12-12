package com.interland.candidate.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interland.candidate.dto.CodeRequest;
import com.interland.candidate.dto.CodingQuestionDTO;
import com.interland.candidate.dto.McqQuestionDTO;
import com.interland.candidate.dto.ResultDTO;
import com.interland.candidate.dto.ServiceResponse;
import com.interland.candidate.entity.CodingQuestion;
import com.interland.candidate.exception.RecordNotFoundException;
import com.interland.candidate.service.CandidateService;
import com.interland.candidate.service.CriteriaService;
import com.interland.candidate.service.QuestionService;
import com.interland.candidate.service.ResultService;

@RestController
@RequestMapping("/candidate")
//@CrossOrigin(origins = "http://localhost:4200")
public class CandidateController {

	@Autowired
	private QuestionService questionService;
	@Autowired
	private ResultService resultService;
	@Autowired
	private CandidateService candidateService;
	@Autowired
	private CriteriaService criteriaService;

	@GetMapping("/mcqQuestions/{criteriaId}")
	public ResponseEntity<List<McqQuestionDTO>> getMcqQuestionsByCriteria(@PathVariable Long criteriaId,Principal principal)
			throws RecordNotFoundException {
		List<McqQuestionDTO> questions = questionService.generateMcqQuestionsByCriteriaId(criteriaId,principal);
		return new ResponseEntity<>(questions, HttpStatus.OK);
	}
	@GetMapping("/getTime/{criteriaId}")
	public ResponseEntity<Double> getTime(@PathVariable Long criteriaId)
			throws RecordNotFoundException {
		
		try {
            Double testDuration = criteriaService.getTime(criteriaId);
            return ResponseEntity.ok(testDuration);
        } catch (RecordNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

	}
	
	@GetMapping("/getActiveCriteriaId/{id}")
	public ResponseEntity<Long> getActiveCriteriaId(@PathVariable String id)
			 {
		
		
            Long criteriaId = criteriaService.getActiveCriteriaId();
            System.out.println(criteriaId);
            return ResponseEntity.ok(criteriaId);
       

	}

	@GetMapping("/codingQuestions/{criteriaId}")
	public ResponseEntity<List<CodingQuestionDTO>> getCodingQuestionsByCriteria(@PathVariable Long criteriaId)
			throws RecordNotFoundException {
		List<CodingQuestionDTO> questions = questionService.generateCodingQuestionsByCriteriaId(criteriaId);
		return new ResponseEntity<>(questions, HttpStatus.OK);
	}

	@PostMapping("/addMcqResults")
	public ResponseEntity<ServiceResponse> addMcqResults(@RequestBody List<ResultDTO> dto) {
		return new ResponseEntity<>(resultService.addMcqResults(dto), HttpStatus.OK);
	}

	@PostMapping("/addCodingResults")
	public ResponseEntity<ServiceResponse> addCodingResults(@RequestBody ResultDTO dto) {
		return new ResponseEntity<>(resultService.addCodingResults(dto), HttpStatus.OK);
	}

	@GetMapping("/addFinalResult/{userId}/{finishingTime}/{criteriaId}")
	public ResponseEntity<ServiceResponse> addFinalResult(@PathVariable String userId, @PathVariable Long finishingTime,
			@PathVariable Long criteriaId) {
		return new ResponseEntity<>(resultService.addFinalResult(userId, finishingTime, criteriaId), HttpStatus.OK);
	}

	@PostMapping("/compile")
	public ResponseEntity<Object> compileAndExecute(@RequestBody CodeRequest codeRequest) {

		return candidateService.compileAndExecute(codeRequest);

	}
	
	@GetMapping("/code")
    public List<CodingQuestion> getAllEntities() {
        return candidateService.getAllEntities();
    }


}
