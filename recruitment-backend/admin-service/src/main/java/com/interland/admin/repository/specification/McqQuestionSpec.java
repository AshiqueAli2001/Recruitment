package com.interland.admin.repository.specification;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.interland.admin.entity.McqQuestion;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class McqQuestionSpec {

	public static Specification<McqQuestion> getMcqQuestionSpec(String searchParam) {

		return new Specification<McqQuestion>() {

			@Override
			public Predicate toPredicate(Root<McqQuestion> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				Predicate finalPredicate = null;
				JSONObject searchObject;
				JSONParser parser = new JSONParser();

				try {
					searchObject = (JSONObject) parser.parse(searchParam);
					String category = (String) searchObject.get("category");
					String difficulty = (String) searchObject.get("difficulty");

					if (!StringUtils.isEmpty(category)) {
						Predicate categoryPredicate = criteriaBuilder.equal(root.get("category"), category);
						if (finalPredicate != null) {
							finalPredicate = criteriaBuilder.and(finalPredicate, categoryPredicate);
						} else {
							finalPredicate = categoryPredicate;
						}
					}
					if (!StringUtils.isEmpty(difficulty)) {
						Predicate difficultyPredicate = criteriaBuilder.like(root.get("difficulty"), difficulty);
						if (finalPredicate != null) {
							finalPredicate = criteriaBuilder.and(finalPredicate, difficultyPredicate);
						} else {
							finalPredicate = difficultyPredicate;
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
