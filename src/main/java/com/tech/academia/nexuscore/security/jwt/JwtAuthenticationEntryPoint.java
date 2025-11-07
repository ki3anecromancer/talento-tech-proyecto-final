package com.tech.academia.nexuscore.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {

    // 1. Establecer el código de estado HTTP
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Código 401

    // 2. Establecer el tipo de contenido como JSON
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    // 3. Escribir el mensaje de error en la respuesta
    Map<String, Object> body = new HashMap<>();
    body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
    body.put("error", "Unauthorized");
    body.put("message", "Acceso denegado. Necesitas un token JWT válido.");
    body.put("path", request.getServletPath());

    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(response.getOutputStream(), body);
  }
}
