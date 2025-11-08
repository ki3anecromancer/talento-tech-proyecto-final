package com.tech.academia.nexuscore.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtTokenProvider tokenProvider;

  /**
   * Procesa las peticiones para buscar el JWT y establecer la autenticación.
   */
  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String token = obtenerTokenDeRequest(request);

    if (StringUtils.hasText(token) && tokenProvider.validarToken(token)) {

      try {
        // 1. Obtenemos el objeto Authentication (ID y Authorities/Roles) directamente del token.
        Authentication authentication = tokenProvider.getAuthentication(token);

        // 2. Establecer la autenticación en el Contexto de Seguridad.
        // 💡 La línea .setDetails(...) ha sido eliminada.
        SecurityContextHolder.getContext().setAuthentication(authentication);

      } catch (Exception e) {
        logger.error("Error al establecer la autenticación desde el token JWT", e);
      }
    }

    filterChain.doFilter(request, response);
  }

  /**
   * Métod auxiliar para extraer el token 'Bearer ' de la cabecera.
   */
  private String obtenerTokenDeRequest(HttpServletRequest request) {

    String bearerToken = request.getHeader("Authorization");

    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }
}