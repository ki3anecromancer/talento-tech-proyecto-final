package com.tech.academia.nexuscore.security;

import com.tech.academia.nexuscore.security.jwt.JwtAuthenticationFilter;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .cors(Customizer.withDefaults())
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .requestMatchers("/api/auth/**").permitAll()

            .requestMatchers(HttpMethod.POST, "/api/cursos").hasAnyRole("USER", "INSTRUCTOR", "ADMIN")
            .requestMatchers(HttpMethod.GET, "/api/cursos/me").hasAnyRole("USER", "INSTRUCTOR", "ADMIN")

            .requestMatchers(HttpMethod.GET, "/api/cursos/{id}").hasAnyRole("USER", "INSTRUCTOR", "ADMIN")
            .requestMatchers(HttpMethod.PUT, "/api/cursos/{idCurso}").hasAnyRole("INSTRUCTOR", "ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/cursos/{idCurso}").hasAnyRole("INSTRUCTOR", "ADMIN")

            .requestMatchers(HttpMethod.GET, "/api/usuarios/me").hasAnyRole("USER", "INSTRUCTOR", "ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/usuarios/me").hasAnyRole("USER", "INSTRUCTOR", "ADMIN")
            .requestMatchers(HttpMethod.PATCH, "/api/usuarios/me").hasAnyRole("USER", "INSTRUCTOR", "ADMIN")

            .requestMatchers(HttpMethod.GET, "/api/cursos/{idCurso}/inscripciones").hasAnyRole("INSTRUCTOR", "ADMIN")
            .requestMatchers(HttpMethod.POST, "/api/cursos/{idCurso}/modulos").hasAnyRole("INSTRUCTOR", "ADMIN")

            // .anyRequest().authenticated()
        )
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}