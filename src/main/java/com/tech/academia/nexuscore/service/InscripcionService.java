package com.tech.academia.nexuscore.service;

import com.tech.academia.nexuscore.dto.InscripcionCursosResponseDTO;
import com.tech.academia.nexuscore.dto.InscripcionResponseDTO;

public interface InscripcionService {

  // Inscribir usuario a un curso
  InscripcionResponseDTO inscribirUsuarioACurso(Long idUsuario, Long idCurso);

  // Obtener inscripcion por ID
  InscripcionResponseDTO obtenerInscripcionPorId(Long id);

  // Obtener los cursos inscritos de usuario
  InscripcionCursosResponseDTO obtenerCursosInscritosDeUsuario(Long idUsuario);

  // Actualizar progreso
  InscripcionResponseDTO actualizarProgreso(Long idUsuario, Long idCurso);

  // Eliminar inscripcion
  void eliminarInscripcion(Long id);
}
