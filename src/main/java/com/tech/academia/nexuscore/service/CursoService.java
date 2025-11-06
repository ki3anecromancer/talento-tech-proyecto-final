package com.tech.academia.nexuscore.service;

import com.tech.academia.nexuscore.dto.CursoCreateRequestDTO;
import com.tech.academia.nexuscore.dto.CursoResponseDTO;
import com.tech.academia.nexuscore.dto.CursoUpdateRequestDTO;
import java.util.List;

public interface CursoService {

  // Obtener todos los cursos
  List<CursoResponseDTO> obtenerTodosLosCursos();

  // Obtener curso por ID
  CursoResponseDTO  obtenerCursoPorId(Long id);

  // Crear curso
  CursoResponseDTO crearCurso(CursoCreateRequestDTO createDto);

  // Actualizar curso
  CursoResponseDTO actualizarCurso(Long id, CursoUpdateRequestDTO updateDto);

  // Eliminar curso
  void eliminarCurso(Long id);
}
