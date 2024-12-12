package com.interland.admin.repository.specification;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.interland.admin.entity.Role;
import com.interland.admin.entity.User;
import com.interland.admin.utils.Constants;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class UserSpec {

	public static Specification<User> getUserSpec(String searchParam) {

		return new Specification<User>() {
			
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
					
					Predicate finalPredicate= null; 
					JSONObject searchObject;
					JSONParser parser = new JSONParser();
					
					try {
					searchObject = (JSONObject) parser.parse(searchParam);
					String role = (String) searchObject.get("role");
					String userId = (String) searchObject.get("userId");
					String name = (String) searchObject.get("name");
//					String email = (String) searchObject.get("email");
					String college = (String) searchObject.get("college");
					String department = (String) searchObject.get("department");
					String noOfAttempts = (String) searchObject.get("noOfAttempts");
//					String result = (String) searchObject.get("result");
//					String score = (String) searchObject.get("score");
					String status = (String) searchObject.get("status");
					switch(status) {
					case "1" -> status=Constants.MESSAGE_STATUS.PROCESSED; 
					case "2" -> status=Constants.MESSAGE_STATUS.DELETED;
					case "3" -> status=Constants.MESSAGE_STATUS.DELETE;
					case "4" -> status=Constants.MESSAGE_STATUS.VERIFIED;
					default -> status="";
					}
					
					if(!StringUtils.isEmpty(role)) {
						Predicate rolePredicate = criteriaBuilder.equal(root.get("role"), role);
						if(finalPredicate!=null) {
							finalPredicate=criteriaBuilder.and(finalPredicate,rolePredicate);
						}
						else {
							finalPredicate = rolePredicate;
						}
					}					
					if(!StringUtils.isEmpty(status)) {
						Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), status);
						if(finalPredicate!=null) {
							finalPredicate=criteriaBuilder.and(finalPredicate,statusPredicate);
						}
						else {
							finalPredicate = statusPredicate;
						}
					}					
					if(!StringUtils.isEmpty(userId)) {
						Predicate userIdPredicate = criteriaBuilder.like(root.get("userId"), "%"+userId.toUpperCase()+"%");
						if(finalPredicate!=null) {
							finalPredicate=criteriaBuilder.and(finalPredicate,userIdPredicate);
						}
						else {
							finalPredicate = userIdPredicate;
						}
					}					
					if(!StringUtils.isEmpty(name)) {
						Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%"+name.toUpperCase()+"%");
						if(finalPredicate!=null) {
							finalPredicate=criteriaBuilder.and(finalPredicate,namePredicate);
						}
						else {
							finalPredicate = namePredicate;
						}
					}					
					if(!StringUtils.isEmpty(college)) {
   					 Predicate collegePredicate = criteriaBuilder.like(root.get("college"), "%"+college.toUpperCase()+"%");
   					 if(finalPredicate!=null) {
   						 finalPredicate=criteriaBuilder.and(finalPredicate,collegePredicate);
   					 }
   					 else {
   						 finalPredicate = collegePredicate;
   					 }
					}					
					if(!StringUtils.isEmpty(department)) {
						Predicate departmentPredicate = criteriaBuilder.like(root.get("department"), "%"+department.toUpperCase()+"%");
						if(finalPredicate!=null) {
							finalPredicate=criteriaBuilder.and(finalPredicate,departmentPredicate);
						}
						else {
							finalPredicate = departmentPredicate;
						}
					}					
					if(!StringUtils.isEmpty(noOfAttempts)) {
						Predicate noOfAttemptsPredicate = criteriaBuilder.equal(root.get("noOfAttempts"),noOfAttempts);
						if(finalPredicate!=null) {
							finalPredicate=criteriaBuilder.and(finalPredicate,noOfAttemptsPredicate);
						}
						else {
							finalPredicate = noOfAttemptsPredicate;
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
