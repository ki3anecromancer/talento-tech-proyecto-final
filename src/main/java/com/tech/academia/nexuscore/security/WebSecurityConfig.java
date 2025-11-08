package com.tech.academia.nexuscore.security;

import com.tech.academia.nexuscore.security.jwt.JwtAuthenticationFilter;
import com.tech.academia.nexuscore.security.userdetails.UsuarioDetailsService;
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
        // Aplicar la configuración del CORS
        .cors(Customizer.withDefaults())

        // Deshabilitar CSRF
        .csrf(AbstractHttpConfigurer::disable)

        // Manejo de sesiones: Stateless (no guarda estado, usa JWT)
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // Manejo de autorización de rutas
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

            .requestMatchers("/api/auth/**").permitAll()

            .requestMatchers(HttpMethod.GET, "/api/usuarios/me").hasAnyRole("USER", "INSTRUCTOR", "ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/usuarios/me").hasAnyRole("USER", "INSTRUCTOR", "ADMIN")
            .requestMatchers(HttpMethod.PATCH, "/api/usuarios/me").hasAnyRole("USER", "INSTRUCTOR", "ADMIN")

            .anyRequest().authenticated()
        )

        // Agregar el filtro JWT
        // Se ejecuta ANTES del filtro UsernamePasswordAuthenticationFilter
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  // 3. Bean que define la política CORS
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    // Permite tu origen de frontend
    configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:5500", "http://localhost:5500"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(
        Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "accept", "Origin"));
    // Permite las credenciales y, lo más importante, expone los headers relevantes
    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3600L); // Cache del preflight por 1 hora

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration); // Aplica a todas las rutas
    return source;
  }
}
