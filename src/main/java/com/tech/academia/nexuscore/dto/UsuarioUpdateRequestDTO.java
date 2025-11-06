package com.tech.academia.nexuscore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioUpdateRequestDTO(
    @NotBlank
    @Size(min = 4, max = 20)
    String nombreUsuario,

    @NotBlank
    @Email
    String email,

    @NotBlank
    @Size(max = 50)
    String nombre,

    @NotBlank
    @Size(max = 50)
    String apellido
) {

}
