package com.interland.candidate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interland.candidate.entity.Criteria;
import com.interland.candidate.exception.RecordNotFoundException;
import com.interland.candidate.repository.CriteriaRepository;

@Service
public class CriteriaServiceImplement implements CriteriaService {


	@Autowired
	CriteriaRepository criteriaRepository;


	@Override
	public Double getTime(Long criteriaId) throws RecordNotFoundException {
		Optional<Criteria> optionalCriteria = criteriaRepository.findById(criteriaId);

		if (optionalCriteria.isPresent()) {
			Criteria criteria = optionalCriteria.get();
			

			return criteria.getTestDuration();

		} else {
			throw new RecordNotFoundException("Criteria not found with ID: " + criteriaId);
		}
	}


	@Override
	public Long getActiveCriteriaId() {
		System.out.println("inside");
	    List<Criteria> allCriteria = criteriaRepository.findAll();

	    for (Criteria criteria : allCriteria) {
	        if (criteria.getStatus().equals("VERIFIED")) {
	        	System.out.println(criteria.getCriteriaId());
	            return criteria.getCriteriaId(); // Assuming the ID is of type Double
	        }
	    }

	    return null; // No active criteria found
	}
}
