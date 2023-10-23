package com.homework.jwtsecurity.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException {

    Map<String, Object> data = new HashMap<>();
    data.put("timestamp", LocalDateTime.now().toString());
    data.put("status", HttpServletResponse.SC_UNAUTHORIZED);
    data.put("error", "Unauthorized");
    data.put("path", request.getRequestURI());

    String json = new ObjectMapper().writeValueAsString(data);

    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.getWriter().write(json);
  }
}
