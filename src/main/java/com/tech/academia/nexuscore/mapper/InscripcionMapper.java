package com.tech.academia.nexuscore.mapper;

import com.tech.academia.nexuscore.dto.CursoReferenceDTO;
import com.tech.academia.nexuscore.dto.CursoResponseDTO;
import com.tech.academia.nexuscore.dto.InscripcionResponseDTO;
import com.tech.academia.nexuscore.dto.UsuarioReferenceDTO;
import com.tech.academia.nexuscore.dto.UsuarioResponseDTO;
import com.tech.academia.nexuscore.model.Curso;
import com.tech.academia.nexuscore.model.Inscripcion;
import com.tech.academia.nexuscore.model.Usuario;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class InscripcionMapper {

  // Usuario + Curso -> Inscripcion
  public Inscripcion usuarioYCursoToInscripcion(
      Usuario usuario, Curso curso) {

    return new Inscripcion(
        null,
        LocalDate.now(),
        0.0,
        usuario,
        curso
        );
  }

  // Inscripcion + UsuarioResponseDTO + CursoResponseDTO -> InscripcionResponseDTO
  public InscripcionResponseDTO inscripcionToResponseDto(
      Inscripcion inscripcion, UsuarioReferenceDTO usuarioDto, CursoReferenceDTO cursoDto) {

    return new InscripcionResponseDTO(
        inscripcion.getId(),
        inscripcion.getFechaInscripcion(),
        inscripcion.getProgreso(),
        usuarioDto,
        cursoDto
    );
  }
}
