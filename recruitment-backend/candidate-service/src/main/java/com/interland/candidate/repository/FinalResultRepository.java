package com.interland.candidate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.interland.candidate.entity.FinalResult;

@Repository
public interface FinalResultRepository extends JpaRepository<FinalResult, Long>,JpaSpecificationExecutor<FinalResult>{

}
