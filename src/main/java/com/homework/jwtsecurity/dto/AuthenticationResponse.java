package com.homework.jwtsecurity.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AuthenticationResponse {
  private final String jwt;

  @JsonCreator
  public AuthenticationResponse(@JsonProperty("jwt") String jwt) {
    this.jwt = jwt;
  }

}
