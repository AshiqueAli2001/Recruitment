package com.interland.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.interland.auth.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{
}
