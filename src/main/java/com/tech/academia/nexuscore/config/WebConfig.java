package com.tech.academia.nexuscore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**") // Aplica a todos los endpoints bajo /api/
        .allowedOrigins("http://localhost:5500", "http://127.0.0.1:5500") // << Reemplaza con el puerto de tu frontend
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos HTTP permitidos
        .allowedHeaders("*") // Permite todas las cabeceras, crucial para el 'Authorization' (JWT)
        .allowCredentials(true) // Permite el envío de cookies o credenciales (si las usas)
        .maxAge(3600); // Tiempo de caché para preflight requests
  }
}