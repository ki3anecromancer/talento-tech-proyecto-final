package com.tech.academia.nexuscore.controller;

import com.tech.academia.nexuscore.dto.ModuloResponseDTO;
import com.tech.academia.nexuscore.dto.ModuloUpdateRequestDTO;
import com.tech.academia.nexuscore.service.ModuloService;
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
@RequestMapping("/api/modulos")
public class ModuloController {

  private final ModuloService moduloService;

  // Obtener modulo por ID
  @GetMapping("/{id}")
  public ResponseEntity<ModuloResponseDTO> obtenerModuloPorId(
      @PathVariable Long id) {

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(moduloService.obtenerModuloPorId(id));
  }

  // Actualizar modulo
  @PutMapping("/{id}")
  public ResponseEntity<ModuloResponseDTO> actualizarModulo(
      @PathVariable Long id,
      @Valid @RequestBody ModuloUpdateRequestDTO updateDto) {

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(moduloService.actualizarModulo(id, updateDto));
  }

  // Eliminar modulo
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminarModulo(
      @PathVariable Long id) {

    return ResponseEntity.noContent().build();
  }
}
