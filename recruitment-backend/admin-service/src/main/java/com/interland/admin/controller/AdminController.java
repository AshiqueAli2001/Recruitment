package com.interland.admin.controller;

import java.security.Principal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.interland.admin.dto.CodingQuestionDTO;
import com.interland.admin.dto.CriteriaDTO;
import com.interland.admin.dto.McqQuestionDTO;
import com.interland.admin.dto.ServiceResponse;
import com.interland.admin.dto.UserDTO;
import com.interland.admin.entity.FinalResult;
import com.interland.admin.entity.User;
import com.interland.admin.service.AdminService;
import com.interland.admin.service.CandidateService;
import com.interland.admin.service.CriteriaService;
import com.interland.admin.service.QuestionService;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/admin")

public class AdminController {
	
	 private static Logger logger =LogManager.getLogger(AdminController.class);


	@Autowired
	CandidateService candidateService;

	@Autowired
	AdminService adminService;

	@Autowired
	QuestionService questionService;

	@Autowired
	CriteriaService criteriaService;

	@GetMapping("/searchAdmin")
	public ResponseEntity<JSONObject> searchAdmin(@RequestParam("searchParam") String searchParam,
			@RequestParam("iDisplayStart") String iDisplayStart,
			@RequestParam("iDisplayLength") String iDisplayLength) {

		JSONObject list = adminService.searchAdmin(searchParam, Integer.parseInt(iDisplayStart),
				Integer.parseInt(iDisplayLength));

		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
	}
	@GetMapping("/searchCandidate")
	public ResponseEntity<JSONObject> searchCandidate(@RequestParam("searchParam") String searchParam,
			@RequestParam("iDisplayStart") String iDisplayStart,
			@RequestParam("iDisplayLength") String iDisplayLength) {
		
		JSONObject list = candidateService.searchCandidate(searchParam, Integer.parseInt(iDisplayStart),
				Integer.parseInt(iDisplayLength));
		
		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@GetMapping("/searchMcqQuestion")
	public ResponseEntity<JSONObject> searchMcqQuestion(@RequestParam("searchParam") String searchParam,
			@RequestParam("iDisplayStart") String iDisplayStart,
			@RequestParam("iDisplayLength") String iDisplayLength) {
		
		JSONObject list = questionService.searchMcqQuestion(searchParam, Integer.parseInt(iDisplayStart),
				Integer.parseInt(iDisplayLength));
		
		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
	}
	@GetMapping("/searchCodingQuestion")
	public ResponseEntity<JSONObject> searchCodingQuestion(@RequestParam("searchParam") String searchParam,
			@RequestParam("iDisplayStart") String iDisplayStart,
			@RequestParam("iDisplayLength") String iDisplayLength) {
		
		JSONObject list = questionService.searchCodingQuestion(searchParam, Integer.parseInt(iDisplayStart),
				Integer.parseInt(iDisplayLength));
		
		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/searchCriteria")
	public ResponseEntity<JSONObject> searchCriteria(@RequestParam("searchParam") String searchParam,
			@RequestParam("iDisplayStart") String iDisplayStart,
			@RequestParam("iDisplayLength") String iDisplayLength) {
		
		JSONObject list = criteriaService.searchCriteria(searchParam, Integer.parseInt(iDisplayStart),
				Integer.parseInt(iDisplayLength));
		
		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/searchFinalResult")
	public ResponseEntity<JSONObject> searchFinalResult(@RequestParam("searchParam") String searchParam,
			@RequestParam("iDisplayStart") String iDisplayStart,
			@RequestParam("iDisplayLength") String iDisplayLength) {
		
		JSONObject list = adminService.searchFinalResult(searchParam, Integer.parseInt(iDisplayStart),
				Integer.parseInt(iDisplayLength));
		
		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/downloadFile/{finalResultid}")
	public ResponseEntity<?> downloadFile(@PathVariable("finalResultid") Long id) {
		FinalResult finalResult = adminService.downloadFile(id);
		
		if (finalResult!=null) {
			
			   HttpHeaders headers = new HttpHeaders();
			    headers.add(HttpHeaders.CONTENT_DISPOSITION,  finalResult.getUser().getName() );
			    headers.setContentType(MediaType.APPLICATION_PDF);
			    headers.add("Access-Control-Expose-Headers", "Content-Disposition");

			return new ResponseEntity<>(finalResult.getPdf(),  headers, HttpStatus.OK);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		 
	}
	


	@PostMapping("/addCandidate")
	public ResponseEntity<ServiceResponse> addCandidate(@RequestPart(value = "file") MultipartFile file,Principal principal) {
		return new ResponseEntity<>(candidateService.addCandidate(file,principal), new HttpHeaders(), HttpStatus.OK);
	}
	@PostMapping("/addIndividualCandidate")
	public ResponseEntity<ServiceResponse> addIndividualCandidate( @RequestBody UserDTO dto,Principal principal) {
		return new ResponseEntity<>(candidateService.addIndividualCandidate(dto,principal), new HttpHeaders(), HttpStatus.OK);
	}
	
	@PostMapping("/updateCandidate")
    public ResponseEntity<ServiceResponse> updateCandidate(@RequestPart(value = "file", required = false) MultipartFile file,@RequestPart("itemToUpdate") UserDTO dto,Principal principal) {
        return new ResponseEntity<>(candidateService.updateCandidate(dto,file,principal), new HttpHeaders(), HttpStatus.OK);
    }
	
	@DeleteMapping("/deleteCandidate/{userId}")
	public ResponseEntity<ServiceResponse> deleteCandidate(@PathVariable String userId,Principal principal) {
	    return new ResponseEntity<>(candidateService.deleteCandidate(userId,principal), new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/verifyCandidate/{userId}")
	public ResponseEntity<ServiceResponse> verifyCandidate(@PathVariable String userId,Principal principal) {
	    return new ResponseEntity<>(candidateService.verifyCandidate(userId,principal), new HttpHeaders(), HttpStatus.OK);
	}
	

	@PostMapping("/addAdmin")
	public ResponseEntity<ServiceResponse> addAdmin( @RequestBody UserDTO dto,Principal principal) {
		return new ResponseEntity<>(adminService.addAdmin(dto,principal), new HttpHeaders(), HttpStatus.OK);
	}
	
	@PostMapping("/updateAdmin")
    public ResponseEntity<ServiceResponse> updateAdmin(@RequestPart(value = "file", required = false) MultipartFile file,@RequestPart("itemToUpdate") UserDTO dto,Principal principal) {
           return new ResponseEntity<>(adminService.updateAdmin(dto,file,principal), new HttpHeaders(), HttpStatus.OK);
    }
	
	@DeleteMapping("/deleteAdmin/{userId}")
	public ResponseEntity<ServiceResponse> deleteAdmin(@PathVariable("userId") String userId,Principal principal) {		
		return new ResponseEntity<>(adminService.deleteAdmin(userId,principal), new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@PostMapping("/addMcqQuestion")
	public ResponseEntity<ServiceResponse> addMcqQuestion( @RequestBody McqQuestionDTO dto,Principal principal) {
		return new ResponseEntity<>(questionService.addMcqQuestion(dto,principal), new HttpHeaders(), HttpStatus.OK);
	}
	
	@PostMapping("/updateMcqQuestion")
    public ResponseEntity<ServiceResponse> updateMcqQuestion( @RequestBody McqQuestionDTO dto,Principal principal) {
          return new ResponseEntity<>(questionService.updateMcqQuestion(dto,principal), new HttpHeaders(), HttpStatus.OK);
    }
	@DeleteMapping("/deleteMcqQuestion/{questionId}")
	public ResponseEntity<ServiceResponse> deleteMcqQuestion(@PathVariable("questionId") Long questionId,Principal principal) {		
		return new ResponseEntity<>(questionService.deleteMcqQuestion(questionId,principal), new HttpHeaders(), HttpStatus.OK);
	}
	
	@PostMapping("/addCodingQuestion")
	public ResponseEntity<ServiceResponse> addCodingQuestion( @RequestBody CodingQuestionDTO dto,Principal principal) {
		return new ResponseEntity<>(questionService.addCodingQuestion(dto,principal), new HttpHeaders(), HttpStatus.OK);
	}
	
	@PostMapping("/updateCodingQuestion")
	public ResponseEntity<ServiceResponse> updateCodingQuestion( @RequestBody CodingQuestionDTO dto,Principal principal) {
		return new ResponseEntity<>(questionService.updateCodingQuestion(dto,principal), new HttpHeaders(), HttpStatus.OK);
	}
	@DeleteMapping("/deleteCodingQuestion/{questionId}")
	public ResponseEntity<ServiceResponse> deleteCodingQuestion(@PathVariable("questionId") Long questionId,Principal principal) {		
		return new ResponseEntity<>(questionService.deleteCodingQuestion(questionId,principal), new HttpHeaders(), HttpStatus.OK);
	}

	
	

	@PostMapping("/addCriteria")
	public ResponseEntity<ServiceResponse> addCriteria( @RequestBody CriteriaDTO dto,Principal principal) {
		return new ResponseEntity<>(criteriaService.addCriteria(dto,principal), new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/updateCriteria/{criteriaId}")
    public ResponseEntity<ServiceResponse> updateCriteria(@PathVariable("criteriaId") Long criteriaId,Principal principal) {
      
        return new ResponseEntity<>(criteriaService.updateCriteria(criteriaId,principal), new HttpHeaders(), HttpStatus.OK);
    }
	
	
	@DeleteMapping("/deleteCriteria/{criteriaId}")
	public ResponseEntity<ServiceResponse> deleteCriteria(@PathVariable("criteriaId") Long criteriaId,Principal principal) {		
		return new ResponseEntity<>(criteriaService.deleteCriteria(criteriaId,principal), new HttpHeaders(), HttpStatus.OK);
	}
	
	

	@GetMapping("/searchById/{id}")
	public ResponseEntity<Object> getUSerById(@PathVariable String id){
		try {
			logger.info("Get user id : {}", id);
			User user = candidateService.getUserById(id);

			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
			return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping("/searchFinalResultById/{id}")
	public ResponseEntity<Object> searchFinalResultById(@PathVariable Long id){
		try {
			logger.info("Get Final Result id : {}", id);
			FinalResult finalResult = candidateService.searchFinalResultById(id);

			return new ResponseEntity<>(finalResult, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
			return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
