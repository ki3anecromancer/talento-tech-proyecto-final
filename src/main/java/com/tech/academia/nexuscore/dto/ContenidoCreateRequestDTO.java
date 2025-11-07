package com.tech.academia.nexuscore.dto;

import com.tech.academia.nexuscore.model.enums.TipoContenido;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record ContenidoCreateRequestDTO(

    @NotBlank
    @Size(min = 3, max = 50)
    String titulo,

    @NotNull
    TipoContenido tipo,

    @URL
    String urlArchivo,

    @Min(1)
    Integer duracionMinutos,

    @NotNull
    @Min(1L)
    Long idModulo
) {

}
