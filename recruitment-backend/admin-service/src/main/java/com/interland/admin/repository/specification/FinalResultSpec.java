package com.interland.admin.repository.specification;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.interland.admin.entity.Criteria;
import com.interland.admin.entity.FinalResult;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class FinalResultSpec {
	public static Specification<FinalResult> getFinalResultSpec(String searchParam) {
		return new Specification<FinalResult>() {
			
			@Override
			public Predicate toPredicate(Root<FinalResult> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
			
				Predicate finalPredicate= null; 
				JSONObject searchObject;
				JSONParser parser = new JSONParser();
				
				try {
				searchObject = (JSONObject) parser.parse(searchParam);
				
				String userId = (String) searchObject.get("userId");
				String score = (String) searchObject.get("score");
				String result = (String) searchObject.get("result");

				if(!StringUtils.isEmpty(userId)) {
					Predicate userIdPredicate = criteriaBuilder.like(root.get("user").get("userId"), "%"+userId.toUpperCase()+"%");
					if(finalPredicate!=null) {
						finalPredicate=criteriaBuilder.and(finalPredicate,userIdPredicate);
					}
					else {
						finalPredicate = userIdPredicate;
					}
				}					
								
				if(!StringUtils.isEmpty(score)) {
					Predicate scorePredicate = criteriaBuilder.equal(root.get("score"), score);
					if(finalPredicate!=null) {
						finalPredicate=criteriaBuilder.and(finalPredicate,scorePredicate);
					}
					else {
						finalPredicate = scorePredicate;
					}
				}					
				if(!StringUtils.isEmpty(result)) {
					Predicate resultPredicate =  criteriaBuilder.like(root.get("result"), "%"+result.toUpperCase()+"%");
					if(finalPredicate!=null) {
						finalPredicate=criteriaBuilder.and(finalPredicate,resultPredicate);
					}
					else {
						finalPredicate = resultPredicate;
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
