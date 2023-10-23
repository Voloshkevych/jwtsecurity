package com.homework.jwtsecurity.service.implementation;

import com.homework.jwtsecurity.model.UserEntity;
import com.homework.jwtsecurity.repository.UserEntityRepository;
import com.homework.jwtsecurity.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserEntityServiceImpl implements UserEntityService, UserDetailsService {

  private final UserEntityRepository userEntityRepository;

  @Autowired
  public UserEntityServiceImpl(UserEntityRepository userEntityRepository) {
    this.userEntityRepository = userEntityRepository;
  }

  @Override
  public UserEntity getUserEntityById(Long id) {
    return userEntityRepository.getById(id);
  }

  @Override
  public UserEntity addUserEntity(UserEntity userEntity) {
    return userEntityRepository.save(userEntity);
  }

  @Override
  public UserEntity updateUserEntity(UserEntity userEntity) {
    return userEntityRepository.save(userEntity);
  }

  @Override
  public void deleteUserEntityById(Long id) {
    userEntityRepository.deleteById(id);
  }

  @Override
  public UserEntity getUserEntityByUsername(String username) {
    return userEntityRepository.findByUsername(username);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity userEntity = userEntityRepository.findByUsername(username);

    if (userEntity == null) {
      throw new UsernameNotFoundException("There is no user with username " + username);
    }

    UserBuilder userBuilder = User.withUsername(userEntity.getUsername());
    userBuilder.password(userEntity.getPassword());
    userBuilder.authorities(userEntity.getRole().name());

    return userBuilder.build();
  }
}
