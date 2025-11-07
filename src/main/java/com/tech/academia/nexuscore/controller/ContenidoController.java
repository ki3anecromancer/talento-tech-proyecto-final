package com.tech.academia.nexuscore.controller;

import com.tech.academia.nexuscore.dto.ContenidoResponseDTO;
import com.tech.academia.nexuscore.dto.ContenidoUpdateRequestDTO;
import com.tech.academia.nexuscore.service.ContenidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/contenidos")
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
  @PutMapping("/{id}")
  public ResponseEntity<ContenidoResponseDTO> actualizarContenido(
      @PathVariable Long id,
      @Valid @RequestBody ContenidoUpdateRequestDTO updateDto) {

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(contenidoService.actualizarContenido(id, updateDto));
  }

  // Eliminar contenido
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminarContenido(
      @PathVariable Long id) {

    contenidoService.eliminarContenido(id);

    return ResponseEntity.noContent().build();
  }
}
