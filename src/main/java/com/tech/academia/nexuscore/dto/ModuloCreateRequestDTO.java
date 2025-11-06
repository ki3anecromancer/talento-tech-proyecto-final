package com.tech.academia.nexuscore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ModuloCreateRequestDTO(

    @NotBlank
    @Size(min = 3, max = 50)
    String titulo,

    @NotNull
    @Min(1)
    Integer orden,

    @NotNull
    @Min(1L)
    Long idCurso
) {

}
