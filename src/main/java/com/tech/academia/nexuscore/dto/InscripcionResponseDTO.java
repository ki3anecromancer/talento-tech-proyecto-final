package com.tech.academia.nexuscore.dto;

import java.time.LocalDate;

public record InscripcionResponseDTO(
    Long id,
    LocalDate fechaInscripcion,
    Double progreso,
    UsuarioReferenceDTO usuario,
    CursoReferenceDTO curso
) {

}
