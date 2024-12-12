package com.interland.candidate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interland.candidate.dto.CodeRequest;
import com.interland.candidate.entity.CodingQuestion;
import com.interland.candidate.repository.CodingQuestionRepository;

@Service
public class CandidateServiceImplement implements CandidateService {


	@Autowired
	CodingQuestionRepository codingQuestionRepository;

	

	public ResponseEntity<Object> compileAndExecute(CodeRequest codeRequest) {
		JSONObject response;
		switch (codeRequest.getLanguage()) {
        case "java":
            response = compileWithJDoodle(codeRequest.getCode(), codeRequest.getQuestionId(), "java");
            break;
        case "python":
            response = compileWithJDoodle(codeRequest.getCode(), codeRequest.getQuestionId(), "python3");
            break;
        case "cpp":
            response = compileWithJDoodle(codeRequest.getCode(), codeRequest.getQuestionId(), "cpp");
            break;
        case "c":
            response = compileWithJDoodle(codeRequest.getCode(), codeRequest.getQuestionId(), "c");
            break;
        default:
            return ResponseEntity.badRequest().body(new JSONObject().put("error", "Unsupported language: " + codeRequest.getLanguage()));
    }

		return ResponseEntity.ok(response);
	}
	
	 private String clientId = "c621c7ac7c350fc51cb8d9be50bcd6b5";
	 private String clientSecret = "c3e8d6021af1e0da8730fae6c64fb81ad4ad7cd89ba4b5756ae9ee0fc7bc2236";
	    
	private JSONObject compileWithJDoodle(String code, Long questionId, String language) {
		
		CodingQuestion codingQuestion = codingQuestionRepository.findById(questionId).get();
		JSONObject obj = new JSONObject();

		for (int i = 1; i <=5; i++) {
			

	        String input = getTestCaseInput(codingQuestion, i);
	        String expectedOutput = getExpectedOutput(codingQuestion, i);
			
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> requestBodyMap = new HashMap<>();
			requestBodyMap.put("clientId", clientId);
			requestBodyMap.put("clientSecret", clientSecret);
			requestBodyMap.put("script", code);
			requestBodyMap.put("language", language);
			requestBodyMap.put("versionIndex", "4");
			requestBodyMap.put("stdin", input); 
			
			try {
				String requestBody = objectMapper.writeValueAsString(requestBodyMap);
				
				
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
				
				RestTemplate restTemplate = new RestTemplate();
				String jdoodleApiUrl = "https://api.jdoodle.com/v1/execute";
				
				try {
					String jdoodleResponse = restTemplate.postForObject(jdoodleApiUrl, requestEntity, String.class);

					
					
					obj.put("Test Case " + i, isTestCasePassed(jdoodleResponse,expectedOutput) ? "PASS" : "FAIL");

					
				} catch (Exception e) {
					obj.put("error", "Error making JDoodle API call");
	                return obj;
				} 
			} catch (JsonProcessingException e) {
				 obj.put("error", "Error creating JSON request body");
		            return obj;
			}
		}
		
		return obj;
	}
	
	
	@Override
    public List<CodingQuestion> getAllEntities() {
        return codingQuestionRepository.findAll();
    }
	
	
	
	
	private String getTestCaseInput(CodingQuestion codingQuestion, int testCaseNumber) {
	    switch (testCaseNumber) {
	        case 1: return codingQuestion.getTestCase1();
	        case 2: return codingQuestion.getTestCase2();
	        case 3: return codingQuestion.getTestCase3();
	        case 4: return codingQuestion.getTestCase4();
	        case 5: return codingQuestion.getTestCase5();
	        default: return "";
	    }
	}

	private String getExpectedOutput(CodingQuestion codingQuestion, int testCaseNumber) {
	    switch (testCaseNumber) {
	        case 1: return codingQuestion.getExpected1();
	        case 2: return codingQuestion.getExpected2();
	        case 3: return codingQuestion.getExpected3();
	        case 4: return codingQuestion.getExpected4();
	        case 5: return codingQuestion.getExpected5();
	        default: return "";
	    }
	}
	
	private boolean isTestCasePassed(String jdoodleResponse, String expectedOutput) {
		try {
	        JSONParser parser = new JSONParser();
	        JSONObject jsonResponse = (JSONObject) parser.parse(jdoodleResponse);

	        String extractedOutput = ((String) jsonResponse.get("output")).trim();
	        expectedOutput = expectedOutput.trim();
	        if(extractedOutput.equals(expectedOutput)) {	        	
	        	
	        	return true;
	        }
	        else {
	        	return false;
	        }

	    } catch (Exception e) {
	        return false;
	    }
	}

	
	
}
