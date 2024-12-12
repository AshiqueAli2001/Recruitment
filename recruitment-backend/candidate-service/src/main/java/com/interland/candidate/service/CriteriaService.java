package com.interland.candidate.service;

import com.interland.candidate.exception.RecordNotFoundException;

public interface CriteriaService {



	Double getTime(Long criteriaId)throws RecordNotFoundException;

	Long getActiveCriteriaId();
}
