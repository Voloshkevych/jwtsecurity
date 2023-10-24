package com.homework.jwtsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GeneralController {

  @GetMapping("/user-endpoint")
  public ResponseEntity<String> userEndpoint() {
    return ResponseEntity.ok("Hello, User! You're authorized.");
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("/admin-endpoint")
  public ResponseEntity<String> adminEndpoint() {
    return ResponseEntity.ok("Hello, Admin! You have the ADMIN role.");
  }

  @CrossOrigin(origins = "http://anysite:80")
  @GetMapping("/local-endpoint")
  public ResponseEntity<String> localEndpoint() {
    return ResponseEntity.ok("This is for localhost!");
  }

  @CrossOrigin
  @GetMapping("/global-endpoint")
  public String globalEndpoint() {
    return "This is for everyone!";
  }

}
