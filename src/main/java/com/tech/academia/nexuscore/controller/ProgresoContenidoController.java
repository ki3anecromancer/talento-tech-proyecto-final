package com.tech.academia.nexuscore.controller;

import com.tech.academia.nexuscore.dto.ProgresoContenidoResponseDTO;
import com.tech.academia.nexuscore.security.userdetails.UsuarioDetails;
import com.tech.academia.nexuscore.service.ProgresoContenidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/progreso/contenidos")
public class ProgresoContenidoController {

  private final ProgresoContenidoService progresoContenidoService;

  // Marcar contenido como completado
  @PatchMapping("/{idContenido}/completado")
  public ResponseEntity<ProgresoContenidoResponseDTO> marcarContenidoComoCompletado(
      @PathVariable Long idContenido,
      @AuthenticationPrincipal UsuarioDetails usuarioDetails) {

    Long idUsuario = usuarioDetails.getId();

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(progresoContenidoService.marcarContenidoComoCompletado(idUsuario, idContenido));
  }
}
