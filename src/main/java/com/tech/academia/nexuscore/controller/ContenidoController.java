package com.tech.academia.nexuscore.controller;

import com.tech.academia.nexuscore.dto.ContenidoResponseDTO;
import com.tech.academia.nexuscore.dto.ContenidoUpdateRequestDTO;
import com.tech.academia.nexuscore.service.ContenidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/contenidos")
public class ContenidoController {

  private final ContenidoService contenidoService;

  // Obtener contenido por ID
  @GetMapping("/{id}")
  public ResponseEntity<ContenidoResponseDTO> obtenerContenidoPorId(
      @PathVariable Long id) {

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(contenidoService.obtenerContenidoPorId(id));
  }

  // Actualizar contenido
  @PutMapping("/{idContenido}")
  public ResponseEntity<ContenidoResponseDTO> actualizarContenido(
      @PathVariable Long idContenido,
      @Valid @RequestBody ContenidoUpdateRequestDTO updateDto,
      @AuthenticationPrincipal String idUsuarioString) {

    Long idUsuario = Long.parseLong(idUsuarioString);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(contenidoService.actualizarContenido(idContenido, idUsuario, updateDto));
  }

  // Eliminar contenido
  @DeleteMapping("/{idContenido}")
  public ResponseEntity<Void> eliminarContenido(
      @PathVariable Long idContenido,
      @AuthenticationPrincipal String idUsuarioString) {

    Long idUsuario = Long.parseLong(idUsuarioString);

    contenidoService.eliminarContenido(idContenido, idUsuario);

    return ResponseEntity.noContent().build();
  }
}
