package com.interland.admin.service;

import java.security.Principal;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.interland.admin.dto.CodeRequest;
import com.interland.admin.dto.ServiceResponse;
import com.interland.admin.dto.UserDTO;
import com.interland.admin.entity.CodingQuestion;
import com.interland.admin.entity.FinalResult;
import com.interland.admin.entity.User;

public interface CandidateService {

	ServiceResponse addCandidate(MultipartFile file, Principal principal);

	JSONObject searchCandidate(String searchParam, int parseInt, int parseInt2);

	ServiceResponse updateCandidate(UserDTO dto, MultipartFile file, Principal principal);
	
	

	ServiceResponse deleteCandidate(String userId, Principal principal);

	User getUserById(String id);

	FinalResult searchFinalResultById(Long id);

	ServiceResponse verifyCandidate(String userId, Principal principal);

	ServiceResponse addIndividualCandidate(UserDTO dto, Principal principal);




	

	


}
