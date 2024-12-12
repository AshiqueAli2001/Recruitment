package com.interland.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.interland.admin.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{
}
