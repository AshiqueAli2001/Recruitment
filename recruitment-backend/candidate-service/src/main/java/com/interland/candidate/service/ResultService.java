package com.interland.candidate.service;

import java.util.List;

import com.interland.candidate.dto.ResultDTO;
import com.interland.candidate.dto.ServiceResponse;

public interface ResultService {

	ServiceResponse addMcqResults(List<ResultDTO> dto);

	ServiceResponse addCodingResults(ResultDTO dto);

	ServiceResponse addFinalResult(String userId, Long finishingTime, Long criteriaId);

}
