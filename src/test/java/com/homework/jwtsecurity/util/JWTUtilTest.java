package com.homework.jwtsecurity.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import com.homework.jwtsecurity.model.Role;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class JWTUtilTest {
  @InjectMocks
  private JWTUtil jwtUtil;

  private UserDetails userDetails;

  private static final String SECRET_KEY = "someSecretKeysomeSecretKeysomeSecretKeysomeSecretKeysomeSecretKeysomeSecretKeysomeSecretKeysomeSecretKeysomeSecretKeysomeSecretKeysomeSecretKeysomeSecretKey";
  private static final long EXPIRATION_TIME = 86400000;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    jwtUtil = new JWTUtil();

    setField(jwtUtil, "secret", SECRET_KEY);
    setField(jwtUtil, "expirationTime", EXPIRATION_TIME);

    userDetails = new User("bohdan", "user", new ArrayList<>());
  }

  @Test
  void successfulTokenGeneration() {
    String token = jwtUtil.generateToken(userDetails, Role.CUSTOMER);
    assertNotNull(token);
  }

  @Test
  void successfulValidation() {
    String token = jwtUtil.generateToken(userDetails, Role.CUSTOMER);
    assertTrue(jwtUtil.validateToken(token, userDetails));
  }

  @Test
  void failedValidation() {
    String token = "some.bullshit.token";

    assertThrows(MalformedJwtException.class, () -> {
      jwtUtil.validateToken(token, userDetails);
    });
  }

  @Test
  void correctUsernameExtraction() {
    String token = jwtUtil.generateToken(userDetails, Role.CUSTOMER);
    assertEquals("bohdan", jwtUtil.extractUsername(token));
  }

  @Test
  public void shouldNotValidateTokenAfterExpiration() throws InterruptedException {
    setField(jwtUtil, "expirationTime", 1L); // 1 millisecond
    String token = jwtUtil.generateToken(userDetails, Role.CUSTOMER);

    Thread.sleep(100);

    assertThrows(ExpiredJwtException.class, () -> {
      jwtUtil.validateToken(token, userDetails);
    });
  }

}
