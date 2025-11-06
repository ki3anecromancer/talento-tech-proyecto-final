package com.tech.academia.nexuscore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record InscripcionRequestDTO(

    @NotNull
    @Min(1L)
    Long usuarioId,

    @NotNull
    @Min(1L)
    Long cursoId
) {

}
