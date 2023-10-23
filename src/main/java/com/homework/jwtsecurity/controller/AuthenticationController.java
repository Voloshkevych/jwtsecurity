package com.homework.jwtsecurity.controller;

import com.homework.jwtsecurity.dto.AuthenticationRequest;
import com.homework.jwtsecurity.dto.AuthenticationResponse;
import com.homework.jwtsecurity.model.Role;
import com.homework.jwtsecurity.service.implementation.UserEntityServiceImpl;
import com.homework.jwtsecurity.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

  private AuthenticationManager authenticationManager;
  private JWTUtil jwtUtil;
  private UserEntityServiceImpl userEntityServiceImpl;

  @Autowired
  public AuthenticationController(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserEntityServiceImpl userEntityServiceImpl) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
    this.userEntityServiceImpl = userEntityServiceImpl;
  }

  @PostMapping("/login")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
      );
    } catch (BadCredentialsException e) {
      throw new Exception("Incorrect username or password", e);
    }

    UserDetails userDetails = userEntityServiceImpl
        .loadUserByUsername(authenticationRequest.getUsername());

    final Role role = userEntityServiceImpl.getUserEntityByUsername(authenticationRequest.getUsername()).getRole();

    final String jwt = jwtUtil.generateToken(userDetails, role);

    return ResponseEntity.ok(new AuthenticationResponse(jwt));
  }
}
