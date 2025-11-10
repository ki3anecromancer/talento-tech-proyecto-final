package com.tech.academia.nexuscore.service;

import com.tech.academia.nexuscore.dto.ContenidoCreateRequestDTO;
import com.tech.academia.nexuscore.dto.ContenidoResponseDTO;
import com.tech.academia.nexuscore.dto.ContenidoUpdateRequestDTO;
import java.util.Set;

public interface ContenidoService {

  // Crear contenido en modulo
  ContenidoResponseDTO crearContenidoEnModulo(Long idUsuario, ContenidoCreateRequestDTO createDto);

  // Obtener contenido por ID
  ContenidoResponseDTO obtenerContenidoPorId(Long id);

  // Actualizar contenido
  ContenidoResponseDTO actualizarContenido(Long id, ContenidoUpdateRequestDTO updateDto);

  // Obtener contenidos por modulo
  Set<ContenidoResponseDTO> obtenerContenidosPorModulo(Long idModulo);

  // Eliminar contenido
  void eliminarContenido(Long id);
}
