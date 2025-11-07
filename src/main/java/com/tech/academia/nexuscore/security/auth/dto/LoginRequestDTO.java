package com.tech.academia.nexuscore.security.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(

    @NotBlank
    String usernameOrEmail,

    @NotBlank
    String contrasena
) {

}
