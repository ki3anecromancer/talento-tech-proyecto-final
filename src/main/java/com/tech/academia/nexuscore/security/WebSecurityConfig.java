package com.tech.academia.nexuscore.security;

import com.tech.academia.nexuscore.security.jwt.JwtAuthenticationFilter;
import com.tech.academia.nexuscore.security.userdetails.UsuarioDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final UsuarioDetailsService usuarioDetailsService;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  // Codificador de contraseña
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // Define el administrador de autenticación
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  // Configuración de seguridad HTTP
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        // Deshabilitar CSRF
        .csrf(AbstractHttpConfigurer::disable)

        // Manejo de sesiones: Stateless (no guarda estado, usa JWT)
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // Manejo de autorización de rutas
        .authorizeHttpRequests(authorize -> authorize
            // Rutas de autenticación (abiertas a todos)
            .requestMatchers("/api/auth/**").permitAll()
            // Rutas públicas (ej. listar cursos sin estar logueado)
            .requestMatchers(HttpMethod.GET, "/api/cursos").permitAll()
            // El resto de las peticiones requieren autenticación
            .anyRequest().authenticated()
        )

        // Agregar el filtro JWT
        // Se ejecuta ANTES del filtro UsernamePasswordAuthenticationFilter
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
