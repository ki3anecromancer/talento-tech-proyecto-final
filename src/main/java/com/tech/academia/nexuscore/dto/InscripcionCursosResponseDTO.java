package com.tech.academia.nexuscore.dto;

import java.util.Set;

public record InscripcionCursosResponseDTO(
    UsuarioReferenceDTO usuario,
    Set<CursoReferenceDTO> cursos
) {

}
