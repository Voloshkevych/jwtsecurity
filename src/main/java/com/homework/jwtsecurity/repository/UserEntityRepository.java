package com.homework.jwtsecurity.repository;

import com.homework.jwtsecurity.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

  UserEntity findByUsername(String username);
}
