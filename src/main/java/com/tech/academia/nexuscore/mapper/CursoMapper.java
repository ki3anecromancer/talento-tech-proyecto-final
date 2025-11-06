package com.tech.academia.nexuscore.mapper;

import com.tech.academia.nexuscore.dto.CursoCreateRequestDTO;
import com.tech.academia.nexuscore.dto.CursoReferenceDTO;
import com.tech.academia.nexuscore.dto.CursoResponseDTO;
import com.tech.academia.nexuscore.dto.CursoUpdateRequestDTO;
import com.tech.academia.nexuscore.dto.ModuloResponseDTO;
import com.tech.academia.nexuscore.model.Curso;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CursoMapper {

  // CursoCreateRequestDTO -> Curso
  public Curso createDtoToCurso(CursoCreateRequestDTO createDto) {

    return new Curso(
        null,
        createDto.titulo(),
        createDto.descripcion(),
        createDto.duracionHoras(),
        createDto.precio(),
        null,
        null
    );
  }

  // Curso -> CursoResponseDTO
  public CursoResponseDTO cursoToResponseDto(Curso curso) {

    Set<ModuloResponseDTO> modulosDto = curso.getModulos().stream()
        .map(modulo -> new ModuloResponseDTO(
            modulo.getId(),
            modulo.getTitulo(),
            modulo.getOrden()
        ))
        .collect(Collectors.toSet());

    return new CursoResponseDTO(
        curso.getId(),
        curso.getTitulo(),
        curso.getDescripcion(),
        curso.getDuracionHoras(),
        curso.getPrecio(),
        modulosDto
    );
  }

  // Actualizar Curso
  public void actualizarCurso(Curso curso, CursoUpdateRequestDTO updateDto) {

    curso.setTitulo(updateDto.titulo());
    curso.setDescripcion(updateDto.descripcion());
    curso.setDuracionHoras(updateDto.duracionHoras());
    curso.setPrecio(updateDto.precio());
  }

  // Curso -> CursoReferenceDTO
  public CursoReferenceDTO cursoToReferenceDto(Curso curso) {

    return new CursoReferenceDTO(
        curso.getId(),
        curso.getTitulo()
    );
  }
}
