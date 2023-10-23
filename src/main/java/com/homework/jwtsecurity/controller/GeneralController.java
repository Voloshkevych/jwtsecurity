package com.homework.jwtsecurity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GeneralController {

  @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
  @GetMapping("/user-endpoint")
  public ResponseEntity<String> userEndpoint() {
    return ResponseEntity.ok("Hello, User! You're authorized.");
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("/admin-endpoint")
  public ResponseEntity<String> adminEndpoint() {
    return ResponseEntity.ok("Hello, Admin! You have the ADMIN role.");
  }
}
