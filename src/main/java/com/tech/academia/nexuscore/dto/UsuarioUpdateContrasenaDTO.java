package com.tech.academia.nexuscore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioUpdateContrasenaDTO(

    @NotBlank
    @Size(min = 8)
    String contrasena
) {

}
