package com.interland.admin.repository.specification;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.interland.admin.entity.Criteria;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class CriteriaSpec {

	public static Specification<Criteria> getCandidateSpec(String searchParam) {
		return new Specification<Criteria>() {
			
			@Override
			public Predicate toPredicate(Root<Criteria> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
			
				Predicate finalPredicate= null; 
				JSONObject searchObject;
				JSONParser parser = new JSONParser();
				
				try {
				searchObject = (JSONObject) parser.parse(searchParam);
				
				String easyMcqQuestions = (String) searchObject.get("easyMcqQuestions");
				String mediumMcqQuestions = (String) searchObject.get("mediumMcqQuestions");
				String hardMcqQuestions = (String) searchObject.get("hardMcqQuestions");
				String easyCodingQuestions = (String) searchObject.get("easyCodingQuestions");
				String mediumCodingQuestions = (String) searchObject.get("mediumCodingQuestions");
				String hardCodingQuestions = (String) searchObject.get("hardCodingQuestions");
				String totalQuestions = (String) searchObject.get("totalQuestions");
//				String status = (String) searchObject.get("status");
				
				if(!StringUtils.isEmpty(easyMcqQuestions)) {
					Predicate easyMcqQuestionsPredicate = criteriaBuilder.equal(root.get("easyMcqQuestions"), easyMcqQuestions);
					if(finalPredicate!=null) {
						finalPredicate=criteriaBuilder.and(finalPredicate,easyMcqQuestionsPredicate);
					}
					else {
						finalPredicate = easyMcqQuestionsPredicate;
					}
				}					
				if(!StringUtils.isEmpty(mediumMcqQuestions)) {
					Predicate mediumMcqQuestionsPredicate = criteriaBuilder.equal(root.get("mediumMcqQuestions"), mediumMcqQuestions);
					if(finalPredicate!=null) {
						finalPredicate=criteriaBuilder.and(finalPredicate,mediumMcqQuestionsPredicate);
					}
					else {
						finalPredicate = mediumMcqQuestionsPredicate;
					}
				}					
				if(!StringUtils.isEmpty(hardMcqQuestions)) {
					Predicate hardMcqQuestionsPredicate = criteriaBuilder.equal(root.get("hardMcqQuestions"), hardMcqQuestions);
					if(finalPredicate!=null) {
						finalPredicate=criteriaBuilder.and(finalPredicate,hardMcqQuestionsPredicate);
					}
					else {
						finalPredicate = hardMcqQuestionsPredicate;
					}
				}					
				if(!StringUtils.isEmpty(easyCodingQuestions)) {
					Predicate easyCodingQuestionsPredicate = criteriaBuilder.equal(root.get("easyCodingQuestions"), easyCodingQuestions);
					if(finalPredicate!=null) {
						finalPredicate=criteriaBuilder.and(finalPredicate,easyCodingQuestionsPredicate);
					}
					else {
						finalPredicate = easyCodingQuestionsPredicate;
					}
				}					
				if(!StringUtils.isEmpty(mediumCodingQuestions)) {
					Predicate mediumCodingQuestionsPredicate = criteriaBuilder.equal(root.get("mediumCodingQuestions"), mediumCodingQuestions);
					if(finalPredicate!=null) {
						finalPredicate=criteriaBuilder.and(finalPredicate,mediumCodingQuestionsPredicate);
					}
					else {
						finalPredicate = mediumCodingQuestionsPredicate;
					}
				}					
				if(!StringUtils.isEmpty(hardCodingQuestions)) {
					Predicate hardCodingQuestionsPredicate = criteriaBuilder.equal(root.get("hardCodingQuestions"), hardCodingQuestions);
					if(finalPredicate!=null) {
						finalPredicate=criteriaBuilder.and(finalPredicate,hardCodingQuestionsPredicate);
					}
					else {
						finalPredicate = hardCodingQuestionsPredicate;
					}
				}					
								
				
				if(!StringUtils.isEmpty(totalQuestions)) {
					Predicate totalQuestionsPredicate = criteriaBuilder.equal(root.get("totalQuestions"), totalQuestions);
					if(finalPredicate!=null) {
						finalPredicate=criteriaBuilder.and(finalPredicate,totalQuestionsPredicate);
					}
					else {
						finalPredicate = totalQuestionsPredicate;
					}
				}					
				
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
				
			return finalPredicate;
			}
		};
	}

}
