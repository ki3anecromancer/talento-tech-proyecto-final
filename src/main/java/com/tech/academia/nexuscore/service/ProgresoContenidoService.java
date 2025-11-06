package com.tech.academia.nexuscore.service;

import com.tech.academia.nexuscore.dto.ProgresoContenidoResponseDTO;

public interface ProgresoContenidoService {

  // Marcar contenido como completado
  ProgresoContenidoResponseDTO marcarContenidoComoCompletado(Long idUsuario, Long idContenido);
}
