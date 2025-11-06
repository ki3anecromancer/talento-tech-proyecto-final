package com.tech.academia.nexuscore.dto;

import java.time.LocalDate;
import java.util.Set;

public record InscripcionCursosResponseDTO(
    Long id,
    LocalDate fechaInscripcion,
    Double progreso,
    UsuarioReferenceDTO usuario,
    Set<CursoReferenceDTO> cursos
) {

}
