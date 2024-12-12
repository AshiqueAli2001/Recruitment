package com.interland.admin.service;

import java.security.Principal;

import org.json.simple.JSONObject;

import com.interland.admin.dto.CriteriaDTO;
import com.interland.admin.dto.ServiceResponse;
import com.interland.admin.exception.RecordNotFoundException;

public interface CriteriaService {

	ServiceResponse addCriteria(CriteriaDTO dto, Principal principal);

	ServiceResponse updateCriteria(Long criteriaId, Principal principal );

	ServiceResponse deleteCriteria(Long criteriaId, Principal principal);

	JSONObject searchCriteria(String searchParam, int parseInt, int parseInt2);

}
