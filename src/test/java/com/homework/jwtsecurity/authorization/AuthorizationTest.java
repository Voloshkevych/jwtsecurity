package com.homework.jwtsecurity.authorization;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.homework.jwtsecurity.model.Role;
import com.homework.jwtsecurity.service.implementation.UserEntityServiceImpl;
import com.homework.jwtsecurity.util.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorizationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private JWTUtil jwtUtil;

  @Autowired
  private UserEntityServiceImpl userEntityServiceImpl;

  private String generateTokenForUser(String username) {
    UserDetails userDetails = userEntityServiceImpl.loadUserByUsername(username);
    Role role = userDetails.getAuthorities().stream()
        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))
        ? Role.ADMIN : Role.CUSTOMER;
    return jwtUtil.generateToken(userDetails, role);
  }

  @Test
  public void whenUserAccessUserEndpoint_thenSucceeds() throws Exception {
    String userToken = generateTokenForUser("bohdan");

    mockMvc.perform(get("/api/user-endpoint")
            .header("Authorization", "Bearer " + userToken))
        .andExpect(status().isOk())
        .andExpect(content().string("Hello, User! You're authorized."));
  }

  @Test
  public void whenAdminAccessAdminEndpoint_thenSucceeds() throws Exception {
    String adminToken = generateTokenForUser("max");

    mockMvc.perform(get("/api/admin-endpoint")
            .header("Authorization", "Bearer " + adminToken))
        .andExpect(status().isOk())
        .andExpect(content().string("Hello, Admin! You have the ADMIN role."));
  }

  @Test
  public void whenUserAccessAdminEndpoint_thenForbidden() throws Exception {
    String userToken = generateTokenForUser("bohdan");

    mockMvc.perform(get("/api/admin-endpoint")
            .header("Authorization", "Bearer " + userToken))
        .andExpect(status().isForbidden());
  }
}
