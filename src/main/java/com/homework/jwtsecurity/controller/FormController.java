package com.homework.jwtsecurity.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FormController {

  private AuthenticationManager authenticationManager;

  @Autowired
  public FormController(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }
  @GetMapping("/show-form")
  public String showForm() {
    return "form";
  }

  @PostMapping("/submit-form")
  public ResponseEntity<String> submitForm(@RequestParam String username, @RequestParam String password) {

//    if (csrfToken == null) {
//      System.out.println("You are a failure!");
//    } else {
//      System.out.println("A surprise to be sure, but a welcome one");
//      System.out.println("Your CSRF token: " + csrfToken.getToken());
//    }
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password)
      );

      SecurityContextHolder.getContext().setAuthentication(authentication);

      System.out.println("Noooooooooooooooooooob");
      return ResponseEntity.ok("Successfully authenticated");
    } catch (AuthenticationException e) {
      System.out.println("Noooooooooooooooooooob but twice");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
    }
  }
}
