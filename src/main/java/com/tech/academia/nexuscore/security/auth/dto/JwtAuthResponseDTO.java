package com.tech.academia.nexuscore.security.auth.dto;

public record JwtAuthResponseDTO(

    String accessToken,

    // La convención es indicar el tipo de token (siempre "Bearer" para JWT)
    String tokenType
) {

}
