package com.interland.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.interland.admin.entity.McqQuestion;
import com.interland.admin.entity.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long>,JpaSpecificationExecutor<Result>{

	@Query(value = "SELECT * FROM Result WHERE User_ID = :userId AND Attempt_No= :attemptNo",nativeQuery = true)
	List<Result> getResultByUserIdAndAttemptNo(@Param("userId") String userId,@Param("attemptNo") int attemptNo);

}
