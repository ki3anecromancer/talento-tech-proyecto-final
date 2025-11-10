package com.tech.academia.nexuscore.dto;

import java.time.LocalDate;

public record InscripcionUsuarioResponseDTO(
    Long idUsuario,
    String nombreUsuario,
    LocalDate fechaInscripcion,
    Double progreso
) {

}
