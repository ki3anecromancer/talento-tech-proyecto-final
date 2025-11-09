package com.tech.academia.nexuscore.service;

import com.tech.academia.nexuscore.dto.CursoCreateRequestDTO;
import com.tech.academia.nexuscore.dto.CursoCreateResponseDTO;
import com.tech.academia.nexuscore.dto.CursoResponseDTO;
import com.tech.academia.nexuscore.dto.CursoUpdateRequestDTO;
import java.util.List;

public interface CursoService {

  // Obtener todos los cursos
  List<CursoResponseDTO> obtenerTodosLosCursos();

  // Obtener curso por ID
  CursoResponseDTO  obtenerCursoPorId(Long id);

  // Crear curso
  CursoCreateResponseDTO crearCurso(CursoCreateRequestDTO createDto, Long idUsuario);

  // Actualizar curso
  CursoResponseDTO actualizarCurso(Long idCurso, Long idUsuario, CursoUpdateRequestDTO updateDto);

  // Eliminar curso
  void eliminarCurso(Long idCurso, Long idUsuario);

  // Obtener cursos por usuario
  List<CursoResponseDTO> obtenerCursosPorUsuario(Long idUsuario);
}
