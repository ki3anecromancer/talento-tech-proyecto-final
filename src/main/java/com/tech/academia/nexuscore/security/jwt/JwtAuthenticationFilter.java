package com.tech.academia.nexuscore.security.jwt;

import com.tech.academia.nexuscore.security.userdetails.UsuarioDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtTokenProvider tokenProvider;
  private final UsuarioDetailsService usuarioDetailsService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    // 1. Obtener el token de la cabecera de la petición
    String token = obtenerTokenDeRequest(request);

    // 2. Validar el token y cargar la autenticación
    if (token != null && tokenProvider.validarToken(token)) {

      // Obtener el ID del usuario del token
      Long idUsuario = tokenProvider.obtenerIdDeJWT(token);

      // Cargar los detalles del usuario
      // Usamos loadUserById (asumiendo que lo agregamos al service, o lo cargamos por nombre de usuario)
      // Por simplicidad, usaremos el ID para cargar el UserDetails
      UserDetails userDetails = usuarioDetailsService.loadUserById(idUsuario);

      // 3. Crear el objeto de autenticación
      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(
              userDetails,
              null, // La contraseña ya no es necesaria, es null
              userDetails.getAuthorities()
          );

      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

      // 4. Establecer la autenticación en el Contexto de Seguridad
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // Continuar con la cadena de filtros de Spring Security
    filterChain.doFilter(request, response);
  }

  private String obtenerTokenDeRequest(HttpServletRequest request) {

    String bearerToken = request.getHeader("Authorization");

    // El token viene típicamente como "Bearer <token>"
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
