package com.interland.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.interland.admin.entity.FinalResult;

@Repository
public interface FinalResultRepository extends JpaRepository<FinalResult, Long>,JpaSpecificationExecutor<FinalResult>{

}
