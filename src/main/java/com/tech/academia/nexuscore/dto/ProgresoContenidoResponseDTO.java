package com.tech.academia.nexuscore.dto;

import java.time.LocalDateTime;

public record ProgresoContenidoResponseDTO(
    Long id,
    UsuarioReferenceDTO usuario,
    ContenidoReferenceDTO contenido,
    Boolean completado,
    LocalDateTime fechaCompletado
) {

}
