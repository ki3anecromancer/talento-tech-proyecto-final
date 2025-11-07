package com.tech.academia.nexuscore.controller;

import com.tech.academia.nexuscore.dto.ProgresoContenidoResponseDTO;
import com.tech.academia.nexuscore.service.ProgresoContenidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/progreso/contenidos")
public class ProgresoContenidoController {

  private final ProgresoContenidoService progresoContenidoService;

  // TODO: FINALIZAR DESPUÉS DE SPRING SECURITY JWT
  // Marcar contenido como completado
  @PatchMapping("/{idContenido}/completado")
  public ResponseEntity<ProgresoContenidoResponseDTO> marcarContenidoComoCompletado(
      Long idUsuario,
      Long idContenido) {

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(progresoContenidoService.marcarContenidoComoCompletado(idUsuario, idContenido));
  }
}
