package com.interland.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.interland.admin.entity.CodingQuestion;

@Repository
public interface CodingQuestionRepository extends JpaRepository<CodingQuestion, Long>,JpaSpecificationExecutor<CodingQuestion> {

	@Query(value = "SELECT * FROM Coding_Question WHERE difficulty = :difficulty ORDER BY RAND() LIMIT :limit",nativeQuery = true)
	List<CodingQuestion> getRandomQuestionsByDifficultyAndType(@Param("difficulty") String difficulty,@Param("limit") int limit);

}
