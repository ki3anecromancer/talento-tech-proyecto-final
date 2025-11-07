package com.tech.academia.nexuscore.dto;

import com.tech.academia.nexuscore.model.enums.TipoContenido;

public record ContenidoResponseDTO(

    Long id,
    String titulo,
    TipoContenido tipo,
    String urlArchivo,
    Integer duracionMinutos,
    Long idModulo
) {

}
