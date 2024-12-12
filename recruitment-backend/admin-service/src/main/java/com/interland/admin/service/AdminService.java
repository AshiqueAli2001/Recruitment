package com.interland.admin.service;

import java.security.Principal;

import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.interland.admin.dto.ServiceResponse;
import com.interland.admin.dto.UserDTO;
import com.interland.admin.entity.FinalResult;
import com.interland.admin.entity.User;

public interface AdminService {

	ServiceResponse addAdmin(UserDTO dto, Principal principal);

	ServiceResponse updateAdmin(UserDTO dto, MultipartFile file, Principal principal);
	

	ServiceResponse deleteAdmin(String userId, Principal principal);

	JSONObject searchAdmin(String searchParam, int parseInt, int parseInt2);

	JSONObject searchFinalResult(String searchParam, int parseInt, int parseInt2);

	FinalResult downloadFile(Long id);




	

}
