package com.interland.admin.service;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.interland.admin.dto.CriteriaDTO;
import com.interland.admin.dto.ServiceResponse;
import com.interland.admin.entity.Criteria;
import com.interland.admin.entity.User;
import com.interland.admin.exception.RecordNotFoundException;
import com.interland.admin.repository.CriteriaRepository;
import com.interland.admin.repository.UserRepository;
import com.interland.admin.repository.specification.CriteriaSpec;
import com.interland.admin.utils.Constants;

@Service
public class CriteriaServiceImplement implements CriteriaService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	CriteriaRepository criteriaRepository;

	@Autowired
	MessageSource messageSource;
	
    private static Logger logger =LogManager.getLogger(CriteriaServiceImplement.class);



	@Override
	public ServiceResponse addCriteria(CriteriaDTO dto, Principal principal) {
		try {
			Optional<User> obj = userRepository.findById(dto.getUserId());

			if (obj.isEmpty())
				throw new RecordNotFoundException("admin.details.psh.VAL0006");
			User user = obj.get();

			Criteria criteria = new Criteria();
			
			

			BeanUtils.copyProperties(dto, criteria);

			int totalcoding = dto.getEasyCodingQuestions()+dto.getMediumCodingQuestions()+dto.getHardCodingQuestions();
			int totalmcq=dto.getEasyMcqQuestions()+dto.getMediumMcqQuestions()+dto.getHardMcqQuestions();
			criteria.setTotalQuestions(totalcoding+totalmcq);
			criteria.setUser(user);
			criteria.setCreatedBy(principal.getName());
			criteria.setCreatedTime(new Date());
			criteria.setStatus(Constants.MESSAGE_STATUS.PROCESSED);
			criteria.setModifiededBy(principal.getName());
			criteriaRepository.save(criteria);
			return new ServiceResponse(
					messageSource.getMessage("criteria.details.psh.VAL0001", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.SUCCESS, null);
		}catch (RecordNotFoundException e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ServiceResponse(
					messageSource.getMessage("admin.details.psh.VAL0006", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ServiceResponse(
					messageSource.getMessage("criteria.details.psh.VAL0002", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);
		}
	}

	@Override
	public ServiceResponse updateCriteria(Long criteriaId, Principal principal) {
		try {
			Optional<Criteria> optionalCriteria = criteriaRepository.findById(criteriaId);

			if (optionalCriteria.isPresent()) {
				Criteria existingCriteria = optionalCriteria.get();
				
				List<Criteria> allCriteria = criteriaRepository.findAll();

				if (existingCriteria.getStatus().equals(Constants.MESSAGE_STATUS.VERIFIED)) {
					
					existingCriteria.setStatus(Constants.MESSAGE_STATUS.PROCESSED);
					existingCriteria.setModifiededBy(principal.getName());
					criteriaRepository.save(existingCriteria);
		        	return new ServiceResponse(
			                messageSource.getMessage("criteria.details.psh.VAL0012", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.CANCELLED, null);
		        }
			    for (Criteria criteria : allCriteria) {
			        if (criteria.getStatus().equals(Constants.MESSAGE_STATUS.VERIFIED)) {
			        	return new ServiceResponse(
				                messageSource.getMessage("criteria.details.psh.VAL0010", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);
			        }
			    }

				existingCriteria.setStatus(Constants.MESSAGE_STATUS.VERIFIED);
				existingCriteria.setModifiededBy(principal.getName());
				criteriaRepository.save(existingCriteria);


				return new ServiceResponse(
		                messageSource.getMessage("criteria.details.psh.VAL0005", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.SUCCESS, null);
			} else {
				return new ServiceResponse(
						messageSource.getMessage("criteria.details.psh.VAL0011", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);
			}
		} catch (Exception e) {
	        logger.error("Error:" + e.getMessage(), e);
	        return new ServiceResponse(
	                messageSource.getMessage("criteria.details.psh.VAL0004", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);	  
		}
	}

	@Override
	public ServiceResponse deleteCriteria(Long criteriaId, Principal principal) {
		try {
			Optional<Criteria> optionalCriteria = criteriaRepository.findById(criteriaId);
			if (optionalCriteria.isPresent()) {


				criteriaRepository.deleteById(criteriaId);
				return new ServiceResponse(
						messageSource.getMessage("criteria.details.psh.VAL0007", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.DELETE, null);
			} else {
				return new ServiceResponse(
						messageSource.getMessage("criteria.details.psh.VAL0011", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);
			}
		}catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);

			return new ServiceResponse(
					messageSource.getMessage("criteria.details.psh.VAL0008", null, LocaleContextHolder.getLocale()),Constants.MESSAGE_STATUS.FAILED, null);		
		}
	}

	@Override
	public JSONObject searchCriteria(String searchParam, int start, int pageSize) {
		JSONObject result = new JSONObject();
		try {
			Pageable pageable = PageRequest.of(start / pageSize, pageSize);
			Specification<Criteria> spec = CriteriaSpec.getCandidateSpec(searchParam);
			Page<Criteria> criteriaList = criteriaRepository.findAll(spec, pageable);

			JSONArray array = new JSONArray();

			for (Criteria criteria : criteriaList) {
				JSONObject obj = new JSONObject();

				obj.put("criteriaId", criteria.getCriteriaId());
				obj.put("title", criteria.getTitle());
				obj.put("totalQuestions", criteria.getTotalQuestions());
				obj.put("easyMcqQuestions", criteria.getEasyMcqQuestions());
				obj.put("mediumMcqQuestions", criteria.getMediumMcqQuestions());
				obj.put("hardMcqQuestions", criteria.getHardMcqQuestions());
				obj.put("easyCodingQuestions", criteria.getEasyCodingQuestions());
				obj.put("mediumCodingQuestions", criteria.getMediumCodingQuestions());
				obj.put("hardCodingQuestions", criteria.getHardCodingQuestions());
				obj.put("testDuration", criteria.getTestDuration());
				obj.put("status", criteria.getStatus());
				if (criteria.getUser() != null)
					obj.put("userId", criteria.getUser().getUserId());
				else
					obj.put("criteriaId", null);

				array.add(obj);

			}
			result.put("aaData", array);
			result.put("iTotalDisplayRecords", criteriaRepository.findAll(spec).size());
			result.put("iTotalRecords", criteriaRepository.findAll(spec).size());

		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			e.printStackTrace();
		}

		return result;
	}
	
}
