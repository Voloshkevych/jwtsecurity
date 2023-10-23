package com.homework.jwtsecurity.service;

import com.homework.jwtsecurity.model.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserEntityService {

  UserEntity getUserEntityById(Long id);

  UserEntity addUserEntity(UserEntity userEntity);

  UserEntity updateUserEntity(UserEntity userEntity);

  void deleteUserEntityById (Long id);

  UserEntity getUserEntityByUsername(String username);
}