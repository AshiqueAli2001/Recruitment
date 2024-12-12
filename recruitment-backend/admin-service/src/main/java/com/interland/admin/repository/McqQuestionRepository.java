package com.interland.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.interland.admin.entity.McqQuestion;

@Repository
public interface McqQuestionRepository extends JpaRepository<McqQuestion, Long>,JpaSpecificationExecutor<McqQuestion>{

	@Query(value = "SELECT * FROM Mcq_Question WHERE difficulty = :difficulty ORDER BY RAND() LIMIT :limit",nativeQuery = true)
	List<McqQuestion> getRandomQuestionsByDifficultyAndType(@Param("difficulty") String difficulty,@Param("limit") int limit);

}
